package pl.edu.pw.ee.spacegame.server.controller.sample;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.AutowiredController;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.SAMPLE_PATH;

/**
 * Created by Michał on 2016-05-05.
 * <p>
 * Przykładowa klasa do testowania i zabawy.
 * </p>
 */
@RestController
@RequestMapping(SAMPLE_PATH)
public class SampleController extends AutowiredController {


    @RequestMapping(method = GET)
    public ResponseEntity<?> test() {
        databaseLogger.setClass(getClass());
        try {
            databaseLogger.info("Getting GET request in sample controller");
            return new ResponseEntity<>(OK);
        } catch (Exception e) {
            return getResponseEntity(e);
        }
    }

}
