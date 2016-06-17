package pl.edu.pw.ee.spacegame.server.controller.attacksandalliances;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.JsonResponseEntity;
import pl.edu.pw.ee.spacegame.server.controller.TextResponseEntity;
import pl.edu.pw.ee.spacegame.server.dao.crud.BaseAbstractComponent;
import pl.edu.pw.ee.spacegame.server.entity.CurrentAlliancesEntity;
import pl.edu.pw.ee.spacegame.server.entity.CurrentAttacksEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity;
import pl.edu.pw.ee.spacegame.server.entity.UsersEntity;
import pl.edu.pw.ee.spacegame.server.realtime.Refresher;
import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;
import pl.edu.pw.ee.spacegame.server.security.LoggedUsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.INCOMING_ATTACKS_AND_ALLIANCES_LOG;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.INCOMING_ATTACKS_AND_ALLIANCES_PATH;
import static pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity.FleetStatus.ON_THE_WAY_TO_ATTACK;
import static pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity.FleetStatus.ON_THE_WAY_TO_HELP;

/**
 * Created by Michał on 2016-05-05.
 * <p>
 * Przykładowa klasa do testowania i zabawy.
 * </p>
 */
@RestController
@CrossOrigin
@RequestMapping(INCOMING_ATTACKS_AND_ALLIANCES_PATH)
public class IncomingAttacksAndAlliancesController extends BaseAbstractComponent {

    @RequestMapping(method = POST)
    public ResponseEntity<?> getIncomingData(@RequestBody AuthenticationData authenticationData) {
        databaseLogger.setClass(getClass());
        try {
            if (!LoggedUsers.isLogged(authenticationData)) {
                return TextResponseEntity.getNotAuthorizedResponseEntity(authenticationData, databaseLogger);
            }
            UsersEntity usersEntity = usersDAO.getUserByNickname(authenticationData.getNickname());
            if (!usersEntity.getIsActivated()) {
                return TextResponseEntity.getNotActivatedResponseEntity(authenticationData, databaseLogger);
            }
            Refresher.refreshAll(this);
            ArrayList<IncomingData> incoming = getIncomingData(usersEntity.getPlanet());
            databaseLogger.info(String.format(INCOMING_ATTACKS_AND_ALLIANCES_LOG, usersEntity.getNickname()));
            return new JsonResponseEntity<>(incoming, HttpStatus.OK);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

    private ArrayList<IncomingData> getIncomingData(PlanetsEntity planet) {
        ArrayList<IncomingData> incomingDataList = new ArrayList<>();
        addAttacks(incomingDataList, planet.getCurrentAttacksesByPlanetId(), planet);
        addAlliances(incomingDataList, planet.getCurrentAlliancesByPlanetId(), planet);
        sortByTimeOfComing(incomingDataList);
        return incomingDataList;
    }

    private void sortByTimeOfComing(ArrayList<IncomingData> incomingDatas) {
        Collections.sort(incomingDatas, (o1, o2) -> o1.getSecondsToCome().compareTo(o2.getSecondsToCome()));
    }

    private void addAlliances(ArrayList<IncomingData> incomingDatas, Collection<CurrentAlliancesEntity> currentAlliancesByPlanetId, PlanetsEntity planet) {
        for (CurrentAlliancesEntity currentAlliancesEntity : currentAlliancesByPlanetId) {
            PlanetsEntity helpingPlanet = currentAlliancesEntity.getFleetsByFleetId().getPlanet();
            if (helpingPlanet.getFleetStatus().equals(ON_THE_WAY_TO_HELP)) {
                IncomingData incomingData = new IncomingData();
                incomingData.setIsAttack(false);
                createIncomingData(planet, incomingData, helpingPlanet, currentAlliancesEntity.getTimeOfSendingAlliance().getTime());
                incomingDatas.add(incomingData);
            }
        }
    }

    private void addAttacks(ArrayList<IncomingData> incomingDatas, Collection<CurrentAttacksEntity> currentAttacksesByPlanetId, PlanetsEntity planet) {
        for (CurrentAttacksEntity currentAttacksEntity : currentAttacksesByPlanetId) {
            PlanetsEntity attackingPlanet = currentAttacksEntity.getFleetsByFleetId().getPlanet();
            if (attackingPlanet.getFleetStatus().equals(ON_THE_WAY_TO_ATTACK)) {
                IncomingData incomingData = new IncomingData();
                incomingData.setIsAttack(true);
                createIncomingData(planet, incomingData, attackingPlanet, currentAttacksEntity.getTimeOfSendingAttack().getTime());
                incomingDatas.add(incomingData);
            }
        }
    }


    private void createIncomingData(PlanetsEntity planet, IncomingData incomingData, PlanetsEntity otherPlanet, long time) {
        incomingData.setNickname(otherPlanet.getUsersByUserId().getNickname());
        incomingData.setCoordinateX(otherPlanet.getPlanetFieldsByPlanetFieldId().getCoordinateX());
        incomingData.setCoordinateY(otherPlanet.getPlanetFieldsByPlanetFieldId().getCoordinateX());
        long now = System.currentTimeMillis();
        long timeBetween = planet.getTimeDistanceFromOtherPlanet(otherPlanet);
        long toComeBack = timeBetween + time - now;
        incomingData.setSecondsToCome(toComeBack / 1000 + 1);
    }

}
