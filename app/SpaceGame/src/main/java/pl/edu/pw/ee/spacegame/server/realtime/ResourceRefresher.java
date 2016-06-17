package pl.edu.pw.ee.spacegame.server.realtime;

import pl.edu.pw.ee.spacegame.server.entity.BuildingsEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity;
import pl.edu.pw.ee.spacegame.server.entity.ResourcesEntity;

import java.sql.Timestamp;

import static pl.edu.pw.ee.spacegame.server.game.GameBalanceSettings.MILLISECONDS_FOR_ONE_RESOURCE_UNIT;

/**
 * Created by Michał on 2016-06-07.
 */
public class ResourceRefresher {

    public static void refreshResources(PlanetsEntity planet) {
        ResourcesEntity resource = planet.getResourcesByResourceId();
        BuildingsEntity ununtriumMine = planet.getUnuntriumMine();
        BuildingsEntity gadolinMine = planet.getGadolinMine();
        long now = System.currentTimeMillis();
        long timeBetween = (now - resource.getLastUpdate().getTime()) / MILLISECONDS_FOR_ONE_RESOURCE_UNIT;
        if (timeBetween > 0) {
            resource.setUnuntrium(resource.getUnuntrium() + (int) timeBetween * ununtriumMine.getLevel());
            resource.setGadolin(resource.getGadolin() + (int) timeBetween * gadolinMine.getLevel());
            resource.setLastUpdate(new Timestamp(now));
        }
    }
}
