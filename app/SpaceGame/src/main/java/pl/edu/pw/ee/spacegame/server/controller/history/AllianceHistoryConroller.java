package pl.edu.pw.ee.spacegame.server.controller.history;

/**
 * Created by KTamo_000 on 2016-06-12.
 */

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.BaseAbstractController;
import pl.edu.pw.ee.spacegame.server.controller.JsonResponseEntity;
import pl.edu.pw.ee.spacegame.server.controller.TextResponseEntity;
import pl.edu.pw.ee.spacegame.server.entity.AllianceHistoriesEntity;
import pl.edu.pw.ee.spacegame.server.entity.UsersEntity;
import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;
import pl.edu.pw.ee.spacegame.server.security.LoggedUsers;

import java.util.ArrayList;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.ALLIANCE_HISTORY_PATH;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.GET_ALLIANCE_HISTORY_LOG;

@RestController
@CrossOrigin
@RequestMapping(ALLIANCE_HISTORY_PATH)
public class AllianceHistoryConroller extends BaseAbstractController {

    @RequestMapping(method = POST)
    public ResponseEntity<?> test(@RequestBody AuthenticationData authenticationData) {
        databaseLogger.setClass(getClass());
        try {
            if (!LoggedUsers.isLogged(authenticationData)) {
                return TextResponseEntity.getNotAuthorizedResponseEntity(authenticationData, databaseLogger);
            }
            UsersEntity usersEntity = usersDAO.getUserByNickname(authenticationData.getNickname());
            if (!usersEntity.getIsActivated()) {
                return TextResponseEntity.getNotActivatedResponseEntity(authenticationData, databaseLogger);
            }
            databaseLogger.info(GET_ALLIANCE_HISTORY_LOG);
            Iterable<AllianceHistoriesEntity> allianceHistory = allianceHistoriesDAO.getAllianceHistoryByUserIdOrPlanetId(usersEntity.getUserId(), usersEntity.getPlanet().getPlanetId());
            ArrayList<AllianceHistoryData> outputAllianceHistory = new ArrayList<>();
            for (AllianceHistoriesEntity alliance : allianceHistory) {
                outputAllianceHistory.add(alliance.getAllianceHistoryData());
            }
            return new JsonResponseEntity<>(outputAllianceHistory, OK);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }
}
