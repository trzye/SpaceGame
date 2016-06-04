package pl.edu.pw.ee.spacegame.server.dao.crud;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pw.ee.spacegame.server.entity.AttackHistoriesEntity;


/**
 * Created by Michał on 2016-05-05.
 */
public interface AttackHistoriesDAO extends CrudRepository<AttackHistoriesEntity, Integer> {

}
