package com.game.pokers.dao;

import com.game.pokers.constant.biji.BijiRoundStatus;
import com.game.pokers.db.RoundInfoDO;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface BiJiRoundInfoRepo extends CrudRepository<RoundInfoDO, Long> {
    @Modifying
    @Query("UPDATE biji_round_info SET status = :status, update_time=:updateTime, end_time=:endTime, data=:data WHERE id = :id")
    void modify(Long id, BijiRoundStatus status, Date updateTime, Date endTime, String data);

    @Query("select * from biji_round_info where room_id = :roomId order by id desc")
    List<RoundInfoDO> findByRoomId(Long roomId);
}
