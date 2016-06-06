package pl.edu.pw.ee.spacegame.server.controller.signup;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.BaseAbstractController;
import pl.edu.pw.ee.spacegame.server.controller.TextResponseEntity;
import pl.edu.pw.ee.spacegame.server.entity.ActivationsEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetFieldsEntity;

import java.io.IOException;
import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.*;

/**
 * Created by Michał on 2016-06-06.
 */
@RestController
@CrossOrigin
@RequestMapping(path = ACTIVATION_PATH)
public class ActivationController extends BaseAbstractController {

    @RequestMapping(method = GET)
    public ResponseEntity<?> activate(@RequestParam(name = "email") String email,
                                      @RequestParam(name = "activationCode") String activationCode) {
        databaseLogger.setClass(getClass());
        try {
            activateAction(email, activationCode);
            databaseLogger.info(String.format(ACTIVATION_LOG, email));
            return new TextResponseEntity<>(ACTIVATION_SUCCESS, HttpStatus.OK);
        } catch (IOException e) {
            return handleBadRequest(e);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

    @Transactional
    private void activateAction(String email, String activationCode) throws IOException {
        ActivationsEntity activationsEntity = activationsDAO.getActivationByEmail(email);
        if (activationsEntity == null)
            throw new IOException(USER_WITH_SUCH_EMAIL_NOT_EXISTS);

        if (!activationsEntity.getActivationCode().equals(activationCode)) {
            throw new IOException((BAD_ACTIVATION_CODE));
        }

        if (activationsEntity.getUsersByUserId().getIsActivated())
            throw new IOException(USER_ARLEADY_ACTIVATED);

        if (isActivationTimeCorrect(activationsEntity)) {
            activationsEntity.getUsersByUserId().setIsActivated(true);
            activationsEntity.getPlanetFieldsByPlanetFieldId().setStatus(PlanetFieldsEntity.Status.USED);
            //TODO: Stworzenie pozostałych tabelek dla aktywowanego gracza
            activationsDAO.save(activationsEntity);
        } else {
            throw new IOException(ACTIVATION_TIMEOUT);
        }
    }

    private Boolean isActivationTimeCorrect(ActivationsEntity activationsEntity) {
        Date signUpTime = activationsEntity.getTime();
        Date currentTime = new Date();
        long timeDiffer = currentTime.getTime() - signUpTime.getTime();
        long tenMinutes = 1000 * 60 * 10; //1000 millis * 60 seconds * 10 minutes
        return timeDiffer < tenMinutes;
    }


}
