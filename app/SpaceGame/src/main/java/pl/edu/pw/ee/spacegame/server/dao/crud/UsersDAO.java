package pl.edu.pw.ee.spacegame.server.dao.crud;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.edu.pw.ee.spacegame.server.entity.UsersEntity;


/**
 * Created by Michał on 2016-05-05.
 */
public interface UsersDAO extends CrudRepository<UsersEntity, Integer> {

    @Query("SELECT u FROM UsersEntity u WHERE u.nickname = :nickname")
    UsersEntity getUserByNickname(@Param("nickname") String nickname);

    @Query("SELECT u FROM UsersEntity u WHERE u.email = :email")
    UsersEntity getUserByEmail(@Param("email") String email);

}
