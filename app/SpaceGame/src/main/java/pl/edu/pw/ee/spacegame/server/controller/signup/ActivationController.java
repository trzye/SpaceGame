package pl.edu.pw.ee.spacegame.server.controller.signup;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.BaseAbstractController;
import pl.edu.pw.ee.spacegame.server.entity.ActivationsEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetFieldsEntity;

import java.io.IOException;
import java.util.Date;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by Michał on 2016-06-06.
 */
@RestController
@CrossOrigin
@RequestMapping(path = "/activation")
public class ActivationController extends BaseAbstractController {

    @RequestMapping(method = GET)
    public ResponseEntity<?> activate(@RequestParam(name = "email") String email,
                                      @RequestParam(name = "activationCode") String activationCode) {
        try {
            databaseLogger.setClass(getClass());
            activateAccount(email, activationCode);
            databaseLogger.info("Aktywowano konto dla email " + email);
            return new ResponseEntity<>("Pomyślnie aktywowano konto", HttpStatus.OK);
        } catch (IOException e) {
            return handleBadRequest(e);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

    @Transactional
    private void activateAccount(String email, String activationCode) throws IOException {
        ActivationsEntity activationsEntity = activationsDAO.getActivationByEmail(email);
        if (activationsEntity == null)
            throw new IOException("Nie istnieje użytkownik o takim adresie email");

        if (!activationsEntity.getActivationCode().equals(activationCode))
            throw new IOException(("Błędny kod aktywacyjny"));

        if (activationsEntity.getUsersByUserId().getIsActivated())
            throw new IOException("Użytkownik został już aktywowany");

        if (isActivationTimeCorrect(activationsEntity)) {
            activationsEntity.getUsersByUserId().setIsActivated(true);
            activationsEntity.getPlanetFieldsByPlanetFieldId().setStatus(PlanetFieldsEntity.Status.USED);
            //TODO: Stworzenie pozostałych tabelek dla aktywowanego gracza
            activationsDAO.save(activationsEntity);
        } else {
            throw new IOException("Przekroczono czas aktywacji");
        }
    }

    private Boolean isActivationTimeCorrect(ActivationsEntity activationsEntity) {
        Date signUpTime = activationsEntity.getTime();
        Date currentTime = new Date();

        long timeDiffer = currentTime.getTime() - signUpTime.getTime();
        long tenMinutes = 1000 * 60 * 10;

        return timeDiffer < tenMinutes;
    }


}
