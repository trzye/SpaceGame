package pl.edu.pw.ee.spacegame.server.game;

import pl.edu.pw.ee.spacegame.server.entity.BuildingsEntity;

/**
 * Created by Michał on 2016-06-10.
 */
public class FleetCosts {

    public static final Integer BASE_WARSHIP_COST = 20;
    public static final Integer BASE_BOMBER_COST = 40;
    public static final Integer BASE_IRONCLADS_COST = 80;

    public static Integer getWarshipCost(BuildingsEntity hangar) {
        return (int) (BASE_WARSHIP_COST * (1.0 - 0.05 * hangar.getLevel()));
    }

    public static Integer getBomberCost(BuildingsEntity hangar) {
        return (int) (BASE_BOMBER_COST * (1.0 - 0.05 * hangar.getLevel()));
    }

    public static Integer getIroncladsCost(BuildingsEntity hangar) {
        return (int) (BASE_IRONCLADS_COST * (1.0 - 0.05 * hangar.getLevel()));
    }
}
