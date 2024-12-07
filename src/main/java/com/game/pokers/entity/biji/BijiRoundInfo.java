package com.game.pokers.entity.biji;

import com.game.pokers.constant.biji.BijiRoundStatus;
import com.game.pokers.constant.biji.BijiXipai;

import java.util.*;

/**
 * 比鸡回合信息
 */
public class BijiRoundInfo {

    /**
     * 回合ID
     */
    Long roundId;

    /**
     * 房间ID
     */
    Long roomId;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date EndTime;

    /**
     * 牌堆顺序
     */
    private List<Integer> pokers;
    /**
     * 初始牌组
     */
    Map<Long,int[]> initPokers9;
    /**
     * 回合内玩家
     */
    Map<Long, Boolean> userPlayMap;
    /**
     * 配牌组合
     */
    Map<Long,int[][]> pokers9;
    /**
     * 投降
     */
    List<Long> surrenders;
    /**
     * Xipai[] 喜牌描述
     */
    Map<Long, List<BijiXipai>> userXipaiMap;
    /**
     * int[] 0->第一道 1-> 第二道 3-> 第三道 4 -> 喜钱
     */
    Map<Long, int[]> userPointInfoMap;
    /**
     * 本局内合计总分
     */
    Map<Long, Integer> userRountPointMap;

    /**
     * 回合状态
     */
    private BijiRoundStatus status;

    public BijiRoundInfo() {
        this.roomId = null;
        this.startTime = new Date();
        this.pokers = new ArrayList<>();
        this.initPokers9 = new HashMap<>();
        this.userPlayMap = new HashMap<>();
        this.pokers9 = new HashMap<>();
        this.surrenders = new ArrayList<>();
        this.userXipaiMap = new HashMap<>();
        this.userPointInfoMap = new HashMap<>();
        this.userRountPointMap = new HashMap<>();
        this.status = BijiRoundStatus.STARTING;
    }
    public BijiRoundInfo(Long roomId) {
        this.roomId = roomId;
        this.startTime = new Date();
        this.pokers = new ArrayList<>();
        this.initPokers9 = new HashMap<>();
        this.userPlayMap = new HashMap<>();
        this.pokers9 = new HashMap<>();
        this.surrenders = new ArrayList<>();
        this.userXipaiMap = new HashMap<>();
        this.userPointInfoMap = new HashMap<>();
        this.userRountPointMap = new HashMap<>();
        this.status = BijiRoundStatus.STARTING;
    }

    public Long getRoundId() {
        return roundId;
    }

    public void setRoundId(Long roundId) {
        this.roundId = roundId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return EndTime;
    }

    public void setEndTime(Date endTime) {
        EndTime = endTime;
    }

    public List<Integer> getPokers() {
        return pokers;
    }

    public void setPokers(List<Integer> pokers) {
        this.pokers = pokers;
    }

    public Map<Long, int[]> getInitPokers9() {
        return initPokers9;
    }

    public void setInitPokers9(Map<Long, int[]> initPokers9) {
        this.initPokers9 = initPokers9;
    }

    public Map<Long, int[][]> getPokers9() {
        return pokers9;
    }

    public void setPokers9(Map<Long, int[][]> pokers9) {
        this.pokers9 = pokers9;
    }

    public List<Long> getSurrenders() {
        return surrenders;
    }

    public void setSurrenders(List<Long> surrenders) {
        this.surrenders = surrenders;
    }

    public Map<Long, int[]> getUserPointInfoMap() {
        return userPointInfoMap;
    }

    public void setUserPointInfoMap(Map<Long, int[]> userPointInfoMap) {
        this.userPointInfoMap = userPointInfoMap;
    }

    public Map<Long, List<BijiXipai>> getUserXipaiMap() {
        return userXipaiMap;
    }

    public void setUserXipaiMap(Map<Long, List<BijiXipai>> userXipaiMap) {
        this.userXipaiMap = userXipaiMap;
    }

    public Map<Long, Integer> getUserRountPointMap() {
        return userRountPointMap;
    }

    public void setUserRountPointMap(Map<Long, Integer> userRountPointMap) {
        this.userRountPointMap = userRountPointMap;
    }

    public Map<Long, Boolean> getUserPlayMap() {
        return userPlayMap;
    }

    public void setUserPlayMap(Map<Long, Boolean> userPlayMap) {
        this.userPlayMap = userPlayMap;
    }

    public BijiRoundStatus getStatus() {
        return status;
    }

    public void setStatus(BijiRoundStatus status) {
        this.status = status;
    }
}
