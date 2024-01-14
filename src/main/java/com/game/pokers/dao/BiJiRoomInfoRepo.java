package com.game.pokers.dao;

import com.game.pokers.constant.biji.BijiRoomStatus;
import com.game.pokers.db.RoomInfoDO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

public interface BiJiRoomInfoRepo extends CrudRepository<RoomInfoDO, Long> {
    @Modifying
    @Query("UPDATE biji_room_info SET status = :status, update_time=:updateTime, end_time=:endTime, data=:data WHERE id = :id")
    void modefy(Long id, BijiRoomStatus status, Date updateTime, Date endTime, String data);

}
