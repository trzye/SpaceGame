package pl.edu.pw.ee.spacegame.server.controller.history;

/**
 * Created by KTamo_000 on 2016-06-09.
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.BaseAbstractController;
import pl.edu.pw.ee.spacegame.server.controller.JsonResponseEntity;
import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;
import pl.edu.pw.ee.spacegame.server.entity.AttackHistoriesEntity;
import pl.edu.pw.ee.spacegame.server.entity.UsersEntity;
import pl.edu.pw.ee.spacegame.server.security.LoggedUsers;
import java.util.ArrayList;



import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.ATTACK_HISTORY_PATH;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.GET_ATTACK_HISTORY_LOG;

@RestController
@CrossOrigin
@RequestMapping(ATTACK_HISTORY_PATH)
public class AttackHistoryController extends BaseAbstractController {

    @RequestMapping(method = GET)
    public ResponseEntity<?> test(@RequestBody AuthenticationData authenticationData) {
        databaseLogger.setClass(getClass());
        try {
//            if (!LoggedUsers.isLogged(authenticationData)) {
//                return TextResponseEntity.getNotAuthorizedResponseEntity(authenticationData, databaseLogger);
//            }
                UsersEntity usersEntity = usersDAO.getUserByNickname(authenticationData.getNickname());
//           if (!usersEntity.getIsActivated()) {
//                return TextResponseEntity.getNotActivatedResponseEntity(authenticationData, databaseLogger);
//           }
            databaseLogger.info(GET_ATTACK_HISTORY_LOG);
            Iterable<AttackHistoriesEntity> attackHistory = attackHistoriesDAO.findAll();
            ArrayList<AttackHistoryData> outputAttackHistory = new ArrayList<>();
            for (AttackHistoriesEntity attack : attackHistory) {
                if(attack.getUsersByUserId().equals(usersEntity))
                    outputAttackHistory.add(attack.getAttackHistoryData());
            }
            return new JsonResponseEntity<>(outputAttackHistory, OK);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

}
