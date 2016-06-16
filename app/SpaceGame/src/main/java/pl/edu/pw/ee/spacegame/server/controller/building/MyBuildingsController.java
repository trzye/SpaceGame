package pl.edu.pw.ee.spacegame.server.controller.building;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.JsonResponseEntity;
import pl.edu.pw.ee.spacegame.server.controller.TextResponseEntity;
import pl.edu.pw.ee.spacegame.server.dao.crud.BaseAbstractComponent;
import pl.edu.pw.ee.spacegame.server.entity.UsersEntity;
import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;
import pl.edu.pw.ee.spacegame.server.security.LoggedUsers;

import java.util.ArrayList;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.MY_BUILDINGS_LOG;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.MY_BUILDINGS_PATH;

/**
 * Created by Michał on 2016-05-05.
 */
@RestController
@CrossOrigin
@RequestMapping(MY_BUILDINGS_PATH)
public class MyBuildingsController extends BaseAbstractComponent {


    @RequestMapping(method = POST)
    public ResponseEntity<?> getMyBuildings(@RequestBody AuthenticationData authenticationData) {
        databaseLogger.setClass(getClass());
        try {
            if (!LoggedUsers.isLogged(authenticationData)) {
                return TextResponseEntity.getNotAuthorizedResponseEntity(authenticationData, databaseLogger);
            }
            UsersEntity usersEntity = usersDAO.getUserByNickname(authenticationData.getNickname());
            if (!usersEntity.getIsActivated()) {
                return TextResponseEntity.getNotActivatedResponseEntity(authenticationData, databaseLogger);
            }
            ArrayList<MyBuildingData> myBuildings = usersEntity.getMyBuildingsData();
            databaseLogger.info(String.format(MY_BUILDINGS_LOG, usersEntity.getNickname()));
            return new JsonResponseEntity<>(myBuildings, HttpStatus.OK);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

}
