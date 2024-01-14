package com.game.pokers.service;

import com.game.pokers.dao.UserRepo;
import com.game.pokers.db.UserInfoDO;
import com.game.pokers.entity.UserInfo;
import com.game.pokers.mapper.BiJiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserService implements UserServiceI {

    @Autowired
    public UserRepo userRepo;

    @Override
    public Long saveUser(UserInfo userInfo) {
        UserInfoDO dbO = BiJiMapper.toDO(userInfo);
        dbO.setCreateTime(new Date());
        dbO.setUpdateTime(new Date());
        return userRepo.save(dbO).getId();
    }

    @Override
    public UserInfo getUserByName(String name) {
        UserInfoDO userDo = userRepo.findByName(name);
        return BiJiMapper.toEntity(userDo);
    }

    @Override
    public UserInfo getUserById(Long userId) {
        UserInfoDO dbUser = userRepo.findById(userId).orElse(null);
        return BiJiMapper.toEntity(dbUser);
    }

    @Override
    public UserInfo getUserByPhone(String phone) {
        UserInfoDO userDo = userRepo.findByPhone(phone);
        return BiJiMapper.toEntity(userDo);
    }
}
