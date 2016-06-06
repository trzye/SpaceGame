package pl.edu.pw.ee.spacegame.server.controller.map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.BaseAbstractController;
import pl.edu.pw.ee.spacegame.server.entity.PlanetFieldsEntity;

import java.util.ArrayList;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.MAP_PATH;

/**
 * Created by Michał on 2016-05-05.
 * <p>
 * Przykładowa klasa do testowania i zabawy.
 * </p>
 */
@RestController
@CrossOrigin
@RequestMapping(MAP_PATH)
public class MapController extends BaseAbstractController {

    @RequestMapping(method = GET)
    public ResponseEntity<?> getMap() {
        databaseLogger.setClass(getClass());
        try {
            databaseLogger.info("Wysłano informacje o mapie galaktyki");
            Iterable<PlanetFieldsEntity> planetFields = planetFieldsDAO.findAll();
            ArrayList<PlanetFieldData> outputPlanetFields = new ArrayList<>();
            for (PlanetFieldsEntity planetField : planetFields) {
                outputPlanetFields.add(new PlanetFieldData(planetField));
            }
            return new ResponseEntity<>(outputPlanetFields, OK);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

}
