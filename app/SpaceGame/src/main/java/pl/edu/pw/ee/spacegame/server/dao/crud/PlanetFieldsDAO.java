package pl.edu.pw.ee.spacegame.server.dao.crud;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.pw.ee.spacegame.server.entity.PlanetFieldsEntity;


/**
 * Created by Michał on 2016-05-05.
 */
public interface PlanetFieldsDAO extends CrudRepository<PlanetFieldsEntity, Integer> {

    @Query("SELECT p FROM PlanetFieldsEntity p where p.coordinateX=:X and p.coordinateY=:Y")
    PlanetFieldsEntity getPlanetByXandY(@Param(value = "X") Integer X, @Param(value = "Y") Integer Y);

}
