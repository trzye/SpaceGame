package pl.edu.pw.ee.spacegame.server.dao.crud;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.pw.ee.spacegame.server.entity.ActivationsEntity;

/**
 * Created by Michał on 2016-05-05.
 * Shift alt insert - toogle mode
 */
public interface ActivationsDAO extends CrudRepository<ActivationsEntity, Integer> {

    @Query("" +
            " SELECT a from ActivationsEntity a                " +
            "   join a.usersByUserId u                         " +
            "   where u.email = :email                         ")
    ActivationsEntity getActivationByEmail(@Param("email") String email);

}
