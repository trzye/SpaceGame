package pl.edu.pw.ee.spacegame.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.dao.crud.*;
import pl.edu.pw.ee.spacegame.server.utils.DatabaseLogger;

import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.REQUEST_ERROR;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.UNEXPECTED_ERROR;

/**
 * Created by Micha³ on 2016-06-04.
 */
@RestController
public abstract class AutowiredController {

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

    protected ResponseEntity<?> getResponseEntity(IOException e) {
        databaseLogger.error(UNEXPECTED_ERROR + e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

    protected ResponseEntity<?> getResponseEntity(Exception e) {
        String error = REQUEST_ERROR + e.getMessage();
        databaseLogger.error(error);
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
    }
}
