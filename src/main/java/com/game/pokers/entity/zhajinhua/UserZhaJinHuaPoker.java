package com.game.pokers.entity.zhajinhua;

public class UserZhaJinHuaPoker{
    private int[]poker3;
    private Long userId;

    public int[] getPoker3() {
        return poker3;
    }

    public void setPoker3(int[] poker3) {
        this.poker3 = poker3;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UserZhaJinHuaPoker(int[] poker3, Long userId) {
        this.poker3 = poker3;
        this.userId = userId;
    }
}
