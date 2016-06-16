package pl.edu.pw.ee.spacegame.server.controller.attacksandalliances;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.BaseAbstractController;
import pl.edu.pw.ee.spacegame.server.controller.JsonResponseEntity;
import pl.edu.pw.ee.spacegame.server.controller.TextResponseEntity;
import pl.edu.pw.ee.spacegame.server.entity.CurrentAlliancesEntity;
import pl.edu.pw.ee.spacegame.server.entity.FleetsEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity;
import pl.edu.pw.ee.spacegame.server.entity.UsersEntity;
import pl.edu.pw.ee.spacegame.server.realtime.Refresher;
import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;
import pl.edu.pw.ee.spacegame.server.security.LoggedUsers;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.ALLIANCES_ON_MY_PLANET_LOG;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.ALLIANCES_ON_MY_PLANET_PATH;
import static pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity.FleetStatus.ON_THE_OTHER_PLANET;

/**
 * Created by Michał on 2016-05-05.
 * <p>
 * Przykładowa klasa do testowania i zabawy.
 * </p>
 */
@RestController
@CrossOrigin
@RequestMapping(ALLIANCES_ON_MY_PLANET_PATH)
public class AlliancesOnMyPlanetController extends BaseAbstractController {

    @RequestMapping(method = POST)
    public ResponseEntity<?> getAlliancesData(@RequestBody AuthenticationData authenticationData) {
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
            AlliancesOnPlanetData alliances = getAlliancesData(usersEntity.getPlanet());
            databaseLogger.info(String.format(ALLIANCES_ON_MY_PLANET_LOG, usersEntity.getNickname()));
            return new JsonResponseEntity<>(alliances, HttpStatus.OK);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

    private AlliancesOnPlanetData getAlliancesData(PlanetsEntity planet) {
        AlliancesOnPlanetData alliancesOnPlanetData = new AlliancesOnPlanetData();
        alliancesOnPlanetData.setBombers(0);
        alliancesOnPlanetData.setIronclads(0);
        alliancesOnPlanetData.setWarships(0);
        for (CurrentAlliancesEntity currentAlliancesEntity : planet.getCurrentAlliancesByPlanetId()) {
            if (currentAlliancesEntity.getFleetsByFleetId().getPlanet().getFleetStatus().equals(ON_THE_OTHER_PLANET)) {
                addAlliances(alliancesOnPlanetData, currentAlliancesEntity.getFleetsByFleetId());
            }
        }
        return alliancesOnPlanetData;
    }

    private void addAlliances(AlliancesOnPlanetData alliancesOnPlanetData, FleetsEntity fleetsByFleetId) {
        alliancesOnPlanetData.setWarships(alliancesOnPlanetData.getWarships() + fleetsByFleetId.getWarships());
        alliancesOnPlanetData.setBombers(alliancesOnPlanetData.getBombers() + fleetsByFleetId.getBombers());
        alliancesOnPlanetData.setWarships(alliancesOnPlanetData.getIronclads() + fleetsByFleetId.getIronclads());
    }


}
