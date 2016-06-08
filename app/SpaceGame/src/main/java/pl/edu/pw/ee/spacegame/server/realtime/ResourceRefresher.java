package pl.edu.pw.ee.spacegame.server.realtime;

import pl.edu.pw.ee.spacegame.server.entity.BuildingsEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity;
import pl.edu.pw.ee.spacegame.server.entity.ResourcesEntity;

/**
 * Created by Michał on 2016-06-07.
 */
public class ResourceRefresher {
    public static void refreshResources(PlanetsEntity planet) {
        ResourcesEntity resource = planet.getResourcesByResourceId();
        BuildingsEntity ununtriumMine = planet.getUnuntriumMine();
        BuildingsEntity gadolinMine = planet.getGadolinMine();
        long now = System.currentTimeMillis();
        long secondsminutesBeetween = (now - resource.getLastUpdate().getTime()) / 1000 / 60;
        resource.setUnuntrium(resource.getUnuntrium() + (int) secondsminutesBeetween * ununtriumMine.getLevel());
        resource.setGadolin(resource.getGadolin() + (int) secondsminutesBeetween * gadolinMine.getLevel());
    }
}
