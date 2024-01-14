package com.game.pokers.service;

import com.game.pokers.entity.biji.BijiRoomInfo;
import com.game.pokers.entity.biji.BijiRoundInfo;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface BijiServiceI {


    Long createRoom(BijiRoomInfo roomInfo);
    void updateRoom(BijiRoomInfo roomInfo);
    BijiRoomInfo getRoom(Long roomId);

    List<BijiRoomInfo> listRoom(Long userId, Integer limit);

    List<BijiRoundInfo> listRound(Long roomId);

    Long createRound(BijiRoundInfo roundInfo);

    void updateRound(BijiRoundInfo roundInfo);

}
