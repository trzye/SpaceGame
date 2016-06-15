package pl.edu.pw.ee.spacegame.server.realtime;

import pl.edu.pw.ee.spacegame.server.dao.crud.AllianceHistoriesDAO;
import pl.edu.pw.ee.spacegame.server.entity.AllianceHistoriesEntity;
import pl.edu.pw.ee.spacegame.server.entity.CurrentAlliancesEntity;
import pl.edu.pw.ee.spacegame.server.entity.FleetsEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity;

import java.sql.Timestamp;

import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.ONE_BYTE;

/**
 * Created by Michał on 2016-06-07.
 */
public class AlliancesRefresher {
    public static CurrentAlliancesEntity refreshOnTheWayToHelp(PlanetsEntity helpingPlanet, CurrentAlliancesEntity currentAlliances) {
        long now = System.currentTimeMillis();
        long timeOfSending = currentAlliances.getTimeOfSendingAlliance().getTime();
        long distance = helpingPlanet.getTimeDistanceFromOtherPlanet(currentAlliances.getPlanetsByHelpedPlanetId());
        if (timeOfSending + distance <= now) {
            currentAlliances.setTimeOfSendingAlliance(null);
            currentAlliances.setTimeOfComing(new Timestamp(timeOfSending + distance));
        }
        return currentAlliances;
    }

    public static CurrentAlliancesEntity refreshComingBackFromHelp(PlanetsEntity helpingPlanet, CurrentAlliancesEntity currentAlliances, AllianceHistoriesDAO historiesDAO) {
        long now = System.currentTimeMillis();
        long timeOfSending = currentAlliances.getTimeOfSendingAlliance().getTime();
        long distance = helpingPlanet.getTimeDistanceFromOtherPlanet(helpingPlanet.getLastHelpedPlanet());
        long helpTime = distance + timeOfSending;
        if (helpTime <= now) {
            createHistory(helpingPlanet, currentAlliances, historiesDAO, helpTime);
            currentAlliances.setTimeOfSendingAlliance(null);
            currentAlliances.setPlanetsByHelpedPlanetId(null);
        }
        return currentAlliances;
    }

    private static void createHistory(PlanetsEntity helpingPlanet, CurrentAlliancesEntity currentAlliances, AllianceHistoriesDAO historiesDAO, long helpTime) {
        AllianceHistoriesEntity attackHistoriesEntity = new AllianceHistoriesEntity();
        FleetsEntity fleetsEntity = helpingPlanet.getFleetsByFleetId();
        attackHistoriesEntity.setBombers(fleetsEntity.getBombers());
        attackHistoriesEntity.setIronclads(fleetsEntity.getIronclads());
        attackHistoriesEntity.setWarships(fleetsEntity.getWarships());
        attackHistoriesEntity.setPlanetsByHelpedPlanetId(currentAlliances.getPlanetsByHelpedPlanetId());
        attackHistoriesEntity.setUsersByUserId(helpingPlanet.getUsersByUserId());
        attackHistoriesEntity.setResult(ONE_BYTE);
        attackHistoriesEntity.setTime(new Timestamp(helpTime));
        historiesDAO.save(attackHistoriesEntity);
    }
}
