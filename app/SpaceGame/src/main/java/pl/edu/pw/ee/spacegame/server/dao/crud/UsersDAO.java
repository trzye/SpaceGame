package pl.edu.pw.ee.spacegame.server.dao.crud;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pw.ee.spacegame.server.entity.UsersEntity;


/**
 * Created by Michał on 2016-05-05.
 */
public interface UsersDAO extends CrudRepository<UsersEntity, Integer> {

}
