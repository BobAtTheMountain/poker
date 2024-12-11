package com.game.pokers.controller;

import com.game.pokers.constant.biji.BijiRoomStatus;
import com.game.pokers.constant.biji.BijiRoundStatus;
import com.game.pokers.dto.*;
import com.game.pokers.entity.UserInfo;
import com.game.pokers.entity.biji.BijiRoomInfo;
import com.game.pokers.entity.biji.BijiRoundInfo;
import com.game.pokers.global.Global;
import com.game.pokers.mapper.BiJiMapper;
import com.game.pokers.polices.BiJiJudge;
import com.game.pokers.service.BijiServiceI;
import com.game.pokers.service.UserServiceI;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.game.pokers.constant.ErrorCode.*;
import static com.game.pokers.polices.BiJiJudge.settlement;

@RestController
public class BiJiController {

    @Resource
    BijiServiceI bijiService;

    @Resource
    UserServiceI userService;


    /**
     * 创建房间
     */
    @RequestMapping(value = "/createRoom", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResDTO<BiJiRoomInfoDTO> createRoom(HttpServletRequest request, @RequestBody BijiRoomConfigDTO configDTO) {
        Long userId = (Long) request.getSession().getAttribute(Global.SESSION_USER_ID);
        if (userId == null) {
            return ResDTO.getErrResDTO(TO_LOGGING, "请重新登陆");
        }
        Long oldRoomId = Global.USER_ROOMID_MAP.get(userId);
        if (oldRoomId != null && oldRoomId > 0) {
            if (Global.BIJI_GLOBAL_ROOM_MAP.get(oldRoomId) != null && Global.BIJI_GLOBAL_ROOM_MAP.get(oldRoomId).getBijiRoomStatus() != BijiRoomStatus.END) {
                BijiRoomInfo roomInfo = bijiService.getRoom(oldRoomId);
                return ResDTO.getErrResDTO(TO_ROOM_PAGE, "您已在其他房间中，暂时无法创建房间。房间序号：" + roomInfo.getRoomId() + "，房间密码：" + roomInfo.getRoomPassport());
            }
            Global.USER_ROOMID_MAP.remove(userId);
            Global.BIJI_GLOBAL_ROOM_MAP.remove(oldRoomId);
        }
        UserInfo userInfo = userService.getUserById(userId);

        BijiRoomInfo roomInfo = new BijiRoomInfo();
        // TODO 校验规则

        // 填充
        roomInfo.setRoomPassport(configDTO.getRoomPassport());
        roomInfo.setStartTime(new Date());
        roomInfo.setBijiRoomStatus(BijiRoomStatus.CREATED);
        roomInfo.setAllPlayers(new ArrayList<>());
        roomInfo.setPointsMap(new HashMap<>());
        roomInfo.setRoomMainUser(userInfo);
        roomInfo.setRuleConfig(configDTO.getRule());
        roomInfo.setNumOfPlayers(configDTO.getNumOfPlayers());
        roomInfo.setRounds(0);

        roomInfo.getAllPlayers().add(userInfo);
        roomInfo.getPointsMap().put(userInfo.getId(), 0);

        // 创建房间
        Long roomId = bijiService.createRoom(roomInfo);
        roomInfo.setRoomId(roomId);

        // 设置global
        Global.USER_ROOMID_MAP.put(userId, roomId);
        Global.BIJI_GLOBAL_ROOM_MAP.put(roomId, roomInfo);
        return ResDTO.getSuccessResDTO(BiJiMapper.toDTO(roomInfo));
    }


    @RequestMapping(value = "/enterRoom", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public ResDTO<BiJiRoomInfoDTO> enterRoom(HttpServletRequest request, @RequestParam Long roomId, @RequestParam String password) {
        Long userId = (Long) request.getSession().getAttribute(Global.SESSION_USER_ID);
        if (userId == null) {
            return ResDTO.getErrResDTO(TO_LOGGING, "请重新登陆");
        }
        BijiRoomInfo roomInfo = Global.BIJI_GLOBAL_ROOM_MAP.get(roomId);
        if (roomInfo == null || roomInfo.getBijiRoomStatus() == BijiRoomStatus.END) {
            return ResDTO.getErrResDTO(PARAM_ERROR,"房间序号不正确");
        }
        Long oldRoomId = Global.USER_ROOMID_MAP.get(userId);
        if (oldRoomId != null && oldRoomId > 0 && !Objects.equals(roomId, oldRoomId)) {
            if (Global.BIJI_GLOBAL_ROOM_MAP.get(oldRoomId) != null && Global.BIJI_GLOBAL_ROOM_MAP.get(oldRoomId).getBijiRoomStatus() != BijiRoomStatus.END) {
                BijiRoomInfo oldRoomInfo = bijiService.getRoom(oldRoomId);
                return ResDTO.getErrResDTO(TO_ENTER_PAGE, "您已在其他房间中，房间序号：" + oldRoomInfo.getRoomId() + "，房间密码：" + oldRoomInfo.getRoomPassport());
            }
            Global.USER_ROOMID_MAP.remove(userId);
            Global.BIJI_GLOBAL_ROOM_MAP.remove(oldRoomId);
        }
        if (!Objects.equals(roomInfo.getRoomPassport(), password)) {
            return ResDTO.getErrResDTO(PARAM_ERROR, "房间密码不正确");
        }

        synchronized (roomInfo) {
            UserInfo userInfo = userService.getUserById(userId);

            boolean isExisted = false;
            for (UserInfo u : roomInfo.getAllPlayers()) {
                if (Objects.equals(u.getId(), userId)) {
                    isExisted = true;
                    break;
                }
            }
            // 房间是否满员
            if (!isExisted && roomInfo.getAllPlayers().size() == 5) {
                return ResDTO.getErrResDTO(PARAM_ERROR, "该房间已满员");
            }
            // 不存在的话加进来
            if (!isExisted) {
                roomInfo.getAllPlayers().add(userInfo);
                roomInfo.getPointsMap().put(userInfo.getId(), 0);
            }

            // 设置global
            Global.USER_ROOMID_MAP.put(userId, roomId);

            // 如果满足条件，则设置roomId状态
            if (roomInfo.getAllPlayers().size() == roomInfo.getNumOfPlayers()) {
                BijiRoundInfo newRoundPlayingInfo = BiJiJudge.newRound(roomInfo);
                roomInfo.setBijiRoomStatus(BijiRoomStatus.STARTED);
                Long roundId = bijiService.createRound(newRoundPlayingInfo);
                newRoundPlayingInfo.setRoundId(roundId);
                for (UserInfo player : roomInfo.getAllPlayers()) {
                    newRoundPlayingInfo.getUserPlayMap().put(player.getId(), true);
                }
                newRoundPlayingInfo.setStatus(BijiRoundStatus.STARTING);
                BiJiJudge.startRound(roomInfo);
            }
            return ResDTO.getSuccessResDTO(BiJiMapper.toDTO(roomInfo));
        }
    }

    @RequestMapping(value = "/roomInfo", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public ResDTO<BiJiRoomInfoDTO> roomInfo(HttpServletRequest request) {
        // TODO 不要看到别人的牌
        Long userId = (Long) request.getSession().getAttribute(Global.SESSION_USER_ID);
        if (userId == null) {
            return ResDTO.getErrResDTO(TO_LOGGING, "请重新登陆");
        }
        Long roomId = Global.USER_ROOMID_MAP.get(userId);
        if (roomId == null) {
            return ResDTO.getErrResDTO(TO_ENTER_PAGE, "您未绑定直播间或绑定的直播间被关闭里，请跳转进房页");
        }
        // 设置global
        BijiRoomInfo roomInfo = Global.BIJI_GLOBAL_ROOM_MAP.get(roomId);
        if (roomInfo == null) {
            Global.USER_ROOMID_MAP.remove(userId);
            return ResDTO.getErrResDTO(TO_ENTER_PAGE, "您未绑定直播间或绑定的直播间被关闭里，请跳转进房页");
        }
        BiJiRoomInfoDTO roomInfoDTO = BiJiMapper.toDTO(roomInfo);
        roomInfoDTO.setThisUserId(userId);
        return ResDTO.getSuccessResDTO(roomInfoDTO);
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public ResDTO<Boolean> submit(HttpServletRequest request, @RequestBody BiJiSubmitDTO submitDTO) {
        Long userId = (Long) request.getSession().getAttribute(Global.SESSION_USER_ID);
        if (userId == null) {
            return ResDTO.getErrResDTO(TO_LOGGING, "请重新登陆");
        }
        Long roomId = Global.USER_ROOMID_MAP.get(userId);
        if (roomId == null) {
            return ResDTO.getErrResDTO(TO_ENTER_PAGE, "请先进入房间");
        }
        // 设置global
        BijiRoomInfo roomInfo = Global.BIJI_GLOBAL_ROOM_MAP.get(roomId);
        if (roomInfo == null) {
            Global.USER_ROOMID_MAP.remove(userId);
            return ResDTO.getErrResDTO(TO_ENTER_PAGE, "请先进入房间");
        }
        synchronized (roomInfo) {
            if (roomInfo.getPlayingRound() == null || roomInfo.getPlayingRound().getStatus() != BijiRoundStatus.SUBMITTING) {
                return ResDTO.getErrResDTO(PARAM_ERROR, "比赛还没开始");
            }
            if (roomInfo.getPlayingRound().getInitPokers9().get(userId) == null ){
                return ResDTO.getErrResDTO(PARAM_ERROR, "您不再本回合游戏里");
            }
            if (submitDTO.getSurrender()) {
                if (!roomInfo.getPlayingRound().getSurrenders().isEmpty()) {
                    return ResDTO.getErrResDTO(PARAM_ERROR, "已有人投降了，你晚了");
                }
                roomInfo.getPlayingRound().getSurrenders().add(userId);
            }else {
                int[] initPokers = roomInfo.getPlayingRound().getInitPokers9().get(userId);
                // TODO 比较前后是否一致 initPokers, pokerIds
                int[][] configPokers = BiJiJudge.getBijiPokers(submitDTO.getPokerIds());
                roomInfo.getPlayingRound().getPokers9().put(userId, configPokers);
            }

            // 更新本局状态
            if (BiJiJudge.isAllPlayerSubmitted(roomInfo.getPlayingRound())) {
                settlement(roomInfo);
                bijiService.updateRound(roomInfo.getPlayingRound());
                bijiService.updateRoom(roomInfo);
            }
            return ResDTO.getSuccessResDTO(true);
        }
    }

    @RequestMapping(value = "/ready", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public ResDTO<String> ready(HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute(Global.SESSION_USER_ID);
        if (userId == null) {
            return ResDTO.getErrResDTO(TO_LOGGING, "请重新登陆");
        }
        Long roomId = Global.USER_ROOMID_MAP.get(userId);
        if (roomId == null) {
            return ResDTO.getErrResDTO(TO_ENTER_PAGE, "请先进入房间");
        }
        // 设置global
        BijiRoomInfo roomInfo = Global.BIJI_GLOBAL_ROOM_MAP.get(roomId);
        if (roomInfo == null) {
            Global.USER_ROOMID_MAP.remove(userId);
            return ResDTO.getErrResDTO(TO_ENTER_PAGE, "请先进入房间");
        }
        synchronized (roomInfo) {
            if (roomInfo.getPlayingRound() == null
                    || roomInfo.getPlayingRound().getStatus() != BijiRoundStatus.SETTLEMENT
                    && roomInfo.getPlayingRound().getStatus() != BijiRoundStatus.END
                    && roomInfo.getPlayingRound().getStatus() != BijiRoundStatus.STARTING
            ) {
                return ResDTO.getErrResDTO(PARAM_ERROR, "当前不允许准备请求");
            }
            if (roomInfo.getPlayingRound().getStatus() == BijiRoundStatus.STARTING) {
                BiJiJudge.ready(roomInfo, userId);
            } else {
                BijiRoundInfo roundInfo = BiJiJudge.newRound(roomInfo);
                Long roundId = bijiService.createRound(roundInfo);
                roundInfo.setRoundId(roundId);
                roundInfo.getUserPlayMap().put(userId, true);
            }

            if (roomInfo.getPlayingRound().getUserPlayMap().size() == roomInfo.getNumOfPlayers()) {
                BiJiJudge.startRound(roomInfo);
                bijiService.updateRound(roomInfo.getPlayingRound());
            }
            return ResDTO.getSuccessResDTO("成功");
        }
    }

    @RequestMapping(value = "/endRoom", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public ResDTO<String> endRoom(HttpServletRequest request, @RequestParam(required=false) Long endRoomId) {
        Long userId = (Long) request.getSession().getAttribute(Global.SESSION_USER_ID);
        if (userId == null) {
            return ResDTO.getErrResDTO(TO_LOGGING, "请重新登陆");
        }
        BijiRoomInfo roomInfo;
        if (userId == 1 && endRoomId != null) {
            // 设置global
            roomInfo = Global.BIJI_GLOBAL_ROOM_MAP.get(endRoomId);
            if (roomInfo == null) {
                return ResDTO.getSuccessResDTO( "终止成功");
            }
        }else {
            Long roomId = Global.USER_ROOMID_MAP.get(userId);
            if (roomId == null) {
                return ResDTO.getErrResDTO(PARAM_ERROR, "请先进入房间");
            }
            // 设置global
            roomInfo = Global.BIJI_GLOBAL_ROOM_MAP.get(roomId);
            if (roomInfo == null) {
                Global.USER_ROOMID_MAP.remove(userId);
                return ResDTO.getErrResDTO(PARAM_ERROR, "房间已结束或异常结束，请创建新房间");
            }
            if (roomInfo.getPlayingRound() != null
                    && roomInfo.getPlayingRound().getStatus() != BijiRoundStatus.SETTLEMENT
                    && roomInfo.getPlayingRound().getStatus() != BijiRoundStatus.END
                    && roomInfo.getPlayingRound().getStatus() != BijiRoundStatus.STARTING) {
                return ResDTO.getErrResDTO(PARAM_ERROR, "存在正在进行的比赛，请稍后操作");
            }
        }
        synchronized (roomInfo) {
            roomInfo.setBijiRoomStatus(BijiRoomStatus.END);
            roomInfo.setEndTime(new Date());
            bijiService.updateRoom(roomInfo);
            List<UserInfo> users = roomInfo.getAllPlayers();
            users.forEach(u -> {
                if (Objects.equals(Global.USER_ROOMID_MAP.get(u.getId()), roomInfo.getRoomId())) {
                    Global.USER_ROOMID_MAP.remove(u.getId());
                }
            });
            return ResDTO.getSuccessResDTO("成功");
        }
    }

    @RequestMapping(value = "/roomHistory", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public ResDTO<List<BiJiRoomInfoDTO>> roomHistory(HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute(Global.SESSION_USER_ID);
        if (userId == null) {
            return ResDTO.getErrResDTO(TO_LOGGING, "请重新登陆");
        }
        List<BijiRoomInfo> historyRooms = bijiService.listRoom(userId, 5000);
        List<BiJiRoomInfoDTO> historyRoomDTOs =  historyRooms.stream().map(BiJiMapper::toDTO).toList();
        historyRoomDTOs.forEach(o->o.setThisUserId(userId));
        return ResDTO.getSuccessResDTO(historyRoomDTOs);
    }

    @RequestMapping(value = "/roundHistory", method = RequestMethod.GET, produces="application/json;charset=UTF-8")
    public ResDTO<List<BiJiRoundInfoDTO>> roundHistory(HttpServletRequest request, @RequestParam Long roomId) {
        Long userId = (Long) request.getSession().getAttribute(Global.SESSION_USER_ID);
        if (userId == null) {
            return ResDTO.getErrResDTO(TO_LOGGING, "请重新登陆");
        }
        List<BijiRoundInfo> historyRounds = bijiService.listRound(roomId);
        List<BiJiRoundInfoDTO> historyRoundDTOs =  historyRounds.stream().map(BiJiMapper::toDTO).toList();
        return ResDTO.getSuccessResDTO(historyRoundDTOs);
    }
}
