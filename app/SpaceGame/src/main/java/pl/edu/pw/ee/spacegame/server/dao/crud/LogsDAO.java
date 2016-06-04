package pl.edu.pw.ee.spacegame.server.dao.crud;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pw.ee.spacegame.server.entity.LogsEntity;


/**
 * Created by Michał on 2016-05-05.
 */
public interface LogsDAO extends CrudRepository<LogsEntity, Integer> {

}
