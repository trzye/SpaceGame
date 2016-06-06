package pl.edu.pw.ee.spacegame.server.controller.signin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.BaseAbstractController;
import pl.edu.pw.ee.spacegame.server.entity.UsersEntity;
import pl.edu.pw.ee.spacegame.server.security.AES;
import pl.edu.pw.ee.spacegame.server.security.AuthenticationData;
import pl.edu.pw.ee.spacegame.server.security.LoggedUsers;

import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.SIGN_IN_PATH;

/**
 * Created by Michał on 2016-05-05.
 */
@RestController
@CrossOrigin
@RequestMapping(SIGN_IN_PATH)
public class SignInController extends BaseAbstractController {


    @RequestMapping(method = POST)
    public ResponseEntity<?> signIn(@RequestBody SignInData signInData) {
        databaseLogger.setClass(getClass());
        try {
            AuthenticationData authenticationData = signInAction(signInData);
            databaseLogger.info("Zalogowano użytkownika: " + signInData.getNickname());
            return new ResponseEntity<>(authenticationData, OK);
        } catch (IOException e) {
            return handleBadRequest(e);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

    private AuthenticationData signInAction(SignInData signInData) throws IOException {
        UsersEntity user = usersDAO.getUserByNickname(signInData.getNickname());
        if (user == null) {
            throw new IOException("Nie istnieje użytkownik o podanej nazwie");
        }
        if (!AES.isPasswordCorrect(signInData.getRawPassword(), new AES.EncryptionData(user.getPassword(), user.getSalt()))) {
            throw new IOException("Złe hasło");
        }
        return LoggedUsers.generateTokenAndAddToLoggedUsers(user.getNickname());
    }
}
