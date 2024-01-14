package com.game.pokers.constant.zhajinhua;

public enum ZhajinHuaType {
    DAN_ZHI_ZI(0, "单只子"),
    DUI_ZI(1, "对子"),
    TUO_LA_JI(2, "拖拉机"),
    JIN_HUA(3, "金花"),
    SHUN_QING(4, "顺清"),
    SAN_GE_TOU(5, "三个头"),
    ;
    final int index;
    final String desc;
    ZhajinHuaType(int index, String desc){
        this.index = index;
        this.desc = desc;
    }

    public int getIndex() {
        return index;
    }

    public String getDesc() {
        return desc;
    }
}
