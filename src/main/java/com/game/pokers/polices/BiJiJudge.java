package com.game.pokers.polices;

import com.game.pokers.constant.PokerColor;
import com.game.pokers.constant.PokerIndex;
import com.game.pokers.constant.biji.BijiRoundStatus;
import com.game.pokers.constant.biji.BijiXipai;
import com.game.pokers.constant.zhajinhua.ZhajinHuaType;
import com.game.pokers.dto.BiJiRoomInfoDTO;
import com.game.pokers.entity.UserInfo;
import com.game.pokers.entity.biji.BijiRoomInfo;
import com.game.pokers.entity.biji.BijiRoundInfo;
import com.game.pokers.entity.biji.BijiRuleConfig;
import com.game.pokers.entity.zhajinhua.UserZhaJinHuaPoker;
import com.game.pokers.mapper.BiJiMapper;
import com.game.pokers.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class BiJiJudge {
    /**
     * 校验比鸡9张牌是否合法
     */
    public static void validate(int[][]pokers) {
        if (pokers == null) {
            throw new RuntimeException("比鸡牌不能为空");
        }
        if (pokers.length != 3) {
            throw new RuntimeException("比鸡怎么不是三道子");
        }
        Map<Integer, Boolean> pokerSet = new HashMap<>();
        for (int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                if (pokers[i][j]<0 || pokers[i][j]>51) {
                    throw new RuntimeException("你这牌不对啊，我表示看不懂");
                }
                pokerSet.put(pokers[i][j], true);
            }
        }
        if (pokerSet.size() != 9) {
            throw new RuntimeException("你恐怕是有两张一样的牌");
        }
        int[] firstDao = pokers[0];
        int[] secondDao = pokers[1];
        int[] thirdDao = pokers[2];
        if (ZhaJinHuaJudge.zhaJinHuaCompare(firstDao, secondDao) > 0 ||
                ZhaJinHuaJudge.zhaJinHuaCompare(secondDao, thirdDao) > 0) {
            throw new RuntimeException("你怕是在倒鼓笼子");
        }
    }

    /**
     * 新回合
     */
    public static BijiRoundInfo newRound(BijiRoomInfo roomInfo) {
        if (roomInfo.getPlayingRound() != null && roomInfo.getPlayingRound().getStatus() != BijiRoundStatus.END
                && roomInfo.getPlayingRound().getStatus() != BijiRoundStatus.SETTLEMENT) {
            throw new RuntimeException("还有玩家未确认结果，请稍后再试");
        }
        BijiRoundInfo oldPlayRound = roomInfo.getPlayingRound();
        if (oldPlayRound != null) {
            oldPlayRound.setStatus(BijiRoundStatus.END);
        }
        BijiRoundInfo bijiRoundInfo = new BijiRoundInfo(roomInfo.getRoomId());
        bijiRoundInfo.setPokers(getRandomPokers(52));
        roomInfo.setPlayingRound(bijiRoundInfo);
        roomInfo.setRounds(roomInfo.getRounds()+1);
        return bijiRoundInfo;
    }

    public static void ready(BijiRoomInfo roomInfo, Long userId) {
        if (roomInfo.getPlayingRound() != null && roomInfo.getPlayingRound().getStatus() != BijiRoundStatus.STARTING) {
            throw new RuntimeException("还有玩家未确认结果，请稍后再试");
        }
        BijiRoundInfo playingRound = roomInfo.getPlayingRound();
        if (playingRound.getUserPlayMap().size() >= 5) {
            throw new RuntimeException("已经有5人准备");
        }
        playingRound.getUserPlayMap().put(userId, true);
    }

    /**
     * 开始发牌
     */
    public static void startRound(BijiRoomInfo roomInfo) {
        if (roomInfo.getPlayingRound() != null && roomInfo.getPlayingRound().getStatus() != BijiRoundStatus.STARTING) {
            throw new RuntimeException("请确认回合是否已开始");
        }
        BijiRoundInfo playingRound = roomInfo.getPlayingRound();
        if (playingRound.getUserPlayMap().size() < 2) {
            throw new RuntimeException("准备人数不足，不能开始");
        }
        // 发牌
        int pokerIndex = 0;
        for (int i=0;i<9;i++) {
            for (Long userId : playingRound.getUserPlayMap().keySet()) {
                playingRound.getInitPokers9().computeIfAbsent(userId, k -> new int[9]);
                playingRound.getInitPokers9().get(userId)[i] = playingRound.getPokers().get(pokerIndex);
                pokerIndex++;
            }
        }
        playingRound.setStatus(BijiRoundStatus.SUBMITTING);
    }

    public static List<Integer> getRandomPokers(int count) {
        List<Integer> res = new ArrayList<>();
        for (int i=0;i<count;i++) {
            res.add(i);
        }
        Collections.shuffle(res);
        return res;
    }


    /**
     * 仅仅算回合的分
     */
    public static void settlement(BijiRoomInfo roomInfo) {
        BijiRoundInfo roundInfo = roomInfo.getPlayingRound();
        roomInfo.getPlayingRound().setStatus(BijiRoundStatus.SETTLEMENT);
        // 提交的组合牌型
        Map<Long, int[][]> playerPoker9Map = roundInfo.getPokers9();
        // 投降列表
        List<Long> surrenders = roundInfo.getSurrenders();
        // 比较牌型
        List<UserZhaJinHuaPoker> touDaoUserZhaJinHuaPokers = getDescSortedJinHua(playerPoker9Map, 0);
        List<UserZhaJinHuaPoker> erDaoUserZhaJinHuaPokers = getDescSortedJinHua(playerPoker9Map, 1);
        List<UserZhaJinHuaPoker> sanDaoUserZhaJinHuaPokers = getDescSortedJinHua(playerPoker9Map, 2);

        // 初始化
        Map<Long, int[]> userPointInfoMap = new HashMap<>();
        for (Long userId : playerPoker9Map.keySet()) {
            userPointInfoMap.put(userId, new int[4]);
        }
        for (Long userId : surrenders) {
            userPointInfoMap.put(userId, new int[4]);
        }

        // 拉取房间配置规则
        BijiRuleConfig rule = roomInfo.getRuleConfig();

        // 算投降分数
        List<Integer> surrenderPoints = rule.getSurrenderPoints();
        int surrenderPoint = setSurrenderPoint(surrenders, surrenderPoints, userPointInfoMap);

        // 算每道比较得分
        List<Integer> comparePoint = rule.getComparePoints();
        setComparePoint(touDaoUserZhaJinHuaPokers, comparePoint, 0, userPointInfoMap, surrenderPoint);
        setComparePoint(erDaoUserZhaJinHuaPokers, comparePoint, 1, userPointInfoMap, surrenderPoint);
        setComparePoint(sanDaoUserZhaJinHuaPokers, comparePoint, 2, userPointInfoMap, surrenderPoint);

        // 拉取比鸡喜牌
        Map<Long, List<BijiXipai>> allBijiXiPaiMap = getBiJiXiPai(playerPoker9Map, surrenders);

        // 算喜牌分数
        Map<BijiXipai, Integer> xiPaiPointMap = rule.getXiPaiPointMap();
        setXiPaiPoint(allBijiXiPaiMap, xiPaiPointMap, userPointInfoMap, surrenders);

        // 总计
        Map<Long, Integer> pointMap = new HashMap<>();
        for (Long userId : userPointInfoMap.keySet()) {
            int point = 0;
            for (int p : userPointInfoMap.get(userId)) {
                point += p;
            }
            pointMap.put(userId, point);
        }
        roundInfo.setUserPointInfoMap(userPointInfoMap);
        roundInfo.setUserXipaiMap(allBijiXiPaiMap);

        roundInfo.setUserRountPointMap(pointMap);

        // 总分合并到房间里
        for (Long userId : pointMap.keySet()) {
            Integer oldPoint = roomInfo.getPointsMap().get(userId);
            oldPoint += pointMap.get(userId);
            roomInfo.getPointsMap().put(userId, oldPoint);
        }
    }
    private static void setXiPaiPoint(Map<Long, List<BijiXipai>> allBijiXiPaiMap, Map<BijiXipai, Integer> xiPaiPointMap, Map<Long, int[]> userPointInfoMap, List<Long> surrenders) {
        Map<Long, Integer> userXiPaiPoint = new HashMap<>();
        for (Long userId : allBijiXiPaiMap.keySet()) {
            List<BijiXipai> xipais = allBijiXiPaiMap.getOrDefault(userId, new ArrayList<>());
            int point = 0;
            for (BijiXipai xipai : xipais) {
                point += xiPaiPointMap.getOrDefault(xipai, 0);
            }
            userXiPaiPoint.put(userId, point);
        }
        if (surrenders == null) {
            surrenders = new ArrayList<>();
        }
        for (Long userId : userXiPaiPoint.keySet()) {
            int xiPaiPoint =  userXiPaiPoint.get(userId);
            for (Long thatUserId : userPointInfoMap.keySet()) {
                if (!Objects.equals(thatUserId, userId) && !surrenders.contains(thatUserId)) {
                    userPointInfoMap.get(thatUserId)[3] -= xiPaiPoint;
                    userPointInfoMap.get(userId)[3] += xiPaiPoint;
                }
            }
        }
    }

    private static int setSurrenderPoint(List<Long> surrenders, List<Integer> surrenderPoints, Map<Long, int[]> userPointInfoMap) {
        int surrenderPoint = 0;
        for (int i=0;i<surrenders.size();i++) {
            Long userId = surrenders.get(i);
            Integer point = surrenderPoints.get(i);
            Arrays.fill(userPointInfoMap.get(userId), -1 * point);
            surrenderPoint += point;
        }
        return surrenderPoint;
    }

    private static void setComparePoint(List<UserZhaJinHuaPoker> sorted, List<Integer> comparePoint, int daoshu, Map<Long, int[]> userPointInfoMap, int surrenderPoint) {
        int toudaoPoint = 0;
        for (int i=1;i<sorted.size();i++) {
            Long userId = sorted.get(i).getUserId();
            Integer point = comparePoint.get(i-1);
            userPointInfoMap.get(userId)[daoshu] = -1 * point;
            toudaoPoint += point;
        }
        userPointInfoMap.get(sorted.getFirst().getUserId())[daoshu] = toudaoPoint + surrenderPoint;
    }

    /**
     * 计算喜牌
     */
    public static Map<Long, List<BijiXipai>> getBiJiXiPai(
            Map<Long,int[][]> pokers9Map, List<Long> surrenders) {
        Map<Long, List<BijiXipai>> userXiPaiMap = new HashMap<>();
        List<UserZhaJinHuaPoker> touDaoUserZhaJinHuaPokers = getDescSortedJinHua(pokers9Map, 0);
        List<UserZhaJinHuaPoker> erDaoUserZhaJinHuaPokers = getDescSortedJinHua(pokers9Map, 1);
        List<UserZhaJinHuaPoker> sanDaoUserZhaJinHuaPokers = getDescSortedJinHua(pokers9Map, 2);
        for (Long userId : pokers9Map.keySet()) {
            touDaoUserZhaJinHuaPokers.add(new UserZhaJinHuaPoker(pokers9Map.get(userId)[0], userId));
            erDaoUserZhaJinHuaPokers.add(new UserZhaJinHuaPoker(pokers9Map.get(userId)[1], userId));
            sanDaoUserZhaJinHuaPokers.add(new UserZhaJinHuaPoker(pokers9Map.get(userId)[2], userId));
            userXiPaiMap.put(userId, getBiJiXiPai(pokers9Map.get(userId)));
        }
        for (Long userId : surrenders) {
            userXiPaiMap.put(userId, new ArrayList<>());
        }
        touDaoUserZhaJinHuaPokers.sort((o1, o2) -> -1 * ZhaJinHuaJudge.zhaJinHuaCompare(o1.getPoker3(), o2.getPoker3()));
        erDaoUserZhaJinHuaPokers.sort((o1, o2) ->-1 * ZhaJinHuaJudge.zhaJinHuaCompare(o1.getPoker3(), o2.getPoker3()));
        sanDaoUserZhaJinHuaPokers.sort((o1, o2) ->-1 * ZhaJinHuaJudge.zhaJinHuaCompare(o1.getPoker3(), o2.getPoker3()));
        if (Objects.equals(touDaoUserZhaJinHuaPokers.getFirst().getUserId(), erDaoUserZhaJinHuaPokers.getFirst().getUserId()) &&
                Objects.equals(erDaoUserZhaJinHuaPokers.getFirst().getUserId(), sanDaoUserZhaJinHuaPokers.getFirst().getUserId())) {
            userXiPaiMap.get(touDaoUserZhaJinHuaPokers.getFirst().getUserId()).add(BijiXipai.SAN_TONG);
        }
        return userXiPaiMap;
    }

    private static List<UserZhaJinHuaPoker> getDescSortedJinHua(Map<Long,int[][]> pokers9Map, int index) {
        List<UserZhaJinHuaPoker> res = new ArrayList<>();
        for (Long userId : pokers9Map.keySet()) {
            res.add(new UserZhaJinHuaPoker(pokers9Map.get(userId)[index], userId));
        }
        res.sort((o1, o2) -> -1 * ZhaJinHuaJudge.zhaJinHuaCompare(o1.getPoker3(), o2.getPoker3()));
        return res;
    }

    public static List<BijiXipai> getBiJiXiPai (int[][]poker9) {
        List<BijiXipai> resList = new ArrayList<>();
        int[] indexies = new int[13];

        // 全黑判断
        boolean quanHei = true;
        for (int[] poker3 : poker9) {
            for (int poker : poker3) {
                indexies[PokerIndex.getIndex(poker).getIndex()] ++;
                if (PokerColor.getColor(poker) != PokerColor.Club && PokerColor.getColor(poker) != PokerColor.Spade) {
                    quanHei = false;
                }
            }
        }
        if (quanHei) {
            resList.add(BijiXipai.QUAN_HEI);
        }
        // 全黑判断
        boolean quanHong = true;
        for (int[] poker3 : poker9) {
            for (int poker : poker3) {
                if (PokerColor.getColor(poker) != PokerColor.Heart && PokerColor.getColor(poker) != PokerColor.Diamond) {
                    quanHong = false;
                }
            }
        }
        if (quanHong) {
            resList.add(BijiXipai.QUAN_HONG);
        }

        int[] firstDao = poker9[0];
        int[] secondDao = poker9[1];
        int[] thirdDao = poker9[2];

        ZhajinHuaType firstDaoType = ZhaJinHuaJudge.getZhaJinHuaType(firstDao);
        ZhajinHuaType secondDaoType = ZhaJinHuaJudge.getZhaJinHuaType(secondDao);
        ZhajinHuaType thirdDaoType = ZhaJinHuaJudge.getZhaJinHuaType(thirdDao);


        if (firstDaoType == ZhajinHuaType.JIN_HUA) {
            resList.add(BijiXipai.TOUDAO_JINHUA);
        }
        if (firstDaoType == ZhajinHuaType.SHUN_QING) {
            resList.add(BijiXipai.TOUDAO_SHUN_QING);
        }
        if (firstDaoType == ZhajinHuaType.SAN_GE_TOU) {
            resList.add(BijiXipai.TOUDAO_SANGETOU);
        }
        if (secondDaoType == ZhajinHuaType.SHUN_QING) {
            resList.add(BijiXipai.ERDAO_SHUN_QING);
        }
        if (secondDaoType == ZhajinHuaType.SAN_GE_TOU) {
            resList.add(BijiXipai.ERDAO_SAN_GE_TOU);
        }

        // 炸弹
        int zhaDanCount = 0;
        for (int count : indexies) {
            if (count == 4) {
                zhaDanCount ++;
            }
        }
        if (zhaDanCount == 1) {
            resList.add(BijiXipai.ZHA_DAN);
        }
        if (zhaDanCount == 2) {
            resList.add(BijiXipai.SHUANG_ZHA_DAN);
        }
        return resList;
    }

    public static int[][] getBijiPokers(int[]pokers) {
        int[][]configPokers = new int[3][3];
        configPokers[0][0] = pokers[0];
        configPokers[0][1] = pokers[1];
        configPokers[0][2] = pokers[2];
        configPokers[1][0] = pokers[3];
        configPokers[1][1] = pokers[4];
        configPokers[1][2] = pokers[5];
        configPokers[2][0] = pokers[6];
        configPokers[2][1] = pokers[7];
        configPokers[2][2] = pokers[8];
        return configPokers;
    }

    public static boolean isAllPlayerSubmitted(BijiRoundInfo roundInfo) {
        for (Long userId : roundInfo.getUserPlayMap().keySet()) {
            if (roundInfo.getPokers9().get(userId) == null && (!roundInfo.getSurrenders().contains(userId))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String s = "{\"pointsMap\":{},\"allPlayers\":[{\"userId\":2,\"nikeName\":\"桂越2\"},{\"userId\":1,\"nikeName\":\"桂越\"}],\"roomId\":17,\"roomSeq\":null,\"startTime\":\"2024-01-14 08:04:16\",\"endTime\":null,\"status\":\"STARTED\",\"mainUser\":{\"userId\":2,\"nikeName\":\"桂越2\"},\"roomPassport\":\"1\",\"ruleConfig\":{\"xiPaiPointMap\":{\"QUAN_HEI\":1,\"QUAN_HONG\":1,\"TOUDAO_JINHUA\":1,\"TOUDAO_SHUN_QING\":1,\"TOUDAO_SANGETOU\":1,\"ERDAO_SHUN_QING\":1,\"ERDAO_SAN_GE_TOU\":1,\"ZHA_DAN\":1,\"SHUANG_ZHA_DAN\":1,\"SAN_TONG\":1},\"surrenderNum\":1,\"comparePoints\":[1,1,1,1],\"surrenderPoints\":null,\"xiPaiAddition\":null},\"endedRoundInfos\":[],\"playingRound\":{\"roundId\":8,\"roomId\":17,\"startTime\":\"2024-01-14 08:04:30\",\"pokers\":[45,9,26,28,47,38,8,35,37,11,1,31,6,49,23,44,10,7,24,16,50,22,32,3,0,25,2,21,43,39,41,33,18,40,4,19,13,34,48,15,46,51,42,30,14,36,5,20,29,27,17,12],\"initPokers9\":{\"1\":[45,26,47,8,37,1,6,23,10],\"2\":[9,28,38,35,11,31,49,44,7]},\"userPlayMap\":{\"1\":true,\"2\":true},\"pokers9\":{\"1\":[[8,23,47],[6,10,26],[1,37,45]],\"2\":[[28,38,44],[7,9,49],[11,31,35]]},\"surrenders\":[],\"userXipaiMap\":{\"1\":[\"ZHA_DAN\"],\"2\":[\"ZHA_DAN\"]},\"userPointInfoMap\":{\"1\":[1,-1,-1,0],\"2\":[1,0,0,0]},\"userRountPointMap\":{\"1\":-1,\"2\":1},\"status\":\"SETTLEMENT\",\"endTime\":null},\"thisUserId\":1,\"numOfPlayers\":2}";
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        BijiRoomInfo info = gson.fromJson(s, BijiRoomInfo.class);
        settlement(info);


    }
}
