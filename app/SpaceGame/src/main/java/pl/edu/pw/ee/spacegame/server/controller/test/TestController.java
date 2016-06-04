package pl.edu.pw.ee.spacegame.server.controller.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.AutowiredController;

import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.TEST_PATH;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.UNEXPECTED_ERROR;

/**
 * Created by Michał on 2016-05-05.
 * <p>
 * Klasa do testowania i zabawy.
 * </p>
 */
@RestController
@RequestMapping(TEST_PATH)
public class TestController extends AutowiredController {


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> test() {
        databaseLogger.setClass(getClass());
        try {
            databaseLogger.info("Getting GET request in test controller");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            databaseLogger.error(UNEXPECTED_ERROR + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
