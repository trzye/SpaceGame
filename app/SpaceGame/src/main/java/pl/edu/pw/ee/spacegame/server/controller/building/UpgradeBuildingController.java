package pl.edu.pw.ee.spacegame.server.controller.building;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.BaseAbstractController;
import pl.edu.pw.ee.spacegame.server.controller.TextResponseEntity;
import pl.edu.pw.ee.spacegame.server.entity.BuildingsEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity;
import pl.edu.pw.ee.spacegame.server.entity.ResourcesEntity;
import pl.edu.pw.ee.spacegame.server.entity.UsersEntity;
import pl.edu.pw.ee.spacegame.server.realtime.Refresher;
import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;
import pl.edu.pw.ee.spacegame.server.security.LoggedUsers;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.UPGRADE_BUILDING_PATH;
import static pl.edu.pw.ee.spacegame.server.entity.BuildingsEntity.*;

/**
 * Created by Michał on 2016-05-05.
 */
//TODO: magic Strings
@RestController
@CrossOrigin
@RequestMapping(UPGRADE_BUILDING_PATH)
public class UpgradeBuildingController extends BaseAbstractController {


    @RequestMapping(method = POST)
    public ResponseEntity<?> upgradeBuilding(@RequestBody UpgradeBuildingData upgradeBuildingData) {
        databaseLogger.setClass(getClass());
        try {
            AuthenticationData authenticationData = upgradeBuildingData.getAuthenticationData();
            if (!LoggedUsers.isLogged(authenticationData)) {
                return TextResponseEntity.getNotAuthorizedResponseEntity(authenticationData, databaseLogger);
            }
            UsersEntity usersEntity = usersDAO.getUserByNickname(authenticationData.getNickname());
            if (!usersEntity.getIsActivated()) {
                return TextResponseEntity.getNotActivatedResponseEntity(authenticationData, databaseLogger);
            }
            Refresher.refreshAll(this);
            BuildingsEntity.ID id = BuildingsEntity.ID.values()[upgradeBuildingData.getTypeId()];
            PlanetsEntity planetsEntity = usersEntity.getPlanet();
            ResourcesEntity resourcesEntity = planetsEntity.getResourcesByResourceId();
            upgradeBuilding(id, resourcesEntity, planetsEntity);
            //TODO: logi
            return new TextResponseEntity<>("Zwiększono poziom budynku o 1", HttpStatus.OK);
        } catch (IOException e) {
            return handleBadRequest(e);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

    private void upgradeBuilding(BuildingsEntity.ID id, ResourcesEntity resourcesEntity, PlanetsEntity planetsEntity) throws IOException {
        switch (id) {
            case DEFENCE_SYSTEMS_ID: {
                BuildingsEntity buildingsEntity = planetsEntity.getDefenceSystems();
                upgradeBuilding(buildingsEntity, DEFENCE_SYSTEMS_MAX_LEVEL, BuildingLogic.getDefenceSystemsCost(buildingsEntity), resourcesEntity);
                break;
            }
            case HANGAR_ID: {
                BuildingsEntity buildingsEntity = planetsEntity.getHangar();
                upgradeBuilding(buildingsEntity, HANGAR_MAX_LEVEL, BuildingLogic.getHangarCost(buildingsEntity), resourcesEntity);
                break;
            }
            case GADOLIN_MINE_ID: {
                BuildingsEntity buildingsEntity = planetsEntity.getGadolinMine();
                upgradeBuilding(buildingsEntity, GADOLIN_MINE_MAX_LEVEL, BuildingLogic.getGadolinMineCost(buildingsEntity), resourcesEntity);
                break;
            }
            case UNUNTRIUM_MINE_ID: {
                BuildingsEntity buildingsEntity = planetsEntity.getUnuntriumMine();
                upgradeBuilding(buildingsEntity, UNUNTRIUM_MINE_MAX_LEVEL, BuildingLogic.getUnuntriumMineCost(buildingsEntity), resourcesEntity);
                break;
            }
            default: {
                throw new IOException("Niepoprawny typ budynku do rozbudowania");
            }
        }
    }

    @Transactional
    private void upgradeBuilding(BuildingsEntity buildingsEntity, Integer maxLevel, Integer levelCost, ResourcesEntity resourcesEntity) throws IOException {
        if (buildingsEntity.getLevel() >= maxLevel) {
            throw new IOException("Maksymalny poziom jest już osiągnięty, nie można rozbudować budynku.");
        }
        if (resourcesEntity.getGadolin() < levelCost) {
            throw new IOException("Za mało gadolinium aby rozbudować ten budynek");
        }
        resourcesEntity.setGadolin(resourcesEntity.getGadolin() - levelCost);
        buildingsEntity.setLevel(buildingsEntity.getLevel() + 1);
        resourcesDAO.save(resourcesEntity);
        buildingsDAO.save(buildingsEntity);
    }

}
