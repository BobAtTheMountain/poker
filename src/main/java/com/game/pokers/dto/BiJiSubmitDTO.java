package com.game.pokers.dto;

public class BiJiSubmitDTO {

    private Boolean isSurrender;
    private int[] pokerIds;

    public Boolean getSurrender() {
        return isSurrender;
    }

    public void setSurrender(Boolean surrender) {
        isSurrender = surrender;
    }

    public int[] getPokerIds() {
        return pokerIds;
    }

    public void setPokerIds(int[] pokerIds) {
        this.pokerIds = pokerIds;
    }
}
