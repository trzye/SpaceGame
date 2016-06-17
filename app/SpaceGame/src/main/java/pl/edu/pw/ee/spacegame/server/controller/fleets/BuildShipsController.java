package pl.edu.pw.ee.spacegame.server.controller.fleets;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects;
import pl.edu.pw.ee.spacegame.server.controller.TextResponseEntity;
import pl.edu.pw.ee.spacegame.server.dao.crud.BaseAbstractComponent;
import pl.edu.pw.ee.spacegame.server.entity.*;
import pl.edu.pw.ee.spacegame.server.game.FleetCosts;
import pl.edu.pw.ee.spacegame.server.realtime.Refresher;
import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;
import pl.edu.pw.ee.spacegame.server.security.LoggedUsers;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.*;
import static pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity.FleetStatus.ON_THE_MOTHER_PLANET;

/**
 * Created by Michał on 2016-05-05.
 */
@RestController
@CrossOrigin
@RequestMapping(BUILD_SHIPS_PATH)
public class BuildShipsController extends BaseAbstractComponent {


    @RequestMapping(method = POST)
    public ResponseEntity<?> upgradeBuilding(@RequestBody BuildingShipsData buildingShipsData) {
        databaseLogger.setClass(getClass());
        try {
            AuthenticationData authenticationData = buildingShipsData.getAuthenticationData();
            if (!LoggedUsers.isLogged(authenticationData)) {
                return TextResponseEntity.getNotAuthorizedResponseEntity(authenticationData, databaseLogger);
            }
            UsersEntity usersEntity = usersDAO.getUserByNickname(authenticationData.getNickname());
            if (!usersEntity.getIsActivated()) {
                return TextResponseEntity.getNotActivatedResponseEntity(authenticationData, databaseLogger);
            }
            Refresher.refreshAll(this);
            buildShips(usersEntity, buildingShipsData);
            databaseLogger.info(String.format(ControllerConstantObjects.BUILD_FLEET, usersEntity.getNickname(), buildingShipsData.getTypeId(), buildingShipsData.getNumber()));
            return new TextResponseEntity<>(FLEET_BUILT, HttpStatus.OK);
        } catch (IOException e) {
            return handleBadRequest(e);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

    @Transactional
    private void buildShips(UsersEntity usersEntity, BuildingShipsData buildingShipsData) throws IOException {
        PlanetsEntity planetsEntity = usersEntity.getPlanet();
        ResourcesEntity resourcesEntity = planetsEntity.getResourcesByResourceId();
        FleetsEntity.FleetType fleetType = FleetsEntity.FleetType.values()[buildingShipsData.getTypeId()];
        PlanetsEntity.FleetStatus fleetStatus = planetsEntity.getFleetStatus();
        if (!fleetStatus.equals(ON_THE_MOTHER_PLANET)) {
            throw new IOException(CANT_BUILD_FLEET);
        }
        if (buildingShipsData.getNumber() <= 0) {
            throw new IOException(CANT_BUILD_ZERO_FLEET);
        }
        FleetsEntity fleetsEntity =
                getBuiltFleet(fleetType, resourcesEntity, planetsEntity.getHangar(), planetsEntity.getFleetsByFleetId(), buildingShipsData);
        fleetsDAO.save(fleetsEntity);
    }

    private FleetsEntity getBuiltFleet
            (FleetsEntity.FleetType fleetType, ResourcesEntity resourcesEntity, BuildingsEntity hangar, FleetsEntity fleetsEntity, BuildingShipsData buildingShipsData) throws IOException {
        switch (fleetType) {
            case BOMBER: {
                Integer costs = buildingShipsData.getNumber() * FleetCosts.getBomberCost(hangar);
                if (resourcesEntity.getUnuntrium() <= costs) {
                    throw new IOException(NOT_ENOUGH_UNUNTRIUM_FOR_BUILDING);
                }
                resourcesEntity.setUnuntrium(resourcesEntity.getUnuntrium() - costs);
                fleetsEntity.setBombers(fleetsEntity.getBombers() + buildingShipsData.getNumber());
                return fleetsEntity;
            }
            case WARSHIP: {
                Integer costs = buildingShipsData.getNumber() * FleetCosts.getWarshipCost(hangar);
                if (resourcesEntity.getUnuntrium() <= costs) {
                    throw new IOException(NOT_ENOUGH_UNUNTRIUM_FOR_BUILDING);
                }
                resourcesEntity.setUnuntrium(resourcesEntity.getUnuntrium() - costs);
                fleetsEntity.setWarships(fleetsEntity.getWarships() + buildingShipsData.getNumber());
                return fleetsEntity;
            }
            case IRONCLADS: {
                Integer costs = buildingShipsData.getNumber() * FleetCosts.getIroncladsCost(hangar);
                if (resourcesEntity.getUnuntrium() <= costs) {
                    throw new IOException(NOT_ENOUGH_UNUNTRIUM_FOR_BUILDING);
                }
                resourcesEntity.setUnuntrium(resourcesEntity.getUnuntrium() - costs);
                fleetsEntity.setIronclads(fleetsEntity.getIronclads() + buildingShipsData.getNumber());
                return fleetsEntity;
            }
            default: {
                throw new IOException(NOT_ENOUGH_UNUNTRIUM_FOR_BUILDING);
            }
        }
    }


}
