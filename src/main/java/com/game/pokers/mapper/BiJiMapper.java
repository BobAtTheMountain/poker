package com.game.pokers.mapper;

import com.game.pokers.db.RoomInfoDO;
import com.game.pokers.db.RoundInfoDO;
import com.game.pokers.db.UserInfoDO;
import com.game.pokers.dto.BiJiRoomInfoDTO;
import com.game.pokers.dto.BiJiRoundInfoDTO;
import com.game.pokers.dto.UserInfoDTO;
import com.game.pokers.entity.UserInfo;
import com.game.pokers.entity.biji.BijiRoomInfo;
import com.game.pokers.entity.biji.BijiRoundInfo;
import com.game.pokers.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BiJiMapper {

    public static RoomInfoDO toDO(BijiRoomInfo o) {
        if (o == null) {
            return null;
        }
        RoomInfoDO d = new RoomInfoDO();
        d.setId(o.getRoomId());
        d.setStatus(o.getBijiRoomStatus());
        d.setMainUserId(o.getRoomMainUser().getId());
        d.setStartTime(o.getStartTime());
        d.setEndTime(o.getEndTime());
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String data = gson.toJson(o);
        d.setData(data);
        return d;
    }

    public static RoundInfoDO toDO(BijiRoundInfo o) {
        if (o == null) {
            return null;
        }
        RoundInfoDO d = new RoundInfoDO();
        d.setId(o.getRoundId());
        d.setRoomId(o.getRoomId());
        d.setStatus(o.getStatus());
        d.setStartTime(o.getStartTime());
        d.setEndTime(o.getEndTime());
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        String data = gson.toJson(o);
        d.setData(data);
        return d;
    }

    public static UserInfoDO toDO(UserInfo o) {
        if (o == null) {
            return null;
        }
        UserInfoDO d = new UserInfoDO();
        d.setId(o.getId());
        d.setNickName(o.getNickName());
        d.setPassword(o.getPassword());
        d.setPhone(o.getPhoneNumber());
        return d;
    }

    public static BiJiRoomInfoDTO toDTO(BijiRoomInfo o) {
        if (o == null) {
            return null;
        }
        BiJiRoomInfoDTO r = new BiJiRoomInfoDTO();
        r.setEndTime(Utils.toYYYYMMDDHHmmss(o.getEndTime()));
        r.setAllPlayers(o.getAllPlayers().stream().map(BiJiMapper::toDTO).toList());
        r.setRoomId(o.getRoomId());
        r.setStartTime(Utils.toYYYYMMDDHHmmss(o.getStartTime()));
        r.setStatus(o.getBijiRoomStatus());
        r.setMainUser(toDTO(o.getRoomMainUser()));
        r.setRoomPassport(o.getRoomPassport());
        r.setRuleConfig(o.getRuleConfig());
        r.setPointsMap(o.getPointsMap());
        r.setPlayingRoundInfo(BiJiMapper.toDTO(o.getPlayingRound()));
        r.setNumOfPlayers(o.getNumOfPlayers());
        r.setRounds(o.getRounds());
        return r;
    }

    public static UserInfoDTO toDTO(UserInfo o) {
        return new UserInfoDTO(o.getId(), o.getNickName());
    }

    public static BiJiRoundInfoDTO toDTO(BijiRoundInfo o) {
        if (o == null) {
            return null;
        }
        BiJiRoundInfoDTO r = new BiJiRoundInfoDTO();
        r.setRoundId(o.getRoundId());
        r.setRoomId(o.getRoomId());
        r.setStartTime(Utils.toYYYYMMDDHHmmss(o.getStartTime()));
        r.setEndTime(Utils.toYYYYMMDDHHmmss(o.getEndTime()));
        r.setPokers(o.getPokers());
        r.setInitPokers9(o.getInitPokers9());
        r.setUserPlayMap(o.getUserPlayMap());
        r.setPokers9(o.getPokers9());
        r.setSurrenders(o.getSurrenders());
        r.setUserXipaiMap(o.getUserXipaiMap());
        r.setUserPointInfoMap(o.getUserPointInfoMap());
        r.setUserRountPointMap(o.getUserRountPointMap());
        r.setStatus(o.getStatus());
        return r;
    }


    public static UserInfo toEntity(UserInfoDO dbO) {
        if (dbO == null) {
            return null;
        }
        return new UserInfo(dbO.getId(), dbO.getNickName(), dbO.getPhone(), dbO.getPassword());
    }
    public static BijiRoomInfo toEntity(RoomInfoDO dbO) {
        if (dbO == null) {
            return null;
        }
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.fromJson(dbO.getData(), BijiRoomInfo.class);
    }
    public static BijiRoundInfo toEntity(RoundInfoDO dbO) {
        if (dbO == null) {
            return null;
        }
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.fromJson(dbO.getData(), BijiRoundInfo.class);
    }
}
