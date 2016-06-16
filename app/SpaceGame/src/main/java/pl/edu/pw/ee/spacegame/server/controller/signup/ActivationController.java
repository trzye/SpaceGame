package pl.edu.pw.ee.spacegame.server.controller.signup;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.TextResponseEntity;
import pl.edu.pw.ee.spacegame.server.dao.crud.BaseAbstractComponent;
import pl.edu.pw.ee.spacegame.server.entity.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.*;

/**
 * Created by Michał on 2016-06-06.
 */
@RestController
@CrossOrigin
@RequestMapping(path = ACTIVATION_PATH)
public class ActivationController extends BaseAbstractComponent {

    @RequestMapping(method = GET)
    public ResponseEntity<?> activate(@RequestParam(name = "email") String email,
                                      @RequestParam(name = "activationCode") String activationCode) {
        databaseLogger.setClass(getClass());
        try {
            activateAction(email, activationCode);
            databaseLogger.info(String.format(ACTIVATION_LOG, email));
            return new TextResponseEntity<>(ACTIVATION_SUCCESS, HttpStatus.OK);
        } catch (IOException e) {
            return handleBadRequest(e);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

    @Transactional
    private void activateAction(String email, String activationCode) throws IOException {
        ActivationsEntity activationsEntity = activationsDAO.getActivationByEmail(email);
        if (activationsEntity == null)
            throw new IOException(USER_WITH_SUCH_EMAIL_NOT_EXISTS);

        if (!activationsEntity.getActivationCode().equals(activationCode)) {
            throw new IOException((BAD_ACTIVATION_CODE));
        }

        if (activationsEntity.getUsersByUserId().getIsActivated())
            throw new IOException(USER_ALREADY_ACTIVATED);

        if (isActivationTimeCorrect(activationsEntity)) {
            activationsEntity.getUsersByUserId().setIsActivated(true);
            activationsEntity.getPlanetFieldsByPlanetFieldId().setStatus(PlanetFieldsEntity.Status.USED);
            activationsDAO.save(activationsEntity);
            createActivatedUserData(activationsEntity);
        } else {
            throw new IOException(ACTIVATION_TIMEOUT);
        }
    }

    private Boolean isActivationTimeCorrect(ActivationsEntity activationsEntity) {
        Date signUpTime = activationsEntity.getTime();
        Date currentTime = new Date();
        long timeDiffer = currentTime.getTime() - signUpTime.getTime();
        long tenMinutes = 1000 * 60 * 10; //1000 millis * 60 seconds * 10 minutes
        return timeDiffer < tenMinutes;
    }


    private void createActivatedUserData(ActivationsEntity activationsEntity) {
        PlanetsEntity planetsEntity = getPlanetsEntity(activationsEntity);
        ResourcesEntity resourcesEntity = getResourcesEntity(planetsEntity);
        FleetsEntity fleetsEntity = getFleetsEntity(planetsEntity);
        CurrentAttacksEntity currentAttacksEntity = CurrentAttacksEntity.getStartingState(fleetsEntity);
        CurrentAlliancesEntity currentAlliancesEntity = CurrentAlliancesEntity.getStartingState(fleetsEntity);
        ArrayList<BuildingsEntity> buildingsEntities = getBuildingsEntities(planetsEntity);
        save(planetsEntity, resourcesEntity, fleetsEntity, currentAttacksEntity, currentAlliancesEntity, buildingsEntities);
    }

    private PlanetsEntity getPlanetsEntity(ActivationsEntity activationsEntity) {
        UsersEntity usersEntity = activationsEntity.getUsersByUserId();
        PlanetFieldsEntity planetFieldsEntity = activationsEntity.getPlanetFieldsByPlanetFieldId();
        PlanetsEntity planetsEntity = new PlanetsEntity();
        planetsEntity.setUsersByUserId(usersEntity);
        planetsEntity.setPlanetFieldsByPlanetFieldId(planetFieldsEntity);
        planetsEntity.setCoordinateName();
        return planetsEntity;
    }

    private ResourcesEntity getResourcesEntity(PlanetsEntity planetsEntity) {
        ResourcesEntity resourcesEntity = ResourcesEntity.getStartingState();
        planetsEntity.setResourcesByResourceId(resourcesEntity);
        return resourcesEntity;
    }

    private FleetsEntity getFleetsEntity(PlanetsEntity planetsEntity) {
        FleetsEntity fleetsEntity = FleetsEntity.getStartingState();
        planetsEntity.setFleetsByFleetId(fleetsEntity);
        return fleetsEntity;
    }

    private ArrayList<BuildingsEntity> getBuildingsEntities(PlanetsEntity planetsEntity) {
        BuildingsEntity ununtriumMine = BuildingsEntity.getUnuntriumMine(buildingsDicDAO);
        BuildingsEntity gadolinMine = BuildingsEntity.getGadolinMine(buildingsDicDAO);
        BuildingsEntity hangar = BuildingsEntity.getHangar(buildingsDicDAO);
        BuildingsEntity defenceSystems = BuildingsEntity.getDefenceSystems(buildingsDicDAO);

        ununtriumMine.setPlanetsByPlanetId(planetsEntity);
        gadolinMine.setPlanetsByPlanetId(planetsEntity);
        hangar.setPlanetsByPlanetId(planetsEntity);
        defenceSystems.setPlanetsByPlanetId(planetsEntity);

        ArrayList<BuildingsEntity> buildingsEntities = new ArrayList<>();
        buildingsEntities.add(ununtriumMine);
        buildingsEntities.add(gadolinMine);
        buildingsEntities.add(hangar);
        buildingsEntities.add(defenceSystems);
        return buildingsEntities;
    }

    private void save(PlanetsEntity planetsEntity, ResourcesEntity resourcesEntity, FleetsEntity fleetsEntity, CurrentAttacksEntity currentAttacksEntity, CurrentAlliancesEntity currentAlliancesEntity, ArrayList<BuildingsEntity> buildingsEntities) {
        resourcesDAO.save(resourcesEntity);
        fleetsDAO.save(fleetsEntity);
        currentAttacksDAO.save(currentAttacksEntity);
        currentAlliancesDAO.save(currentAlliancesEntity);
        planetsDAO.save(planetsEntity);
        buildingsDAO.save(buildingsEntities);
    }

}
