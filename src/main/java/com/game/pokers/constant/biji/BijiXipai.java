package com.game.pokers.constant.biji;

public enum BijiXipai {
    QUAN_HEI("全黑", "全部花色都是黑色"),
    QUAN_HONG("全红", "全部花色都是红色"),
    TOUDAO_JINHUA("头道金花", "第一道是金花"),
    TOUDAO_SHUN_QING("头道顺清", "第一道是顺清"),
    TOUDAO_SANGETOU("头道三个头", "第一道是三个头"),
    ERDAO_SHUN_QING("二道顺清", "第二道是顺清"),
    ERDAO_SAN_GE_TOU("二道三个头", "第二道是三个头"),
    ZHA_DAN("炸弹", "9张牌仅有一个炸弹"),
    SHUANG_ZHA_DAN("双炸弹", "9张牌有两个炸弹"),
    SAN_TONG("三通", "三道都是你最大"),
    ;

    final String title;
    final String desc;
    BijiXipai(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
