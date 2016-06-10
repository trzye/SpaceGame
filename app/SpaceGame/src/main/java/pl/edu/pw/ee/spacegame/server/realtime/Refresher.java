package pl.edu.pw.ee.spacegame.server.realtime;

import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.spacegame.server.controller.BaseAbstractController;
import pl.edu.pw.ee.spacegame.server.dao.crud.PlanetsDAO;
import pl.edu.pw.ee.spacegame.server.dao.crud.ResourcesDAO;
import pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity;

/**
 * Created by Michał on 2016-06-07.
 */
public class Refresher {

    @Transactional
    public static void refreshAll(BaseAbstractController baseAbstractController) {
        refreshAllResources(baseAbstractController.getPlanetsDAO(), baseAbstractController.getResourcesDAO());
    }

    private static void refreshAllResources(PlanetsDAO planetsDAO, ResourcesDAO resourcesDAO) {
        for (PlanetsEntity planet : planetsDAO.findAll()) {
            ResourceRefresher.refreshResources(planet);
            resourcesDAO.save(planet.getResourcesByResourceId());
        }
    }

}
