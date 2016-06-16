package pl.edu.pw.ee.spacegame.server.controller.fleets;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.BaseAbstractController;
import pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects;
import pl.edu.pw.ee.spacegame.server.controller.JsonResponseEntity;
import pl.edu.pw.ee.spacegame.server.controller.TextResponseEntity;
import pl.edu.pw.ee.spacegame.server.entity.AllianceHistoriesEntity;
import pl.edu.pw.ee.spacegame.server.entity.CurrentAlliancesEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity;
import pl.edu.pw.ee.spacegame.server.entity.UsersEntity;
import pl.edu.pw.ee.spacegame.server.realtime.Refresher;
import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;
import pl.edu.pw.ee.spacegame.server.security.LoggedUsers;

import java.io.IOException;
import java.sql.Timestamp;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.*;

/**
 * Created by Michał on 2016-05-05.
 */
@RestController
@CrossOrigin
@RequestMapping(GET_BACK_FLEET_PATH)
public class GetBackFleetController extends BaseAbstractController {


    @RequestMapping(method = POST)
    public ResponseEntity<?> getBackFleet(@RequestBody AuthenticationData authenticationData) {
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
            GetBackData getBackData = getBackFleet(usersEntity.getPlanet());
            databaseLogger.info(String.format(GET_BACK_FLEET_LOG, usersEntity.getNickname()));
            return new JsonResponseEntity<>(getBackData, HttpStatus.OK);
        } catch (IOException e) {
            return handleBadRequest(e);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

    @Transactional
    private GetBackData getBackFleet(PlanetsEntity planet) throws IOException {
        if (planet.getFleetStatus().equals(PlanetsEntity.FleetStatus.ON_THE_OTHER_PLANET)) {
            createAllianceHistory(planet);
            return backToMotherPlanet(planet);
        } else {
            throw new IOException(CANT_GET_BACK_FLEET);
        }
    }

    private GetBackData backToMotherPlanet(PlanetsEntity planet) {
        GetBackData getBackData = new GetBackData();
        CurrentAlliancesEntity currentAlliances = planet.getCurrentAlliances();
        getBackData.setSecondsToComeBack
                (planet.getTimeDistanceFromOtherPlanet(currentAlliances.getPlanetsByHelpedPlanetId()) / 1000 + 1); //chcemy sekundy (dodaję jeden dla edge cas'ow)
        currentAlliances.setTimeOfSendingAlliance(new Timestamp(System.currentTimeMillis()));
        currentAlliances.setPlanetsByHelpedPlanetId(planet);
        currentAlliancesDAO.save(currentAlliances);
        return getBackData;
    }

    private void createAllianceHistory(PlanetsEntity planet) {
        CurrentAlliancesEntity alliancesEntity = planet.getCurrentAlliances();
        AllianceHistoriesEntity historiesEntity = new AllianceHistoriesEntity();
        historiesEntity.setPlanetsByHelpedPlanetId(alliancesEntity.getPlanetsByHelpedPlanetId());
        historiesEntity.setBombers(planet.getFleetsByFleetId().getBombers());
        historiesEntity.setIronclads(planet.getFleetsByFleetId().getIronclads());
        historiesEntity.setWarships(planet.getFleetsByFleetId().getWarships());
        historiesEntity.setResult(ControllerConstantObjects.ONE_BYTE);
        historiesEntity.setTime(new Timestamp(System.currentTimeMillis()));
        historiesEntity.setUsersByUserId(planet.getUsersByUserId());
        allianceHistoriesDAO.save(historiesEntity);
    }

}
