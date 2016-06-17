package pl.edu.pw.ee.spacegame.server.controller.otherplanet;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.JsonResponseEntity;
import pl.edu.pw.ee.spacegame.server.controller.TextResponseEntity;
import pl.edu.pw.ee.spacegame.server.dao.crud.BaseAbstractComponent;
import pl.edu.pw.ee.spacegame.server.entity.CurrentAttacksEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetFieldsEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity;
import pl.edu.pw.ee.spacegame.server.entity.UsersEntity;
import pl.edu.pw.ee.spacegame.server.realtime.Refresher;
import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;
import pl.edu.pw.ee.spacegame.server.security.LoggedUsers;

import java.io.IOException;
import java.sql.Timestamp;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.*;
import static pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity.FleetStatus.ON_THE_MOTHER_PLANET;

/**
 * Created by KTamo_000 on 2016-06-14.
 */

@RestController
@CrossOrigin
@RequestMapping(ATTACK_PATH)
public class AttackController extends BaseAbstractComponent {

    @RequestMapping(method = POST)
    public ResponseEntity<?> attack(@RequestBody OtherPlanetData otherPlanetData) {
        databaseLogger.setClass(getClass());
        try {
            AuthenticationData authenticationData = otherPlanetData.getAuthenticationData();
            if (!LoggedUsers.isLogged(authenticationData)) {
                return TextResponseEntity.getNotAuthorizedResponseEntity(authenticationData, databaseLogger);
            }
            UsersEntity usersEntity = usersDAO.getUserByNickname(authenticationData.getNickname());
            if (!usersEntity.getIsActivated()) {
                return TextResponseEntity.getNotActivatedResponseEntity(authenticationData, databaseLogger);
            }
            Refresher.refreshAll(this);
            AttackData attackData = attack(otherPlanetData, usersEntity.getPlanet());
            return new JsonResponseEntity<>(attackData, OK);
        } catch (IOException e) {
            return handleBadRequest(e);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

    @Transactional
    private AttackData attack(OtherPlanetData otherPlanetData, PlanetsEntity planet) throws IOException {
        PlanetFieldsEntity planetFieldEntity = getPlanetFieldsDAO().getPlanetByXandY(otherPlanetData.getCoordinateX(), otherPlanetData.getCoordinateY());
        PlanetsEntity otherPlanet = handleExceptions(planet, planetFieldEntity);
        setAttackingStatus(planet, otherPlanet);
        databaseLogger.info(String.format(ATTACK_PLANET_LOG, planet.getUsersByUserId().getNickname(), otherPlanet.getUsersByUserId().getNickname()));
        return createAttackData(planet, otherPlanet);
    }

    private AttackData createAttackData(PlanetsEntity planet, PlanetsEntity otherPlanet) {
        AttackData attackData = new AttackData();
        attackData.setSecondsToAttack(planet.getTimeDistanceFromOtherPlanet(otherPlanet) / 1000 + 1);
        attackData.setSecondsToComeBack(attackData.getSecondsToAttack() * 2);
        return attackData;
    }

    private void setAttackingStatus(PlanetsEntity planet, PlanetsEntity otherPlanet) {
        CurrentAttacksEntity currentAttacksEntity = planet.getCurrentAttacks();
        currentAttacksEntity.setPlanetsByAttackedPlanetId(otherPlanet);
        currentAttacksEntity.setTimeOfSendingAttack(new Timestamp(System.currentTimeMillis()));
        currentAttacksDAO.save(currentAttacksEntity);
    }

    private PlanetsEntity handleExceptions(PlanetsEntity planet, PlanetFieldsEntity planetFieldEntity) throws IOException {
        if (planetFieldEntity == null) {
            throw new IOException(NO_PLANET_ON_FIELD);
        }
        PlanetsEntity otherPlanet = planetFieldEntity.getPlanetsEntity();
        if (planet == otherPlanet) {
            throw new IOException(CANT_CHOOSE_YOUR_PLANET);
        }
        if (!planet.getFleetStatus().equals(ON_THE_MOTHER_PLANET)) {
            throw new IOException(CANT_ATTACK);
        }
        if (planet.getFleetsByFleetId().sumofAllShips() <= 0) {
            throw new IOException(ZERO_FLEET);
        }
        return otherPlanet;
    }
}
