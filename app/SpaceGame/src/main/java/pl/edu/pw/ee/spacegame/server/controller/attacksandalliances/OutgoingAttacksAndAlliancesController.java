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

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.OUTGOING_ATTACKS_AND_ALLIANCES_LOG;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.OUTGOING_ATTACKS_AND_ALLIANCES_PATH;

/**
 * Created by Michał on 2016-05-05.
 * <p>
 * Przykładowa klasa do testowania i zabawy.
 * </p>
 */
@RestController
@CrossOrigin
@RequestMapping(OUTGOING_ATTACKS_AND_ALLIANCES_PATH)
public class OutgoingAttacksAndAlliancesController extends BaseAbstractComponent {

    @RequestMapping(method = POST)
    public ResponseEntity<?> getOutgoingData(@RequestBody AuthenticationData authenticationData) {
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
            OutgoingData incoming = getOutgoingData(usersEntity.getPlanet());
            databaseLogger.info(String.format(OUTGOING_ATTACKS_AND_ALLIANCES_LOG, usersEntity.getNickname()));
            return new JsonResponseEntity<>(incoming, HttpStatus.OK);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

    private OutgoingData getOutgoingData(PlanetsEntity planet) {
        OutgoingData outgoingData = new OutgoingData();
        switch (planet.getFleetStatus()) {
            case COMING_BACK_FROM_ATTACK: {
                CurrentAttacksEntity currentAttacksEntity = planet.getCurrentAttacks();
                PlanetsEntity helpedPlanet = planet.getLastAttackedPlanet();
                createOutgoingData(PlanetsEntity.FleetStatus.COMING_BACK_FROM_ATTACK, planet, outgoingData, helpedPlanet, currentAttacksEntity.getTimeOfSendingAttack().getTime());
                return outgoingData;
            }
            case COMING_BACK_FROM_HELP: {
                CurrentAlliancesEntity currentAlliancesEntity = planet.getCurrentAlliances();
                PlanetsEntity helpedPlanet = planet.getLastHelpedPlanet();
                createOutgoingData(PlanetsEntity.FleetStatus.COMING_BACK_FROM_HELP, planet, outgoingData, helpedPlanet, currentAlliancesEntity.getTimeOfComing().getTime());
                return outgoingData;
            }
            case ON_THE_WAY_TO_ATTACK: {
                CurrentAttacksEntity currentAttacksEntity = planet.getCurrentAttacks();
                createOutgoingData(PlanetsEntity.FleetStatus.ON_THE_WAY_TO_ATTACK, planet, outgoingData, currentAttacksEntity.getPlanetsByAttackedPlanetId(), currentAttacksEntity.getTimeOfSendingAttack().getTime());
                return outgoingData;
            }
            case ON_THE_WAY_TO_HELP: {
                CurrentAlliancesEntity currentAlliancesEntity = planet.getCurrentAlliances();
                createOutgoingData(PlanetsEntity.FleetStatus.ON_THE_WAY_TO_HELP, planet, outgoingData, currentAlliancesEntity.getPlanetsByHelpedPlanetId(), currentAlliancesEntity.getTimeOfComing().getTime());
                return outgoingData;
            }
        }
        return null;
    }

    private void createOutgoingData(PlanetsEntity.FleetStatus status, PlanetsEntity planet, OutgoingData outgoingData, PlanetsEntity otherPlanet, long time) {
        outgoingData.setNickname(otherPlanet.getUsersByUserId().getNickname());
        outgoingData.setCoordinateX(otherPlanet.getPlanetFieldsByPlanetFieldId().getCoordinateX());
        outgoingData.setCoordinateY(otherPlanet.getPlanetFieldsByPlanetFieldId().getCoordinateX());
        outgoingData.setSecondsOfWholeOperation(planet.getTimeDistanceFromOtherPlanet(otherPlanet) / 1000 + 1);
        outgoingData.setStatus(status.ordinal());
        long now = System.currentTimeMillis();
        long timeBetween = planet.getTimeDistanceFromOtherPlanet(otherPlanet);
        long toComeBack = timeBetween + time - now;
        outgoingData.setSecondsToCome(toComeBack / 1000 + 1);
    }

}
