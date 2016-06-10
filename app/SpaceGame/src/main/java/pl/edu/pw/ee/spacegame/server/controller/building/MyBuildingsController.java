package pl.edu.pw.ee.spacegame.server.controller.building;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.BaseAbstractController;
import pl.edu.pw.ee.spacegame.server.controller.JsonResponseEntity;
import pl.edu.pw.ee.spacegame.server.controller.TextResponseEntity;
import pl.edu.pw.ee.spacegame.server.entity.BuildingsEntity;
import pl.edu.pw.ee.spacegame.server.entity.UsersEntity;
import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;
import pl.edu.pw.ee.spacegame.server.security.LoggedUsers;

import java.util.ArrayList;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.MY_BUILDINGS_PATH;
import static pl.edu.pw.ee.spacegame.server.entity.BuildingsEntity.*;
import static pl.edu.pw.ee.spacegame.server.entity.BuildingsEntity.ID.*;

/**
 * Created by Michał on 2016-05-05.
 */
@RestController
@CrossOrigin
@RequestMapping(MY_BUILDINGS_PATH)
public class MyBuildingsController extends BaseAbstractController {


    @RequestMapping(method = POST)
    public ResponseEntity<?> getMyBuildings(@RequestBody AuthenticationData authenticationData) {
        databaseLogger.setClass(getClass());
        try {
            if (!LoggedUsers.isLogged(authenticationData)) {
                return TextResponseEntity.getNotAuthorizedResponseEntity(authenticationData, databaseLogger);
            }
            UsersEntity usersEntity = usersDAO.getUserByNickname(authenticationData.getNickname());
            if (!usersEntity.getIsActivated()) {
                return TextResponseEntity.getNotActivatedResponseEntity(authenticationData, databaseLogger);
            }
            ArrayList<MyBuildingData> myBuildings = getMyBuildings(usersEntity);
            //TODO: Logi
            return new JsonResponseEntity<>(myBuildings, HttpStatus.OK);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

    private ArrayList<MyBuildingData> getMyBuildings(UsersEntity usersEntity) {
        ArrayList<MyBuildingData> myBuildings = new ArrayList<>();
        myBuildings.add(getUnuntriumMine(usersEntity));
        myBuildings.add(getGadolinMine(usersEntity));
        myBuildings.add(getHangar(usersEntity));
        myBuildings.add(getDefenceSystems(usersEntity));
        return myBuildings;
    }

    private MyBuildingData getUnuntriumMine(UsersEntity usersEntity) {
        BuildingsEntity buildingsEntity = usersEntity.getPlanet().getUnuntriumMine();
        MyBuildingData myBuildingData = new MyBuildingData();
        myBuildingData.setLevel(buildingsEntity.getLevel());
        myBuildingData.setMaxLevel(UNUNTRIUM_MINE_MAX_LEVEL);
        myBuildingData.setTypeId(UNUNTRIUM_MINE_ID.ordinal());
        myBuildingData.setNextLevelInGadolinsCost(BuildingCostsLogic.getUnuntriumMineCost(buildingsEntity));
        return myBuildingData;
    }

    private MyBuildingData getGadolinMine(UsersEntity usersEntity) {
        BuildingsEntity buildingsEntity = usersEntity.getPlanet().getGadolinMine();
        MyBuildingData myBuildingData = new MyBuildingData();
        myBuildingData.setLevel(buildingsEntity.getLevel());
        myBuildingData.setMaxLevel(GADOLIN_MINE_MAX_LEVEL);
        myBuildingData.setTypeId(GADOLIN_MINE_ID.ordinal());
        myBuildingData.setNextLevelInGadolinsCost(BuildingCostsLogic.getGadolinMineCost(buildingsEntity));
        return myBuildingData;
    }

    private MyBuildingData getHangar(UsersEntity usersEntity) {
        BuildingsEntity buildingsEntity = usersEntity.getPlanet().getHangar();
        MyBuildingData myBuildingData = new MyBuildingData();
        myBuildingData.setLevel(buildingsEntity.getLevel());
        myBuildingData.setMaxLevel(HANGAR_MAX_LEVEL);
        myBuildingData.setTypeId(HANGAR_ID.ordinal());
        myBuildingData.setNextLevelInGadolinsCost(BuildingCostsLogic.getHangarCost(buildingsEntity));
        return myBuildingData;
    }

    private MyBuildingData getDefenceSystems(UsersEntity usersEntity) {
        BuildingsEntity buildingsEntity = usersEntity.getPlanet().getDefenceSystems();
        MyBuildingData myBuildingData = new MyBuildingData();
        myBuildingData.setLevel(buildingsEntity.getLevel());
        myBuildingData.setMaxLevel(DEFENCE_SYSTEMS_MAX_LEVEL);
        myBuildingData.setTypeId(DEFENCE_SYSTEMS_ID.ordinal());
        myBuildingData.setNextLevelInGadolinsCost(BuildingCostsLogic.getDefenceSystemsCost(buildingsEntity));
        return myBuildingData;
    }

}
