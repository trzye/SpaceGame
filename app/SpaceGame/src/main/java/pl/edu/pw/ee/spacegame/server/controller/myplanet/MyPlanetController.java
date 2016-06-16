package pl.edu.pw.ee.spacegame.server.controller.myplanet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.BaseAbstractController;
import pl.edu.pw.ee.spacegame.server.controller.JsonResponseEntity;
import pl.edu.pw.ee.spacegame.server.controller.TextResponseEntity;
import pl.edu.pw.ee.spacegame.server.entity.UsersEntity;
import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;
import pl.edu.pw.ee.spacegame.server.security.LoggedUsers;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.GET_MY_PLANET_LOG;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.MY_PLANET_PATH;

/**
 * Created by Michał on 2016-05-05.
 */
@RestController
@CrossOrigin
@RequestMapping(MY_PLANET_PATH)
public class MyPlanetController extends BaseAbstractController {

    @RequestMapping(method = POST)
    public ResponseEntity<?> getMyPlanet(@RequestBody AuthenticationData authenticationData) {
        databaseLogger.setClass(getClass());
        try {
            if (!LoggedUsers.isLogged(authenticationData)) {
                return TextResponseEntity.getNotAuthorizedResponseEntity(authenticationData, databaseLogger);
            }
            UsersEntity usersEntity = usersDAO.getUserByNickname(authenticationData.getNickname());
            if (!usersEntity.getIsActivated()) {
                return TextResponseEntity.getNotActivatedResponseEntity(authenticationData, databaseLogger);
            }
            MyPlanetData myPlanetData = new MyPlanetData();
            myPlanetData.setCoordinateX(usersEntity.getPlanet().getPlanetFieldsByPlanetFieldId().getCoordinateX());
            myPlanetData.setCoordinateY(usersEntity.getPlanet().getPlanetFieldsByPlanetFieldId().getCoordinateY());
            databaseLogger.info(String.format(GET_MY_PLANET_LOG, usersEntity.getNickname()));
            return new JsonResponseEntity<>(myPlanetData, HttpStatus.OK);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }


}
