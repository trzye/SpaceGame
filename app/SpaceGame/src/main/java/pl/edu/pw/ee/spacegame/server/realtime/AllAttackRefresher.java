package pl.edu.pw.ee.spacegame.server.realtime;

import pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects;
import pl.edu.pw.ee.spacegame.server.dao.crud.BaseAbstractComponent;
import pl.edu.pw.ee.spacegame.server.entity.*;

import java.sql.Timestamp;
import java.util.ArrayList;

import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.ZERO_BYTE;
import static pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity.FleetStatus.*;
import static pl.edu.pw.ee.spacegame.server.game.GameBalanceSettings.*;

/**
 * Created by Michał on 2016-06-15.
 */
public class AllAttackRefresher {

    private BaseAbstractComponent daoContainer;
    private long attackTime;
    private int attack;


    public AllAttackRefresher(BaseAbstractComponent daoContainer) {
        this.daoContainer = daoContainer;
    }

    public void refreshAndSaveByPlanet(PlanetsEntity planetsEntity) {
        if (planetsEntity.getFleetStatus().equals(ON_THE_WAY_TO_ATTACK)) {
            refreshByOnTheWayToAttack(planetsEntity);
        }
        if (planetsEntity.getFleetStatus().equals(COMING_BACK_FROM_ATTACK)) {
            refreshByComingBackFromAttack(planetsEntity);
        }
    }

    private void refreshByOnTheWayToAttack(PlanetsEntity planetsEntity) {
        CurrentAttacksEntity currentAttacksEntity = planetsEntity.getCurrentAttacks();
        long now = System.currentTimeMillis();
        long timeOfSending = currentAttacksEntity.getTimeOfSendingAttack().getTime();
        long distance = planetsEntity.getTimeDistanceFromOtherPlanet(currentAttacksEntity.getPlanetsByAttackedPlanetId());
        attackTime = timeOfSending + distance;
        if (timeOfSending + distance <= now) {
            resolveAttack(planetsEntity, currentAttacksEntity.getPlanetsByAttackedPlanetId());
        }
    }

    private void resolveAttack(PlanetsEntity attacker, PlanetsEntity defender) {
        ArrayList<PlanetsEntity> defenderCurrentAlliances = daoContainer.getPlanetsDAO().getCurrentAlliances(defender.getPlanetId());
        filterByAttackTime(defenderCurrentAlliances); //to że jacyś sojusznicy są na planecie, nie znaczy, że byli w czasie ataku z przeszłości
        if (defender.getFleetStatus().equals(ON_THE_MOTHER_PLANET))
            defenderCurrentAlliances.add(defender);
        int defence = calculateDefence(defender.getDefenceSystems(), defenderCurrentAlliances);
        attack = calculateAttack(attacker.getFleetsByFleetId());
        if (attack > defence) {
            resolveWin(attacker, defender, defenderCurrentAlliances);
        } else {
            resolveDefeat(attacker, defender);
        }
    }

    private void filterByAttackTime(ArrayList<PlanetsEntity> defenderCurrentAlliances) {
        ArrayList<PlanetsEntity> toRemove = new ArrayList<>();
        for (PlanetsEntity planetsEntity : defenderCurrentAlliances) {
            if (planetsEntity.getCurrentAlliances().getTimeOfComing().getTime() > attackTime) {
                toRemove.add(planetsEntity);
            }
        }
        defenderCurrentAlliances.removeAll(toRemove);
    }

    private void resolveWin(PlanetsEntity attacker, PlanetsEntity defender, ArrayList<PlanetsEntity> defenderCurrentAlliances) {
        for (PlanetsEntity allyPlanet : defenderCurrentAlliances) {
            deleteFleet(allyPlanet.getFleetsByFleetId());
            backToMotherPlanet(allyPlanet, defender);
            allianceHistory(allyPlanet, defender);
        }
        stealResources(attacker, defender);
        backToMotherPlanet(attacker);
    }

    private void backToMotherPlanet(PlanetsEntity attacker) {
        CurrentAttacksEntity attacksEntity = attacker.getCurrentAttacks();
        attacksEntity.setTimeOfSendingAttack(new Timestamp(attackTime));
        attacksEntity.setPlanetsByAttackedPlanetId(attacker);
        daoContainer.getCurrentAttacksDAO().save(attacksEntity);
    }

    private void stealResources(PlanetsEntity attacker, PlanetsEntity defender) {
        ResourcesEntity attackerResources = attacker.getResourcesByResourceId();
        ResourcesEntity defendersResources = defender.getResourcesByResourceId();
        int ununtrium = defendersResources.getUnuntrium() - attack > 0 ? defendersResources.getUnuntrium() - attack : 0;
        int gadolin = defendersResources.getGadolin() - attack > 0 ? defendersResources.getGadolin() - attack : 0;
        defendersResources.setUnuntrium(ununtrium);
        defendersResources.setGadolin(gadolin);
        attackerResources.setUnuntrium(attackerResources.getUnuntrium() + ununtrium);
        attackerResources.setGadolin(attackerResources.getGadolin() + gadolin);
        daoContainer.getResourcesDAO().save(defendersResources);
        daoContainer.getResourcesDAO().save(attackerResources);
        attackHistory(attacker, defender, ununtrium, gadolin, ControllerConstantObjects.ONE_BYTE);
    }

