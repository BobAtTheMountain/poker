package com.game.pokers.constant;

import java.util.HashMap;
import java.util.Map;

public enum PokerIndex {
    p_2(0, "2"),
    p_3(1, "3"),
    p_4(2, "4"),
    p_5(3, "5"),
    p_6(4, "6"),
    p_7(5, "7"),
    p_8(6, "8"),
    p_9(7, "9"),
    p_10(8, "10"),
    p_J(9, "J"),
    p_Q(10, "Q"),
    p_K(11, "K"),
    p_A(12, "A"),
    ;
    private Integer index;

    private String view;

    PokerIndex(Integer index, String view) {
        this.index = index;
        this.view = view;
    }

    public Integer getIndex() {
        return this.index;
    }

    public String getView() {
        return this.view;
    }
    public static Map<Integer, PokerIndex> INDEX_MAP = new HashMap<>();

    static {
        System.out.println("初始化枚举类 Color");
        for(PokerIndex pokerColor : PokerIndex.values()) {
            INDEX_MAP.put(pokerColor.index, pokerColor);
        }
    }

    public static PokerIndex getIndex(Integer index) {
        return INDEX_MAP.get(index / 4);
    }
}
