package pl.edu.pw.ee.spacegame.server.controller.fleets;

import pl.edu.pw.ee.spacegame.server.entity.BuildingsEntity;
import pl.edu.pw.ee.spacegame.server.entity.CurrentAlliancesEntity;
import pl.edu.pw.ee.spacegame.server.entity.CurrentAttacksEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity;

/**
 * Created by Michał on 2016-06-10.
 */
public class FleetLogic {

    public static final Integer BASE_WARSHIP_COST = 20;
    public static final Integer BASE_BOMBER_COST = 40;
    public static final Integer BASE_IRONCLADS_COST = 80;

    public enum FleetStatus {
        ON_THE_MOTHER_PLANET,
        ON_THE_WAY_TO_ATTACK,
        COMMING_BACK_FROM_ATTACK,
        ON_THE_WAY_TO_HELP,
        COMMING_BACK_FROM_HELP,
        ON_THE_OTHER_PLANET
    }

    public static Integer getWarshipCost(BuildingsEntity hangar) {
        return BASE_WARSHIP_COST * (int) (1.0 - 0.05 * hangar.getLevel());
    }

    public static Integer getBomberCost(BuildingsEntity hangar) {
        return BASE_BOMBER_COST * (int) (1.0 - 0.05 * hangar.getLevel());
    }

    public static Integer getIroncladsCost(BuildingsEntity hangar) {
        return BASE_IRONCLADS_COST * (int) (1.0 - 0.05 * hangar.getLevel());
    }

    public static FleetStatus getStatus(PlanetsEntity planetsEntity, CurrentAlliancesEntity currentAlliancesEntity, CurrentAttacksEntity currentAttacksEntity) {
        if (currentAttacksEntity.getTimeOfSendingAttack() != null) {
            if (currentAttacksEntity.getPlanetsByAttackedPlanetId() != planetsEntity) {
                return FleetStatus.ON_THE_WAY_TO_ATTACK;
            } else {
                return FleetStatus.COMMING_BACK_FROM_ATTACK;
            }
        }
        if (currentAlliancesEntity.getTimeOfSendingAlliance() != null) {
            if (currentAlliancesEntity.getPlanetsByHelpedPlanetId() != planetsEntity) {
                return FleetStatus.ON_THE_WAY_TO_HELP;
            } else {
                return FleetStatus.COMMING_BACK_FROM_HELP;
            }
        }
        if (currentAlliancesEntity.getPlanetsByHelpedPlanetId() != null) {
            return FleetStatus.ON_THE_OTHER_PLANET;
        }
        return FleetStatus.ON_THE_MOTHER_PLANET;
    }
}
