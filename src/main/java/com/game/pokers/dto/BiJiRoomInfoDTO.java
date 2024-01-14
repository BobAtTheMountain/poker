package com.game.pokers.dto;

import com.game.pokers.constant.biji.BijiRoomStatus;
import com.game.pokers.entity.biji.BijiRuleConfig;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class BiJiRoomInfoDTO {
    private Map<Long, Integer> pointsMap;
    private List<UserInfoDTO> allPlayers;

    private Long roomId;

    private String roomSeq;

    private String startTime;

    private String endTime;

    private BijiRoomStatus status;

    private UserInfoDTO mainUser;

    private String roomPassport;

    private BijiRuleConfig ruleConfig;

    private BiJiRoundInfoDTO playingRoundInfo;

    private Long thisUserId;
    private Integer numOfPlayers;
    private Integer rounds;

    public BiJiRoundInfoDTO getPlayingRoundInfo() {
        return playingRoundInfo;
    }

    public void setPlayingRoundInfo(BiJiRoundInfoDTO playingRoundInfo) {
        this.playingRoundInfo = playingRoundInfo;
    }

    public List<UserInfoDTO> getAllPlayers() {
        return allPlayers;
    }

    public void setAllPlayers(List<UserInfoDTO> allPlayers) {
        this.allPlayers = allPlayers;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomSeq() {
        return roomSeq;
    }

    public void setRoomSeq(String roomSeq) {
        this.roomSeq = roomSeq;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public BijiRoomStatus getStatus() {
        return status;
    }

    public void setStatus(BijiRoomStatus status) {
        this.status = status;
    }

    public UserInfoDTO getMainUser() {
        return mainUser;
    }

    public void setMainUser(UserInfoDTO mainUser) {
        this.mainUser = mainUser;
    }

    public String getRoomPassport() {
        return roomPassport;
    }

    public void setRoomPassport(String roomPassport) {
        this.roomPassport = roomPassport;
    }

    public BijiRuleConfig getRuleConfig() {
        return ruleConfig;
    }

    public void setRuleConfig(BijiRuleConfig ruleConfig) {
        this.ruleConfig = ruleConfig;
    }


    public Long getThisUserId() {
        return thisUserId;
    }

    public void setThisUserId(Long thisUserId) {
        this.thisUserId = thisUserId;
    }

    public Map<Long, Integer> getPointsMap() {
        return pointsMap;
    }

    public void setPointsMap(Map<Long, Integer> pointsMap) {
        this.pointsMap = pointsMap;
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
