package pl.edu.pw.ee.spacegame.server.dao;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pw.ee.spacegame.server.model.HelloWorldEntity;

/**
 * Created by Michał on 2016-05-05.
 */
public interface HelloWorldDao extends CrudRepository<HelloWorldEntity, String> {

}
