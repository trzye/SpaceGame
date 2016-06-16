package pl.edu.pw.ee.spacegame.server.dao.crud;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.pw.ee.spacegame.server.entity.PlanetsEntity;

import java.util.ArrayList;


/**
 * Created by Michał on 2016-05-05.
 */
public interface PlanetsDAO extends CrudRepository<PlanetsEntity, Integer> {
    @Query("SELECT p FROM PlanetsEntity p         " +
            "JOIN p.fleetsByFleetId f             " +
            "JOIN f.currentAlliancesByFleetId c   " +
            "JOIN c.planetsByHelpedPlanetId h     " +
            "WHERE h.planetId = :defenderPlanetId " +
            "AND c.timeOfSendingAlliance is null  ")
    ArrayList<PlanetsEntity> getCurrentAlliances(@Param("defenderPlanetId") Integer defenderPlanetId);


}
