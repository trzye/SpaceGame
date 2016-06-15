package pl.edu.pw.ee.spacegame.server.realtime;

import com.google.common.collect.Lists;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pw.ee.spacegame.server.controller.BaseAbstractController;
import pl.edu.pw.ee.spacegame.server.controller.fleets.FleetLogic;
import pl.edu.pw.ee.spacegame.server.dao.crud.AllianceHistoriesDAO;
import pl.edu.pw.ee.spacegame.server.dao.crud.CurrentAlliancesDAO;
import pl.edu.pw.ee.spacegame.server.dao.crud.PlanetsDAO;
import pl.edu.pw.ee.spacegame.server.dao.crud.ResourcesDAO;
import pl.edu.pw.ee.spacegame.server.entity.CurrentAlliancesEntity;
import pl.edu.pw.ee.spacegame.server.entity.CurrentAttacksEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Michał on 2016-06-07.
 */
public class Refresher {

    @Transactional
    public static void refreshAll(BaseAbstractController baseAbstractController) {
        //Najpierw odśwież zasoby wszystkich graczy
        refreshAllResources(baseAbstractController.getPlanetsDAO(), baseAbstractController.getResourcesDAO());
        //Teraz odśwież wsparcia, aby wiedzieć gdzie przebywają wszystkie floty (poza tymi w drodze do ataku/wsparcia)
        refreshAllAlliances(baseAbstractController.getPlanetsDAO(), baseAbstractController.getCurrentAlliancesDAO(), baseAbstractController.getAllianceHistoriesDAO());
        //Teraz clue wszystkiego -> czas na walkę
        refreshAllAttacks(baseAbstractController);
    }

    private static void refreshAllAttacks(BaseAbstractController baseAbstractController) {
        AllAttackRefresher allAttackRefresher = new AllAttackRefresher(baseAbstractController);
        ArrayList<PlanetsEntity> planets = getPlanetsSortedByAttackTime(baseAbstractController);
        for (PlanetsEntity planetsEntity : planets) {
            allAttackRefresher.refreshAndSaveByPlanet(planetsEntity);
        }
    }

    private static ArrayList<PlanetsEntity> getPlanetsSortedByAttackTime(BaseAbstractController baseAbstractController) {
        ArrayList<PlanetsEntity> planets = Lists.newArrayList(baseAbstractController.getPlanetsDAO().findAll());
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
            FleetLogic.FleetStatus status = FleetLogic.getStatus(planet);
            if (status.equals(FleetLogic.FleetStatus.ON_THE_WAY_TO_HELP)) {
                CurrentAlliancesEntity currentAlliancesEntity = AlliancesRefresher.refreshOnTheWayToHelp(planet, planet.getCurrentAlliances());
                currentAlliancesDAO.save(currentAlliancesEntity);
            }
            if (status.equals(FleetLogic.FleetStatus.COMMING_BACK_FROM_HELP)) {
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
