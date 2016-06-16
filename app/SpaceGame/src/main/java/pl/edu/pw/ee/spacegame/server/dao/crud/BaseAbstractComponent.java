package pl.edu.pw.ee.spacegame.server.dao.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.edu.pw.ee.spacegame.server.controller.TextResponseEntity;
import pl.edu.pw.ee.spacegame.server.utils.DatabaseLogger;

import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.REQUEST_ERROR_LOG;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.UNEXPECTED_ERROR_LOG;

/**
 * Created by Michał on 2016-06-04.
 */
@Component
public abstract class BaseAbstractComponent {

    @Autowired
    protected DatabaseLogger databaseLogger;

    @Autowired
    protected ActivationsDAO activationsDAO;

    @Autowired
    protected AllianceHistoriesDAO allianceHistoriesDAO;

    @Autowired
    protected AttackHistoriesDAO attackHistoriesDAO;

    @Autowired
    protected BuildingsDAO buildingsDAO;

    @Autowired
    protected BuildingsDicDAO buildingsDicDAO;

    @Autowired
    protected CurrentAlliancesDAO currentAlliancesDAO;

    @Autowired
    protected CurrentAttacksDAO currentAttacksDAO;

    @Autowired
    protected FleetsDAO fleetsDAO;

    @Autowired
    protected LogsDAO logsDAO;

    @Autowired
    protected PlanetFieldsDAO planetFieldsDAO;

    @Autowired
    protected PlanetsDAO planetsDAO;

    @Autowired
    protected ResourcesDAO resourcesDAO;

    @Autowired
    protected UsersDAO usersDAO;

    protected ResponseEntity<?> handleBadRequest(IOException e) {
        databaseLogger.info(REQUEST_ERROR_LOG + e.getMessage());
        e.printStackTrace();
        return new TextResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

    protected ResponseEntity<?> handleServerError(Exception e) {
        databaseLogger.error(UNEXPECTED_ERROR_LOG + e.getMessage());
        e.printStackTrace();
        return new TextResponseEntity<>(INTERNAL_SERVER_ERROR);
    }

    public ActivationsDAO getActivationsDAO() {
        return activationsDAO;
    }

    public AllianceHistoriesDAO getAllianceHistoriesDAO() {
        return allianceHistoriesDAO;
    }

    public AttackHistoriesDAO getAttackHistoriesDAO() {
        return attackHistoriesDAO;
    }

    public BuildingsDAO getBuildingsDAO() {
        return buildingsDAO;
    }

    public BuildingsDicDAO getBuildingsDicDAO() {
        return buildingsDicDAO;
    }

    public CurrentAlliancesDAO getCurrentAlliancesDAO() {
        return currentAlliancesDAO;
    }

    public CurrentAttacksDAO getCurrentAttacksDAO() {
        return currentAttacksDAO;
    }

    public FleetsDAO getFleetsDAO() {
        return fleetsDAO;
    }

    public LogsDAO getLogsDAO() {
        return logsDAO;
    }

    public PlanetFieldsDAO getPlanetFieldsDAO() {
        return planetFieldsDAO;
    }

    public PlanetsDAO getPlanetsDAO() {
        return planetsDAO;
    }

    public ResourcesDAO getResourcesDAO() {
        return resourcesDAO;
    }

    public UsersDAO getUsersDAO() {
        return usersDAO;
    }
}
