package pl.edu.pw.ee.spacegame.server.controller.signup;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.BaseAbstractController;
import pl.edu.pw.ee.spacegame.server.controller.TextResponseEntity;
import pl.edu.pw.ee.spacegame.server.entity.ActivationsEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetFieldsEntity;
import pl.edu.pw.ee.spacegame.server.entity.UsersEntity;
import pl.edu.pw.ee.spacegame.server.security.AES;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.*;

/**
 * Created by Michał on 2016-06-05.
 */
@RestController
@CrossOrigin
@RequestMapping(SIGN_UP_PATH)
public class SignUpController extends BaseAbstractController {

    @RequestMapping(method = POST)
    public ResponseEntity<?> signUp(@RequestBody SignUpData signUpData) {
        databaseLogger.setClass(getClass());
        try {
            validate(signUpData);
            UsersEntity usersEntity = getUsersEntity(signUpData);
            PlanetFieldsEntity planetFieldsEntity = getPlanetFieldsEntity(signUpData);
            ActivationsEntity activationsEntity = getActivationsEntity(usersEntity, planetFieldsEntity);
            saveUserAndSendMail(usersEntity, planetFieldsEntity, activationsEntity);
            databaseLogger.info(String.format(USER_ADDED_LOG, signUpData.getNickname()));
            return new TextResponseEntity<Object>(String.format(USER_ADDED, signUpData.getEmail()), HttpStatus.OK);
        } catch (IOException e) {
            return handleBadRequest(e);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

    private ActivationsEntity getActivationsEntity(UsersEntity usersEntity, PlanetFieldsEntity planetFieldsEntity) {
        ActivationsEntity activationsEntity = new ActivationsEntity();
        activationsEntity.setActivationCode(AES.encrypt(usersEntity.getEmail()).getEncryptedPassword());
        activationsEntity.setPlanetFieldsByPlanetFieldId(planetFieldsEntity);
        activationsEntity.setUsersByUserId(usersEntity);
        return activationsEntity;
    }

    private PlanetFieldsEntity getPlanetFieldsEntity(SignUpData signUpData) {
        PlanetFieldsEntity planetFieldsEntity = new PlanetFieldsEntity();
        planetFieldsEntity.setCoordinateX(signUpData.getCoordinateX());
        planetFieldsEntity.setCoordinateY(signUpData.getCoordinateY());
        return planetFieldsEntity;
    }

    private UsersEntity getUsersEntity(SignUpData signUpData) {
        UsersEntity usersEntity = new UsersEntity();
        AES.EncryptionData encryptionData = AES.encrypt(signUpData.getRawPassword());
        usersEntity.setNickname(signUpData.getNickname());
        usersEntity.setEmail(signUpData.getEmail());
        usersEntity.setPassword(encryptionData.getEncryptedPassword());
        usersEntity.setSalt(encryptionData.getSalt());
        return usersEntity;
    }

    private void validate(SignUpData signUpData) throws IOException {
        validateUser(signUpData);
        validatePlanetField(signUpData);
    }

    private void validatePlanetField(SignUpData signUpData) throws IOException {
        validateCoordinate(signUpData.getCoordinateX());
        validateCoordinate(signUpData.getCoordinateY());
        PlanetFieldsEntity planet =
                planetFieldsDAO.getPlanetByXandY(signUpData.getCoordinateX(), signUpData.getCoordinateY());
        if (planet != null) {
            throw new IOException(PLANET_FIELD_NOT_EMPTY);
        }
    }

    private void validateCoordinate(Integer coordinate) throws IOException {
        if ((coordinate < 0) || (coordinate > 9))
            throw new IOException(BAD_COORDINATES);
    }

    private void validateUser(SignUpData signUpData) throws IOException {
        if (usersDAO.getUserByNickname(signUpData.getNickname()) != null) {
            throw new IOException(USER_EXISTS);
        }
        if (usersDAO.getUserByEmail(signUpData.getEmail()) != null) {
            throw new IOException(EMAIL_EXISTS);
        }
        if ((signUpData.getNickname().length() < 3) || (signUpData.getNickname().length() > 16)) {
            throw new IOException(WRONG_NICK_LENGTH);
        }
        if ((signUpData.getRawPassword().length() < 6) || (signUpData.getRawPassword().length() > 32)) {
            throw new IOException(WRONG_PASSWORD_LENGTH);
        }
    }

    /*
        Transactional cofa zmiany na bazie danych w razie jakiegoś błędu.
     */
    @Transactional
    private void saveUserAndSendMail(UsersEntity usersEntity, PlanetFieldsEntity planetFieldsEntity, ActivationsEntity activationsEntity) throws IOException {
        try {
            usersDAO.save(usersEntity);
            planetFieldsDAO.save(planetFieldsEntity);
            activationsDAO.save(activationsEntity);
            Mail.sent(usersEntity.getEmail(), activationsEntity.getActivationCode());
        } catch (MessagingException e) {
            activationsDAO.delete(activationsEntity);
            planetFieldsDAO.delete(planetFieldsEntity);
            usersDAO.delete(usersEntity);
            throw new IOException(MAIL_ERROR, e);
        }
    }

}