    private void backToMotherPlanet(PlanetsEntity allyPlanet, PlanetsEntity defender) {
        if (allyPlanet != defender) {
            CurrentAlliancesEntity alliancesEntity = allyPlanet.getCurrentAlliances();
            alliancesEntity.setTimeOfSendingAlliance(null);
            alliancesEntity.setPlanetsByHelpedPlanetId(null);
            daoContainer.getCurrentAlliancesDAO().save(alliancesEntity);
        }
    }

    private void allianceHistory(PlanetsEntity allyPlanet, PlanetsEntity defender) {
        if (allyPlanet != defender) {
            FleetsEntity fleetsEntity = allyPlanet.getFleetsByFleetId();
            AllianceHistoriesEntity allianceHistoriesEntity = new AllianceHistoriesEntity();
            allianceHistoriesEntity.setBombers(fleetsEntity.getBombers());
            allianceHistoriesEntity.setIronclads(fleetsEntity.getIronclads());
            allianceHistoriesEntity.setWarships(fleetsEntity.getWarships());
            allianceHistoriesEntity.setPlanetsByHelpedPlanetId(defender);
            allianceHistoriesEntity.setUsersByUserId(allyPlanet.getUsersByUserId());
            allianceHistoriesEntity.setResult(ZERO_BYTE);
            allianceHistoriesEntity.setTime(new Timestamp(attackTime));
            daoContainer.getAllianceHistoriesDAO().save(allianceHistoriesEntity);
        }
    }

    private void resolveDefeat(PlanetsEntity attacker, PlanetsEntity defender) {
        FleetsEntity fleetsEntity = attacker.getFleetsByFleetId();

        //dodaję do historii ataków
        attackHistory(attacker, defender, null, null, ControllerConstantObjects.ZERO_BYTE);

        //usuwam jednostki
        deleteFleet(fleetsEntity);

        //zmieniam dane aktualnego ataku
        CurrentAttacksEntity attacksEntity = attacker.getCurrentAttacks();
        attacksEntity.setPlanetsByAttackedPlanetId(null);
        attacksEntity.setTimeOfSendingAttack(null);
        daoContainer.getCurrentAttacksDAO().save(attacksEntity);
    }

    private void attackHistory(PlanetsEntity attacker, PlanetsEntity defender, Integer gadolin, Integer ununtium, Byte result) {
        FleetsEntity fleetsEntity = attacker.getFleetsByFleetId();
        AttackHistoriesEntity attackHistoriesEntity = new AttackHistoriesEntity();
        attackHistoriesEntity.setBombers(fleetsEntity.getBombers());
        attackHistoriesEntity.setIronclads(fleetsEntity.getIronclads());
        attackHistoriesEntity.setWarships(fleetsEntity.getWarships());
        attackHistoriesEntity.setPlanetsByAttackedPlanetId(defender);
        attackHistoriesEntity.setUsersByUserId(attacker.getUsersByUserId());
        attackHistoriesEntity.setGadolin(gadolin);
        attackHistoriesEntity.setUnuntrium(ununtium);
        attackHistoriesEntity.setResult(result);
        attackHistoriesEntity.setTime(new Timestamp(attackTime));
        daoContainer.getAttackHistoriesDAO().save(attackHistoriesEntity);
    }

    private void deleteFleet(FleetsEntity fleetsEntity) {
        fleetsEntity.setBombers(0);
        fleetsEntity.setIronclads(0);
        fleetsEntity.setWarships(0);
        daoContainer.getFleetsDAO().save(fleetsEntity);
    }

    private int calculateDefence(BuildingsEntity defenceSystems, ArrayList<PlanetsEntity> defenderCurrentAlliances) {
        int defence = 0;
        defence += defenceSystems.getLevel() * DEFENCE_SYSTEMS_ATTACK;
        for (PlanetsEntity planet : defenderCurrentAlliances) {
            defence += calculateAttack(planet.getFleetsByFleetId());
        }
        return defence;
    }

    private int calculateAttack(FleetsEntity fleet) {
        return WARSHIP_ATTACK * fleet.getWarships() + BOMBER_ATTACK * fleet.getBombers() + IRONCLADS_ATTACK * fleet.getIronclads();
    }

    private void refreshByComingBackFromAttack(PlanetsEntity planetsEntity) {
        CurrentAttacksEntity currentAttacksEntity = planetsEntity.getCurrentAttacks();
        long now = System.currentTimeMillis();
        long timeOfSending = currentAttacksEntity.getTimeOfSendingAttack().getTime();
        long distance = planetsEntity.getTimeDistanceFromOtherPlanet(planetsEntity.getLastAttackedPlanet());
        if (timeOfSending + distance <= now) {
            currentAttacksEntity.setTimeOfSendingAttack(null);
            currentAttacksEntity.setPlanetsByAttackedPlanetId(null);
        }
        daoContainer.getCurrentAttacksDAO().save(currentAttacksEntity);
    }
}
