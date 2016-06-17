package pl.edu.pw.ee.spacegame.server.realtime;

import com.google.common.collect.Lists;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.spacegame.server.dao.crud.*;
import pl.edu.pw.ee.spacegame.server.entity.*;

import java.util.ArrayList;
import java.util.Collections;

import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.ACTIVATION_TIME_LIMIT;

/**
 * Created by Michał on 2016-06-07.
 */
public class Refresher {

    @Transactional
    public static void refreshAll(BaseAbstractComponent baseAbstractComponent) {
        //Usuwanie graczy, którzy przekroczyli czas aktywacji
        refreshActivations(baseAbstractComponent.getActivationsDAO(), baseAbstractComponent.getPlanetFieldsDAO(), baseAbstractComponent.getUsersDAO());
        //Najpierw odśwież zasoby wszystkich graczy
        refreshAllResources(baseAbstractComponent.getPlanetsDAO(), baseAbstractComponent.getResourcesDAO());
        //Teraz odśwież wsparcia, aby wiedzieć gdzie przebywają wszystkie floty (poza tymi w drodze do ataku/wsparcia)
        refreshAllAlliances(baseAbstractComponent.getPlanetsDAO(), baseAbstractComponent.getCurrentAlliancesDAO(), baseAbstractComponent.getAllianceHistoriesDAO());
        //Teraz clue wszystkiego -> czas na walkę
        refreshAllAttacks(baseAbstractComponent);
    }

    private static void refreshActivations(ActivationsDAO activationsDAO, PlanetFieldsDAO planetFieldsDAO, UsersDAO usersDAO) {
        for (ActivationsEntity activationsEntity : activationsDAO.findAll()) {
            PlanetFieldsEntity fieldsEntity = activationsEntity.getPlanetFieldsByPlanetFieldId();
            UsersEntity usersEntity = activationsEntity.getUsersByUserId();
            if (fieldsEntity.getStatus().equals(PlanetFieldsEntity.Status.LOCKED)) {
                long now = System.currentTimeMillis();
                long between = now - activationsEntity.getTime().getTime();
                if (between > ACTIVATION_TIME_LIMIT) {
                    activationsDAO.delete(activationsEntity);
                    planetFieldsDAO.delete(fieldsEntity);
                    usersDAO.delete(usersEntity);
                }
            }
        }
    }

    private static void refreshAllAttacks(BaseAbstractComponent baseAbstractComponent) {
        AllAttackRefresher allAttackRefresher = new AllAttackRefresher(baseAbstractComponent);
        ArrayList<PlanetsEntity> planets = getPlanetsSortedByAttackTime(baseAbstractComponent);
        for (PlanetsEntity planetsEntity : planets) {
            allAttackRefresher.refreshAndSaveByPlanet(planetsEntity);
        }
    }

    private static ArrayList<PlanetsEntity> getPlanetsSortedByAttackTime(BaseAbstractComponent baseAbstractComponent) {
        ArrayList<PlanetsEntity> planets = Lists.newArrayList(baseAbstractComponent.getPlanetsDAO().findAll());
        Collections.sort(planets, (o1, o2) -> {
            CurrentAttacksEntity c1 = o1.getCurrentAttacks();
            CurrentAttacksEntity c2 = o2.getCurrentAttacks();
            if ((c1.getTimeOfSendingAttack() != null) && (c2.getTimeOfSendingAttack() != null)) {
                long c1AttackTime = c1.getTimeOfSendingAttack().getTime() + c1.getPlanetsByAttackedPlanetId().getTimeDistanceFromOtherPlanet(o1);
                long c2AttackTime = c2.getTimeOfSendingAttack().getTime() + c2.getPlanetsByAttackedPlanetId().getTimeDistanceFromOtherPlanet(o2);
                return (int) (c2AttackTime - c1AttackTime);
            } else return 0;
        });
        return planets;
    }

    private static void refreshAllAlliances(PlanetsDAO planetsDAO, CurrentAlliancesDAO currentAlliancesDAO, AllianceHistoriesDAO allianceHistoriesDAO) {
        for (PlanetsEntity planet : planetsDAO.findAll()) {
            PlanetsEntity.FleetStatus status = planet.getFleetStatus();
            if (status.equals(PlanetsEntity.FleetStatus.ON_THE_WAY_TO_HELP)) {
                CurrentAlliancesEntity currentAlliancesEntity = AlliancesRefresher.refreshOnTheWayToHelp(planet, planet.getCurrentAlliances());
                currentAlliancesDAO.save(currentAlliancesEntity);
            }
            if (status.equals(PlanetsEntity.FleetStatus.COMING_BACK_FROM_HELP)) {
                CurrentAlliancesEntity currentAlliancesEntity = AlliancesRefresher.refreshComingBackFromHelp(planet, planet.getCurrentAlliances(), allianceHistoriesDAO);
                currentAlliancesDAO.save(currentAlliancesEntity);
            }
        }
    }

    private static void refreshAllResources(PlanetsDAO planetsDAO, ResourcesDAO resourcesDAO) {
        for (PlanetsEntity planet : planetsDAO.findAll()) {
            ResourceRefresher.refreshResources(planet);
            resourcesDAO.save(planet.getResourcesByResourceId());
        }
    }


}
