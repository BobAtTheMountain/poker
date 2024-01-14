package com.game.pokers.global;

import com.game.pokers.entity.biji.BijiRoomInfo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Global {
    public static ConcurrentMap<Long, BijiRoomInfo> BIJI_GLOBAL_ROOM_MAP = new ConcurrentHashMap<>();

    public static ConcurrentMap<Long, Long> USER_ROOMID_MAP = new ConcurrentHashMap<>();

    public static String SESSION_USER_ID = "SESSION_USER_ID";

}
