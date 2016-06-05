package pl.edu.pw.ee.spacegame.server.controller.signup;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.AutowiredController;
import pl.edu.pw.ee.spacegame.server.entity.ActivationsEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetFieldsEntity;
import pl.edu.pw.ee.spacegame.server.entity.UsersEntity;
import pl.edu.pw.ee.spacegame.server.security.AES;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.*;

/**
 * Created by Micha³ on 2016-06-05.
 * //TODO: dodanie timestamp do BD aktywacji (nie odliczymy 10 minut :P )
 * http://docs.michaljereczek.apiary.io/#reference/0/rejestracja/zarejestruj-uzytkownika
 */
@RestController
@RequestMapping(SIGN_UP_PATH)
public class SignUpController extends AutowiredController {

    @RequestMapping(method = POST)
    public ResponseEntity<?> signUp(@RequestBody SignUpData signUpData) {
        databaseLogger.setClass(getClass());
        try {
            UsersEntity usersEntity = getUsersEntity(signUpData);
            PlanetFieldsEntity planetFieldsEntity = getPlanetFieldsEntity(signUpData);
            ActivationsEntity activationsEntity = getActivationsEntity(usersEntity, planetFieldsEntity);
            saveUser(usersEntity, planetFieldsEntity, activationsEntity);
            //TODO wys³anie maila
            databaseLogger.info(String.format(USER_ADDED_LOG, signUpData.getNickname()));
            return new ResponseEntity<>(String.format(USER_ADDED, signUpData.getEmail()), HttpStatus.OK);
        } catch (IOException e) {
            return getResponseEntity(e);
        } catch (Exception e) {
            return getResponseEntity(e);
        }
    }


    private ActivationsEntity getActivationsEntity(UsersEntity usersEntity, PlanetFieldsEntity planetFieldsEntity) throws IOException {
        //TODO walidacja
        ActivationsEntity activationsEntity = new ActivationsEntity();
        activationsEntity.setActivationCode(AES.encrypt(usersEntity.getEmail()).getEncryptedPassword());
        activationsEntity.setPlanetFieldsByPlanetFieldId(planetFieldsEntity);
        activationsEntity.setUsersByUserId(usersEntity);
        return activationsEntity;
    }

    private PlanetFieldsEntity getPlanetFieldsEntity(SignUpData signUpData) throws IOException {
        //TODO walidacja
        PlanetFieldsEntity planetFieldsEntity = new PlanetFieldsEntity();
        planetFieldsEntity.setCoordinateX(signUpData.getCoordinateX());
        planetFieldsEntity.setCoordinateY(signUpData.getCoordinateY());
        return planetFieldsEntity;
    }

    private UsersEntity getUsersEntity(SignUpData signUpData) throws IOException {
        //TODO walidacja
        if (usersDAO.getUserByNickname(signUpData.getNickname()) != null) {
            throw new IOException(USER_EXISTS);
        }
        UsersEntity usersEntity = new UsersEntity();
        AES.EncryptionData encryptionData = AES.encrypt(signUpData.getRawPassword());
        usersEntity.setNickname(signUpData.getNickname());
        usersEntity.setEmail(signUpData.getEmail());
        usersEntity.setPassword(encryptionData.getEncryptedPassword());
        usersEntity.setSalt(encryptionData.getSalt());
        return usersEntity;
    }

    /*
        Transactional cofa zmiany na bazie danych w razie jakiegoœ b³êdu.
     */
    @Transactional
    private void saveUser(UsersEntity usersEntity, PlanetFieldsEntity planetFieldsEntity, ActivationsEntity activationsEntity) {
        usersDAO.save(usersEntity);
        planetFieldsDAO.save(planetFieldsEntity);
        activationsDAO.save(activationsEntity);
    }

}
