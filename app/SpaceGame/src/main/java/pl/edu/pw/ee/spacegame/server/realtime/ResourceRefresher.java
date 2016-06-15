package pl.edu.pw.ee.spacegame.server.realtime;

import pl.edu.pw.ee.spacegame.server.entity.BuildingsEntity;
import pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity;
import pl.edu.pw.ee.spacegame.server.entity.ResourcesEntity;

import java.sql.Timestamp;

/**
 * Created by Michał on 2016-06-07.
 */
public class ResourceRefresher {
    //TODO: zmienić na minuty
    //TODO: co gdy odświeżenie ataku nastąpi bardzo późno względem faktycznego czasu ataku?
    //TODO: tzn. gdyby faktyczny czas ataku był wtedy gdy planeta nie miała surowców, to atakujący by nic nie ukradł
    //TODO: jednak odświeżenie następujące 10 dni po ataku pozwoliło by na kradzież wygenerowanych w tym czasie
    //TODO: surowców. (na razie myślę, że odświeżanie stanu gry można dodatkowo zawrzeć w jakimś demonie aby zminimalizować
    //TODO: różnicę w zasobach.)
    public static void refreshResources(PlanetsEntity planet) {
        ResourcesEntity resource = planet.getResourcesByResourceId();
        BuildingsEntity ununtriumMine = planet.getUnuntriumMine();
        BuildingsEntity gadolinMine = planet.getGadolinMine();
        long now = System.currentTimeMillis();
        //  long minutesBeetween = (now - resource.getLastUpdate().getTime()) / 1000 / 60;
        long minutesBeetween = (now - resource.getLastUpdate().getTime()) / 1000; //traktujemy jako sekundy aby przyspieszyc gre pod debug
        if (minutesBeetween > 0) {
            resource.setUnuntrium(resource.getUnuntrium() + (int) minutesBeetween * ununtriumMine.getLevel());
            resource.setGadolin(resource.getGadolin() + (int) minutesBeetween * gadolinMine.getLevel());
            resource.setLastUpdate(new Timestamp(now));
        }
    }
}
