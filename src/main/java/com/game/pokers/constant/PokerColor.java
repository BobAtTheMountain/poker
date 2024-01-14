package com.game.pokers.constant;

import java.util.HashMap;
import java.util.Map;

public enum PokerColor {
    Spade(0, "♠️"),
    Heart(1, "♥️"),
    Club(2, "♣️"),
    Diamond(3, "♦️️"),
    ;
    private Integer index;

    private String view;

    PokerColor(Integer index, String view) {
        this.index = index;
        this.view = view;
    }

    public Integer getIndex() {
        return this.index;
    }

    public String getView() {
        return this.view;
    }
    public static Map<Integer, PokerColor> COLOR_MAP = new HashMap<>();

    static {
        System.out.println("初始化枚举类 Color");
        for(PokerColor pokerColor : PokerColor.values()) {
            COLOR_MAP.put(pokerColor.index, pokerColor);
        }
    }

    public static PokerColor getColor(Integer index) {
        return COLOR_MAP.get(index%4);
    }
}
