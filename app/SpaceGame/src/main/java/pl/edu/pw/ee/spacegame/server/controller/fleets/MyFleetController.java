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
        //TODO wyciągnąć logikę ustalania statusu gdzieś na zewnątrz
        if (currentAttacksEntity.getTimeOfSendingAttack() != null) {
            if (currentAttacksEntity.getPlanetsByAttackedPlanetId() != planetsEntity) {
                return FleetsEntity.FleetStatus.ON_THE_WAY_TO_ATTACK.ordinal();
            } else {
                return FleetsEntity.FleetStatus.COMMING_BACK_FROM_ATTACK.ordinal();
            }
        }
        if (currentAlliancesEntity.getTimeOfSendingAlliance() != null) {
            if (currentAlliancesEntity.getPlanetsByHelpedPlanetId() != planetsEntity) {
                return FleetsEntity.FleetStatus.ON_THE_WAY_TO_HELP.ordinal();
            } else {
                return FleetsEntity.FleetStatus.COMMING_BACK_FROM_HELP.ordinal();
            }
        }
        if (currentAlliancesEntity.getCurrentAllianceId() != null) {
            return FleetsEntity.FleetStatus.ON_THE_OTHER_PLANET.ordinal();
        }
        return FleetsEntity.FleetStatus.ON_THE_MOTHER_PLANET.ordinal();
    }

    private ArrayList<ShipsData> getShips(FleetsEntity fleetsEntity, BuildingsEntity hangar) {
        ArrayList<ShipsData> ships = new ArrayList<>();
        Integer hangarLevel = hangar.getLevel();
        ShipsData warships = new ShipsData();
        ShipsData bombers = new ShipsData();
        ShipsData ironcladses = new ShipsData();
        warships.setTypeId(FleetsEntity.FleetType.WARSHIP.ordinal());
        bombers.setTypeId(FleetsEntity.FleetType.BOMBER.ordinal());
        ironcladses.setTypeId(FleetsEntity.FleetType.IRONCLADS.ordinal());
        warships.setNumber(fleetsEntity.getWarships());
        bombers.setNumber(fleetsEntity.getBombers());
        ironcladses.setNumber(fleetsEntity.getIronclads());
        //TODO wyciągnąć logikę ustalania ceny gdzieś na zewnątrz
        warships.setBuildInUnuntiumCost(FleetsEntity.BASE_WARSHIP_COST * (int) (1.0 - 0.05 * hangarLevel));
        bombers.setBuildInUnuntiumCost(FleetsEntity.BASE_BOMBER_COST * (int) (1.0 - 0.05 * hangarLevel));
        ironcladses.setBuildInUnuntiumCost(FleetsEntity.BASE_IRONCLADS_COST * (int) (1.0 - 0.05 * hangarLevel));
        ships.add(warships);
        ships.add(bombers);
        ships.add(ironcladses);
        return ships;
    }

}
