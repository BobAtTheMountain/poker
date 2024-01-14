package com.game.pokers.constant.biji;

public enum BijiRoundStatus {
    /**
     * 等待开局
     */
    STARTING,
    /**
     * 等待所有人提交
     */
    SUBMITTING,
    /**
     * 算分
     */
    SETTLEMENT,
    /**
     * 结束
     */
    END,
    /**
     * 非法结束
     */
    ILLEGAL_END,
    ;
}
