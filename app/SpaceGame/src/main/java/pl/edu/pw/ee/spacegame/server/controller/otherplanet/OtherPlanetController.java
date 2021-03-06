package pl.edu.pw.ee.spacegame.server.controller.otherplanet;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.JsonResponseEntity;
import pl.edu.pw.ee.spacegame.server.controller.TextResponseEntity;
import pl.edu.pw.ee.spacegame.server.dao.crud.BaseAbstractComponent;
import pl.edu.pw.ee.spacegame.server.entity.BuildingsEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetFieldsEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity;
import pl.edu.pw.ee.spacegame.server.entity.UsersEntity;
import pl.edu.pw.ee.spacegame.server.realtime.Refresher;
import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;
import pl.edu.pw.ee.spacegame.server.security.LoggedUsers;

import java.io.IOException;
import java.util.ArrayList;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.*;
import static pl.edu.pw.ee.spacegame.server.entity.BuildingsEntity.ID.*;

/**
 * Created by KTamo_000 on 2016-06-14.
 */

@RestController
@CrossOrigin
@RequestMapping(OTHER_PLANET_PATH)
public class OtherPlanetController extends BaseAbstractComponent {

    @RequestMapping(method = POST)
    public ResponseEntity<?> test(@RequestBody OtherPlanetData otherPlanetData) {
        databaseLogger.setClass(getClass());
        try {
            AuthenticationData authenticationData = otherPlanetData.getAuthenticationData();
            if (!LoggedUsers.isLogged(authenticationData)) {
                return TextResponseEntity.getNotAuthorizedResponseEntity(authenticationData, databaseLogger);
            }
            UsersEntity usersEntity = usersDAO.getUserByNickname(authenticationData.getNickname());
            if (!usersEntity.getIsActivated()) {
                return TextResponseEntity.getNotActivatedResponseEntity(authenticationData, databaseLogger);
            }
            Refresher.refreshAll(this);
            PlanetViewData outputPlanetView = createPlanetViewData(otherPlanetData, usersEntity.getPlanet());
            databaseLogger.info(GET_OTHER_PLANET_LOG);
            return new JsonResponseEntity<>(outputPlanetView, OK);
        } catch (IOException e) {
            return handleBadRequest(e);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

    private PlanetViewData createPlanetViewData(OtherPlanetData otherPlanetData, PlanetsEntity planet) throws IOException {
        PlanetViewData pvd = new PlanetViewData();
        PlanetFieldsEntity planetFieldEntity = getPlanetFieldsDAO().getPlanetByXandY(otherPlanetData.getCoordinateX(), otherPlanetData.getCoordinateY());
        if (planetFieldEntity == null) {
            throw new IOException(NO_PLANET_ON_FIELD);
        }
        PlanetsEntity planetsEntity = planetFieldEntity.getPlanetsEntity();
        if (planet == planetsEntity) {
            throw new IOException(CANT_CHOOSE_YOUR_PLANET);
        }
        pvd.setNickname(planetsEntity.getUsersByUserId().getNickname());
        pvd.setGadolin(planetsEntity.getResourcesByResourceId().getGadolin());
        pvd.setUnuntrium(planetsEntity.getResourcesByResourceId().getUnuntrium());
        pvd.setBuildings(getBuildings(planetsEntity));
        return pvd;
    }

    private ArrayList<BuildingData> getBuildings(PlanetsEntity planetsEntity) {
        ArrayList<BuildingData> buildings = new ArrayList<>();
        buildings.add(getUnuntriumMine(planetsEntity));
        buildings.add(getGadolinMine(planetsEntity));
        buildings.add(getHangar(planetsEntity));
        buildings.add(getDefenceSystems(planetsEntity));
        return buildings;
    }

    private BuildingData getUnuntriumMine(PlanetsEntity planetsEntity) {
        BuildingsEntity buildingsEntity = planetsEntity.getUnuntriumMine();
        BuildingData buildingData = new BuildingData();
        buildingData.setLevel(buildingsEntity.getLevel());
        buildingData.setTypeId(UNUNTRIUM_MINE_ID.ordinal());
        return buildingData;
    }

    private BuildingData getGadolinMine(PlanetsEntity planetsEntity) {
        BuildingsEntity buildingsEntity = planetsEntity.getGadolinMine();
        BuildingData buildingData = new BuildingData();
        buildingData.setLevel(buildingsEntity.getLevel());
        buildingData.setTypeId(GADOLIN_MINE_ID.ordinal());
        return buildingData;
    }

    private BuildingData getHangar(PlanetsEntity planetsEntity) {
        BuildingsEntity buildingsEntity = planetsEntity.getHangar();
        BuildingData buildingData = new BuildingData();
        buildingData.setLevel(buildingsEntity.getLevel());
        buildingData.setTypeId(HANGAR_ID.ordinal());
        return buildingData;
    }

    private BuildingData getDefenceSystems(PlanetsEntity planetsEntity) {
        BuildingsEntity buildingsEntity = planetsEntity.getDefenceSystems();
        BuildingData buildingData = new BuildingData();
        buildingData.setLevel(buildingsEntity.getLevel());
        buildingData.setTypeId(DEFENCE_SYSTEMS_ID.ordinal());
        return buildingData;
    }
}
