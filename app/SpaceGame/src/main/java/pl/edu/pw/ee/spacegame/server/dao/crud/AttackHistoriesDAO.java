package pl.edu.pw.ee.spacegame.server.dao.crud;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.pw.ee.spacegame.server.entity.AttackHistoriesEntity;


/**
 * Created by Michał on 2016-05-05.
 */
public interface AttackHistoriesDAO extends CrudRepository<AttackHistoriesEntity, Integer> {
    @Query("SELECT a " +
            "from AttackHistoriesEntity a " +
            "   join a.usersByUserId u " +
            "   join a.planetsByAttackedPlanetId p " +
            "   where u.userId = :userId " +
            "   or p.planetId = :planetId ")
    Iterable<AttackHistoriesEntity> getAttackHistoryByUserIdOrPlanetId (@Param("userId") Integer userId, @Param("planetId") Integer planetId);
}
