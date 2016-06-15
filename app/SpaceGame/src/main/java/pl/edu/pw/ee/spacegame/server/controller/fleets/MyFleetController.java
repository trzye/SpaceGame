package pl.edu.pw.ee.spacegame.server.controller.fleets;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.BaseAbstractController;
import pl.edu.pw.ee.spacegame.server.controller.JsonResponseEntity;
import pl.edu.pw.ee.spacegame.server.controller.TextResponseEntity;
import pl.edu.pw.ee.spacegame.server.entity.*;
import pl.edu.pw.ee.spacegame.server.realtime.Refresher;
import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;
import pl.edu.pw.ee.spacegame.server.security.LoggedUsers;

import java.util.ArrayList;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.MY_FLEET_PATH;

/**
 * Created by Michał on 2016-05-05.
 */
@RestController
@CrossOrigin
@RequestMapping(MY_FLEET_PATH)
public class MyFleetController extends BaseAbstractController {


    @RequestMapping(method = POST)
    public ResponseEntity<?> getMyFleet(@RequestBody AuthenticationData authenticationData) {
        databaseLogger.setClass(getClass());
        try {
            if (!LoggedUsers.isLogged(authenticationData)) {
                return TextResponseEntity.getNotAuthorizedResponseEntity(authenticationData, databaseLogger);
            }
            UsersEntity usersEntity = usersDAO.getUserByNickname(authenticationData.getNickname());
            if (!usersEntity.getIsActivated()) {
                return TextResponseEntity.getNotActivatedResponseEntity(authenticationData, databaseLogger);
            }
            Refresher.refreshAll(this);
            MyFleetData myFleet = getMyFleet(usersEntity.getPlanet());
            //TODO: Logi
            return new JsonResponseEntity<>(myFleet, HttpStatus.OK);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

    private MyFleetData getMyFleet(PlanetsEntity planetsEntity) {
        MyFleetData fleetData = new MyFleetData();
        FleetsEntity fleetsEntity = planetsEntity.getFleetsByFleetId();
        CurrentAlliancesEntity currentAlliancesEntity = planetsEntity.getCurrentAlliances();
        CurrentAttacksEntity currentAttacksEntity = planetsEntity.getCurrentAttacks();
        fleetData.setStatus(getStatus(planetsEntity, currentAlliancesEntity, currentAttacksEntity));
        ArrayList<ShipsData> ships = getShips(fleetsEntity, planetsEntity.getHangar());
        fleetData.setShips(ships);
        return fleetData;
    }

    private Integer getStatus(PlanetsEntity planetsEntity, CurrentAlliancesEntity currentAlliancesEntity, CurrentAttacksEntity currentAttacksEntity) {
        return FleetLogic.getStatus(planetsEntity).ordinal();
    }

    private ArrayList<ShipsData> getShips(FleetsEntity fleetsEntity, BuildingsEntity hangar) {
        ArrayList<ShipsData> ships = new ArrayList<>();
        ShipsData warships = new ShipsData();
        ShipsData bombers = new ShipsData();
        ShipsData ironclads = new ShipsData();
        warships.setTypeId(FleetsEntity.FleetType.WARSHIP.ordinal());
        bombers.setTypeId(FleetsEntity.FleetType.BOMBER.ordinal());
        ironclads.setTypeId(FleetsEntity.FleetType.IRONCLADS.ordinal());
        warships.setNumber(fleetsEntity.getWarships());
        bombers.setNumber(fleetsEntity.getBombers());
        ironclads.setNumber(fleetsEntity.getIronclads());
        warships.setBuildInUnuntiumCost(FleetLogic.getWarshipCost(hangar));
        bombers.setBuildInUnuntiumCost(FleetLogic.getBomberCost(hangar));
        ironclads.setBuildInUnuntiumCost(FleetLogic.getIroncladsCost(hangar));
        ships.add(warships);
        ships.add(bombers);
        ships.add(ironclads);
        return ships;
    }

}
