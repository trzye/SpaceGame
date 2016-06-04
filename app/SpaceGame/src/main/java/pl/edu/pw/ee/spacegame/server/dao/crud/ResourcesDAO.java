package pl.edu.pw.ee.spacegame.server.dao.crud;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pw.ee.spacegame.server.entity.ResourcesEntity;


/**
 * Created by Michał on 2016-05-05.
 */
public interface ResourcesDAO extends CrudRepository<ResourcesEntity, Integer> {

}
