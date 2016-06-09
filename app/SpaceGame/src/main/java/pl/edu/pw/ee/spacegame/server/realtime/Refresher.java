package pl.edu.pw.ee.spacegame.server.realtime;

import pl.edu.pw.ee.spacegame.server.dao.crud.PlanetsDAO;
import pl.edu.pw.ee.spacegame.server.dao.crud.ResourcesDAO;
import pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity;

/**
 * Created by Michał on 2016-06-07.
 */
public class Refresher {

    public static void refreshAllResources(PlanetsDAO planetsDAO, ResourcesDAO resourcesDAO) {
        for (PlanetsEntity planet : planetsDAO.findAll()) {
            ResourceRefresher.refreshResources(planet);
            resourcesDAO.save(planet.getResourcesByResourceId());
        }
    }

}