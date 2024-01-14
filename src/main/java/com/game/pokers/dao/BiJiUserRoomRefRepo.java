package com.game.pokers.dao;

import com.game.pokers.db.BiJiUserRoomRefDO;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BiJiUserRoomRefRepo extends CrudRepository<BiJiUserRoomRefDO, Long> {

    @Query("select * from biji_user_room_ref where user_id = :userId order by id desc limit :limit")
    List<BiJiUserRoomRefDO> listByUserId(Long userId, int limit);

    @Query("select * from biji_user_room_ref where room_id = :roomId")
    List<BiJiUserRoomRefDO> listByRoomId(Long roomId);
}
