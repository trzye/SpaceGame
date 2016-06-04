package pl.edu.pw.ee.spacegame.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.dao.crud.*;
import pl.edu.pw.ee.spacegame.server.utils.DatabaseLogger;

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

}
