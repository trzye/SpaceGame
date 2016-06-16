package pl.edu.pw.ee.spacegame.server.controller.map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pw.ee.spacegame.server.controller.JsonResponseEntity;
import pl.edu.pw.ee.spacegame.server.dao.crud.BaseAbstractComponent;
import pl.edu.pw.ee.spacegame.server.entity.PlanetFieldsEntity;

import java.util.ArrayList;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.GET_MAP_LOG;
import static pl.edu.pw.ee.spacegame.server.controller.ControllerConstantObjects.MAP_PATH;

/**
 * Created by Michał on 2016-05-05.
 */
@RestController
@CrossOrigin
@RequestMapping(MAP_PATH)
public class MapController extends BaseAbstractComponent {

    @RequestMapping(method = GET)
    public ResponseEntity<?> getMap() {
        databaseLogger.setClass(getClass());
        try {
            databaseLogger.info(GET_MAP_LOG);
            Iterable<PlanetFieldsEntity> planetFields = planetFieldsDAO.findAll();
            ArrayList<PlanetFieldData> outputPlanetFields = new ArrayList<>();
            for (PlanetFieldsEntity planetField : planetFields) {
                outputPlanetFields.add(new PlanetFieldData(planetField));
            }
            //TODO: logi
            return new JsonResponseEntity<>(outputPlanetFields, OK);
        } catch (Exception e) {
            return handleServerError(e);
        }
    }

}
