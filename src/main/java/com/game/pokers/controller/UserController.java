package com.game.pokers.controller;

import com.game.pokers.constant.ErrorCode;
import com.game.pokers.dto.ResDTO;
import com.game.pokers.dto.UserInfoDTO;
import com.game.pokers.entity.UserInfo;
import com.game.pokers.global.Global;
import com.game.pokers.service.UserServiceI;
import com.game.pokers.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserServiceI userService;

    @RequestMapping(value = "/doRegister", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public ResDTO<Long> doRegister(
            HttpServletRequest request, ModelMap model, @RequestParam String phone, @RequestParam String password,
            @RequestParam String name) {
        if(!Utils.isPhoneLegal(phone)) {
            return ResDTO.getErrResDTO(ErrorCode.PARAM_ERROR, "手机错误号格式错误！");
        }
        if (userService.getUserByPhone(phone) != null) {
            return ResDTO.getErrResDTO(ErrorCode.PARAM_ERROR, "该手机号已注册过！");
        }
        if (userService.getUserByName(name) != null) {
            return ResDTO.getErrResDTO(ErrorCode.PARAM_ERROR, "该名称已注册过！");
        }
        if (password == null || password.length() < 4) {
            return ResDTO.getErrResDTO(ErrorCode.PARAM_ERROR, "密码长度小于4位！");
        }
        UserInfo newUserInfo = new UserInfo(name, phone, password);
        Long userId = userService.saveUser(newUserInfo);
        request.getSession().setAttribute(Global.SESSION_USER_ID, userId);
        return ResDTO.getSuccessResDTO(userId);
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public ResDTO<UserInfoDTO> doLogin(
            HttpServletRequest request, @RequestParam String phone, @RequestParam String password){
        if(!Utils.isPhoneLegal(phone)) {
            return ResDTO.getErrResDTO(ErrorCode.PARAM_ERROR, "手机错误号格式错误！");
        }
        UserInfo userInfo = userService.getUserByPhone(phone);
        if (userInfo == null) {
            return ResDTO.getErrResDTO(ErrorCode.PARAM_ERROR, "您的手机号不存在！");
        }
        if(!password.equals(userInfo.getPassword())) {
            return ResDTO.getErrResDTO(ErrorCode.PARAM_ERROR, " 密码不正确！");
        }
        request.getSession().setAttribute(Global.SESSION_USER_ID, userInfo.getId());
        return ResDTO.getSuccessResDTO(new UserInfoDTO(userInfo.getId(), userInfo.getNickName()));
    }
}
