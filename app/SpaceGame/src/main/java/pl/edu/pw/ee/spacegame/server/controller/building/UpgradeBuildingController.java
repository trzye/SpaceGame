package pl.edu.pw.ee.spacegame.server.controller.building;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.TextResponseEntity;
import pl.edu.pw.ee.spacegame.server.dao.crud.BaseAbstractComponent;
import pl.edu.pw.ee.spacegame.server.entity.BuildingsEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity;
import pl.edu.pw.ee.spacegame.server.entity.ResourcesEntity;
import pl.edu.pw.ee.spacegame.server.entity.UsersEntity;
import pl.edu.pw.ee.spacegame.server.game.BuildingCosts;
import pl.edu.pw.ee.spacegame.server.realtime.Refresher;
import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;
import pl.edu.pw.ee.spacegame.server.security.LoggedUsers;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.*;
import static pl.edu.pw.ee.spacegame.server.game.GameBalanceSettings.*;

/**
 * Created by Michał on 2016-05-05.
 */
@RestController
@CrossOrigin
@RequestMapping(UPGRADE_BUILDING_PATH)
public class UpgradeBuildingController extends BaseAbstractComponent {


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
            BuildingsEntity upgradedBuilding = upgradeBuilding(id, resourcesEntity, planetsEntity);
            databaseLogger.info(String.format(UPGRADE_BUILDING_LOG, upgradedBuilding.getBuildingId()));
            return new TextResponseEntity<>(UPGRADE_BUILDING, HttpStatus.OK);
        } catch (IOException e) {
            return handleBadRequest(e);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

    private BuildingsEntity upgradeBuilding(BuildingsEntity.ID id, ResourcesEntity resourcesEntity, PlanetsEntity planetsEntity) throws IOException {
        switch (id) {
            case DEFENCE_SYSTEMS_ID: {
                BuildingsEntity buildingsEntity = planetsEntity.getDefenceSystems();
                return upgradeBuilding(buildingsEntity, DEFENCE_SYSTEMS_MAX_LEVEL, BuildingCosts.getDefenceSystemsCost(buildingsEntity), resourcesEntity);
            }
            case HANGAR_ID: {
                BuildingsEntity buildingsEntity = planetsEntity.getHangar();
                return upgradeBuilding(buildingsEntity, HANGAR_MAX_LEVEL, BuildingCosts.getHangarCost(buildingsEntity), resourcesEntity);
            }
            case GADOLIN_MINE_ID: {
                BuildingsEntity buildingsEntity = planetsEntity.getGadolinMine();
                return upgradeBuilding(buildingsEntity, GADOLIN_MINE_MAX_LEVEL, BuildingCosts.getGadolinMineCost(buildingsEntity), resourcesEntity);
            }
            case UNUNTRIUM_MINE_ID: {
                BuildingsEntity buildingsEntity = planetsEntity.getUnuntriumMine();
                return upgradeBuilding(buildingsEntity, UNUNTRIUM_MINE_MAX_LEVEL, BuildingCosts.getUnuntriumMineCost(buildingsEntity), resourcesEntity);
            }
            default: {
                throw new IOException(WRONG_BUILDING_TYPE_ID);
            }
        }
    }

    @Transactional
    private BuildingsEntity upgradeBuilding(BuildingsEntity buildingsEntity, Integer maxLevel, Integer levelCost, ResourcesEntity resourcesEntity) throws IOException {
        if (buildingsEntity.getLevel() >= maxLevel) {
            throw new IOException(MAX_BUILDING_LEVEL_UPGRADE);
        }
        if (resourcesEntity.getGadolin() < levelCost) {
            throw new IOException(NOT_ENOUGH_GADOLINIUM_FOR_BUILDING);
        }
        resourcesEntity.setGadolin(resourcesEntity.getGadolin() - levelCost);
        buildingsEntity.setLevel(buildingsEntity.getLevel() + 1);
        resourcesDAO.save(resourcesEntity);
        buildingsDAO.save(buildingsEntity);
        return buildingsEntity;
    }

}
