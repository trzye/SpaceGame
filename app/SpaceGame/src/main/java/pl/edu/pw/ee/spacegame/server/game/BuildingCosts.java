package pl.edu.pw.ee.spacegame.server.game;

import pl.edu.pw.ee.spacegame.server.entity.BuildingsEntity;

/**
 * Created by Michał on 2016-06-10.
 */
public class BuildingCosts {

    private static final Integer UNUNTRIUM_COST = 120;
    private static final Integer GADOLIN_COST = 150;
    private static final Integer HANGAR_COST = 120;
    private static final Integer DEFENCE_COST = 100;

    public static Integer getUnuntriumMineCost(BuildingsEntity ununtriumMine) {
        return ununtriumMine.getLevel() * UNUNTRIUM_COST;
    }

    public static Integer getGadolinMineCost(BuildingsEntity gadolinMine) {
        return gadolinMine.getLevel() * GADOLIN_COST;
    }

    public static Integer getHangarCost(BuildingsEntity hangar) {
        return hangar.getLevel() * HANGAR_COST;
    }

    public static Integer getDefenceSystemsCost(BuildingsEntity defenceSystems) {
        return defenceSystems.getLevel() * DEFENCE_COST;
    }

}
