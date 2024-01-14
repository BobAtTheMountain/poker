package com.game.pokers.dto;

public class UserInfoDTO {
    Long userId;
    String nikeName;

    public UserInfoDTO(Long userId, String nikeName) {
        this.userId = userId;
        this.nikeName = nikeName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }
}
