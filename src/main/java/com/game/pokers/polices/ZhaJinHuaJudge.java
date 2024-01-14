package com.game.pokers.polices;

import com.game.pokers.constant.PokerColor;
import com.game.pokers.constant.PokerIndex;
import com.game.pokers.constant.zhajinhua.ZhajinHuaType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ZhaJinHuaJudge {

    /**
     * true -> pokers1 小于 pokers2
     */
    public static int zhaJinHuaCompare(int[] pokers1, int[] pokers2) {
        ZhajinHuaType type1 = getZhaJinHuaType(pokers1);
        ZhajinHuaType type2 = getZhaJinHuaType(pokers2);
        if (type1.getIndex() < type2.getIndex()) {
            return -1;
        }
        if (type1.getIndex() > type2.getIndex()) {
            return 1;
        }

        Arrays.sort(pokers1);
        Arrays.sort(pokers2);

        for (int i=0;i<3;i++) {
            int p1 = pokers1[2-i];
            int p2 = pokers2[2-i];
            if (PokerIndex.getIndex(p1).getIndex() < PokerIndex.getIndex(p2).getIndex()) {
                return -1;
            }
            if (PokerIndex.getIndex(p1).getIndex() > PokerIndex.getIndex(p2).getIndex()) {
                return 1;
            }
        }
        return pokers1[2] < pokers2[2] ? -1 : 1;
    }


    public static ZhajinHuaType getZhaJinHuaType(int[] pokers) {
        if (pokers == null || pokers.length != 3) {
            throw new RuntimeException("炸金花张数不是三张");
        }
        Map<Integer, Boolean> pokerMap = new HashMap<>();
        for (int poker : pokers) {
            pokerMap.put(poker, true);
        }
        if (pokerMap.size() != 3) {
            throw new RuntimeException("你在出千啊，怎么搞两张一样的");
        }
        Arrays.sort(pokers);

        int i0 = PokerIndex.getIndex(pokers[0]).getIndex();
        int i1 = PokerIndex.getIndex(pokers[1]).getIndex();
        int i2 = PokerIndex.getIndex(pokers[2]).getIndex();

        int c0 = PokerColor.getColor(pokers[0]).getIndex();
        int c1 = PokerColor.getColor(pokers[1]).getIndex();
        int c2 = PokerColor.getColor(pokers[2]).getIndex();

        // 三个头
        if(i0 == i1 && i0 == i2) {
            return ZhajinHuaType.SAN_GE_TOU;
        }

        boolean isShunZi = (i0 + 1 == i1 && i1 + 1 == i2) || (i0 == 0 && i1 == 1 && i2 == 12);
        boolean isJinHua = c0 == c1 && c0 == c2;

        // 顺青
        if(isShunZi && isJinHua) {
            return ZhajinHuaType.SHUN_QING;
        }

        // 金花
        if(isJinHua) {
            return ZhajinHuaType.JIN_HUA;
        }

        // 拖拉机
        if(isShunZi) {
            return ZhajinHuaType.TUO_LA_JI;
        }

        // 对子
        if(i0 == i1 || i0 == i2 || i1 == i2) {
            return ZhajinHuaType.DUI_ZI;
        }

        return ZhajinHuaType.DAN_ZHI_ZI;
    }
}
