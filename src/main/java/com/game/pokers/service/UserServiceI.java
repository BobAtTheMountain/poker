package com.game.pokers.service;

import com.game.pokers.entity.UserInfo;

public interface UserServiceI {
    Long saveUser(UserInfo userInfo);
    UserInfo getUserByName(String name);
    UserInfo getUserById(Long userId);
    UserInfo getUserByPhone(String phone);
}
