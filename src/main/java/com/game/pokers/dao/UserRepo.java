package com.game.pokers.dao;
import com.game.pokers.db.UserInfoDO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepo extends CrudRepository<UserInfoDO, Long> {

    @Query("SELECT * FROM user WHERE phone = :phone")
    UserInfoDO findByPhone(String phone);
    @Query("SELECT * FROM user WHERE nick_name = :nickName")
    UserInfoDO findByName(String nickName);
}
