package com.game.pokers.entity;

public class UserInfo {

    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 电话号
     */
    private String phoneNumber;
    /**
     * 密码
     */
    private String password;

    public UserInfo(Long id, String nickName, String phoneNumber, String passport) {
        this.id = id;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.password = passport;
    }
    public UserInfo(String nickName, String phoneNumber, String passport) {
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.password = passport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
