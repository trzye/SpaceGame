package pl.edu.pw.ee.spacegame.server.dao.crud;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.pw.ee.spacegame.server.entity.AllianceHistoriesEntity;


/**
 * Created by Michał on 2016-05-05.
 */
public interface AllianceHistoriesDAO extends CrudRepository<AllianceHistoriesEntity, Integer> {
    @Query("SELECT a " +
            "from AllianceHistoriesEntity a " +
            "   join a.usersByUserId u " +
            "   join a.planetsByHelpedPlanetId p " +
            "   where u.userId = :userId " +
            "   or p.planetId = :planetId ")
    Iterable<AllianceHistoriesEntity> getAllianceHistoryByUserIdOrPlanetId (@Param("userId") Integer userId, @Param("planetId") Integer planetId);
}
