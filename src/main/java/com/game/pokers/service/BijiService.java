package com.game.pokers.service;

import com.game.pokers.dao.BiJiRoomInfoRepo;
import com.game.pokers.dao.BiJiRoundInfoRepo;
import com.game.pokers.dao.BiJiUserRoomRefRepo;
import com.game.pokers.db.BiJiUserRoomRefDO;
import com.game.pokers.db.RoomInfoDO;
import com.game.pokers.db.RoundInfoDO;
import com.game.pokers.entity.UserInfo;
import com.game.pokers.entity.biji.BijiRoomInfo;
import com.game.pokers.entity.biji.BijiRoundInfo;
import com.game.pokers.mapper.BiJiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Component
public class BijiService implements BijiServiceI {

    @Autowired
    public BiJiRoomInfoRepo biJiRoomInfoRepo;

    @Autowired
    public BiJiRoundInfoRepo biJiRoundInfoRepo;

    @Autowired
    public BiJiUserRoomRefRepo biJiUserRoomRefRepo;

    @Override
    public Long createRoom(BijiRoomInfo roomInfo) {
        RoomInfoDO dbO = BiJiMapper.toDO(roomInfo);
        dbO.setCreateTime(new Date());
        dbO.setUpdateTime(new Date());
        Long roomId = biJiRoomInfoRepo.save(dbO).getId();
        roomInfo.setRoomId(roomId);
        this.doUserRoomRef(roomInfo);
        return roomId;
    }

    @Override
    public void updateRoom(BijiRoomInfo roomInfo) {
        RoomInfoDO dbO = BiJiMapper.toDO(roomInfo);
        dbO.setUpdateTime(new Date());
        biJiRoomInfoRepo.modefy(dbO.getId(), dbO.getStatus(), dbO.getUpdateTime(), dbO.getEndTime(), dbO.getData());
        doUserRoomRef(roomInfo);
    }

    @Override
    public BijiRoomInfo getRoom(Long roomId) {
        RoomInfoDO dbO = biJiRoomInfoRepo.findById(roomId).orElse(null);
        return BiJiMapper.toEntity(dbO);
    }

    @Override
    public List<BijiRoomInfo> listRoom(Long userId, Integer limit) {
        List<BiJiUserRoomRefDO> refs = biJiUserRoomRefRepo.listByUserId(userId, limit);
        if (CollectionUtils.isEmpty(refs)) {
            return new ArrayList<>();
        }
        List<Long> roomIds = refs.stream().map(BiJiUserRoomRefDO::getRoomId).toList();
        Iterable<RoomInfoDO> roomDbs = biJiRoomInfoRepo.findAllById(roomIds);
        List<BijiRoomInfo> roomInfos = new ArrayList<>();
        roomDbs.forEach((dbO) -> roomInfos.add(BiJiMapper.toEntity(dbO)));
        return roomInfos;
    }

    @Override
    public List<BijiRoundInfo> listRound(Long roomId) {
        List<RoundInfoDO> dbOs = biJiRoundInfoRepo.findByRoomId(roomId);
        if (CollectionUtils.isEmpty(dbOs)) {
            return new ArrayList<>();
        }
        return dbOs.stream().map(BiJiMapper::toEntity).toList();
    }

    @Override
    public Long createRound(BijiRoundInfo roundInfo) {
        RoundInfoDO dbO = BiJiMapper.toDO(roundInfo);
        dbO.setCreateTime(new Date());
        dbO.setUpdateTime(new Date());
        return biJiRoundInfoRepo.save(dbO).getId();
    }

    @Override
    public void updateRound(BijiRoundInfo roundInfo) {
        RoundInfoDO dbO = BiJiMapper.toDO(roundInfo);
        dbO.setUpdateTime(new Date());
        biJiRoundInfoRepo.modify(dbO.getId(), dbO.getStatus(), dbO.getUpdateTime(), dbO.getEndTime(), dbO.getData());
    }

    private void doUserRoomRef(BijiRoomInfo roomInfo) {
        if (roomInfo == null || CollectionUtils.isEmpty(roomInfo.getAllPlayers())) {
            return;
        }
        List<BiJiUserRoomRefDO> dbRefs = biJiUserRoomRefRepo.listByRoomId(roomInfo.getRoomId());
        if (dbRefs == null) {
            dbRefs = new ArrayList<>();
        }

        for(UserInfo u : roomInfo.getAllPlayers()) {
            boolean isExisted = false;
            for (BiJiUserRoomRefDO dbRef : dbRefs) {
                if (Objects.equals(u.getId(), dbRef.getUserId())) {
                    isExisted = true;
                    break;
                }
            }
            if (!isExisted) {
                biJiUserRoomRefRepo.save(new BiJiUserRoomRefDO(u.getId(), roomInfo.getRoomId()));
            }
        }
    }
}
