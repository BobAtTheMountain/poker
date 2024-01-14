package com.game.pokers.dto;

import com.game.pokers.entity.biji.BijiRuleConfig;

public class BijiRoomConfigDTO {
    BijiRuleConfig rule;
    String roomPassport;
    Integer numOfPlayers;

    public BijiRuleConfig getRule() {
        return rule;
    }

    public void setRule(BijiRuleConfig rule) {
        this.rule = rule;
    }

    public String getRoomPassport() {
        return roomPassport;
    }

    public void setRoomPassport(String roomPassport) {
        this.roomPassport = roomPassport;
    }

    public Integer getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setNumOfPlayers(Integer numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }
}
