package com.game.pokers.entity.biji;

import com.game.pokers.constant.biji.BijiRoomStatus;
import com.game.pokers.entity.UserInfo;
import org.springframework.data.convert.ValueConverter;
import org.springframework.data.relational.core.sql.In;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 比鸡房间信息
 */
public class BijiRoomInfo {

    /**
     * 房间ID
     */
    private Long roomId;

    /**
     * 房间密码
     */
    private String roomPassport;


    /**
     * 开始时间
     */
    Date startTime;

    /**
     * 结束时间
     */
    Date endTime;

    /**
     * 当前房间用户数
     */
    private List<UserInfo> allPlayers;
    /**
     * 总比分
     */
    private Map<Long, Integer> pointsMap;

    /**
     * 房主
     */
    private UserInfo roomMainUser;


    /**
     * 进行中的回合
     */
    private BijiRoundInfo playingRound;

    /**
     * 房间状态
     */
    private BijiRoomStatus bijiRoomStatus;

    /**
     * 房间规则
     */
    private BijiRuleConfig ruleConfig;
    /**
     * 房间玩家总数
     */
    private Integer numOfPlayers;
    /**
     * 当前多少局
     */
    private Integer rounds;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomPassport() {
        return roomPassport;
    }

    public void setRoomPassport(String roomPassport) {
        this.roomPassport = roomPassport;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<UserInfo> getAllPlayers() {
        return allPlayers;
    }

    public void setAllPlayers(List<UserInfo> allPlayers) {
        this.allPlayers = allPlayers;
    }

    public Map<Long, Integer> getPointsMap() {
        return pointsMap;
    }

    public void setPointsMap(Map<Long, Integer> pointsMap) {
        this.pointsMap = pointsMap;
    }

    public UserInfo getRoomMainUser() {
        return roomMainUser;
    }

    public void setRoomMainUser(UserInfo roomMainUser) {
        this.roomMainUser = roomMainUser;
    }

    public BijiRoundInfo getPlayingRound() {
        return playingRound;
    }

    public void setPlayingRound(BijiRoundInfo playingRound) {
        this.playingRound = playingRound;
    }

    public BijiRoomStatus getBijiRoomStatus() {
        return bijiRoomStatus;
    }

    public void setBijiRoomStatus(BijiRoomStatus bijiRoomStatus) {
        this.bijiRoomStatus = bijiRoomStatus;
    }

    public BijiRuleConfig getRuleConfig() {
        return ruleConfig;
    }

    public void setRuleConfig(BijiRuleConfig ruleConfig) {
        this.ruleConfig = ruleConfig;
    }


    public Integer getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setNumOfPlayers(Integer numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public Integer getRounds() {
        return rounds;
    }

    public void setRounds(Integer rounds) {
        this.rounds = rounds;
    }
}
