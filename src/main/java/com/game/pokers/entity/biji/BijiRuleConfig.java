package com.game.pokers.entity.biji;

import com.game.pokers.constant.biji.BijiXipai;

import java.util.List;
import java.util.Map;

/**
 * 比鸡游戏规则配置
 */
public class BijiRuleConfig {

    /**
     * 喜牌是否叠加
     */
    private Boolean isXiPaiAddition;

    /**
     * 喜牌分数
     */
    private Map<BijiXipai, Integer> xiPaiPointMap;

    /**
     * 最多投降人数
     */
    private Integer surrenderNum;

    /**
     * 比牌分数，0-> 第二大输多少分，1-> 第三大输多少分，以此类推
     */
    private List<Integer> comparePoints;

    /**
     * 顺位投降罚钱
     */
    private List<Integer> surrenderPoints;

    public Boolean getXiPaiAddition() {
        return isXiPaiAddition;
    }

    public void setXiPaiAddition(Boolean xiPaiAddition) {
        isXiPaiAddition = xiPaiAddition;
    }

    public Map<BijiXipai, Integer> getXiPaiPointMap() {
        return xiPaiPointMap;
    }

    public void setXiPaiPointMap(Map<BijiXipai, Integer> xiPaiPointMap) {
        this.xiPaiPointMap = xiPaiPointMap;
    }

    public Integer getSurrenderNum() {
        return surrenderNum;
    }

    public void setSurrenderNum(Integer surrenderNum) {
        this.surrenderNum = surrenderNum;
    }

    public List<Integer> getSurrenderPoints() {
        return surrenderPoints;
    }

    public void setSurrenderPoints(List<Integer> surrenderPoints) {
        this.surrenderPoints = surrenderPoints;
    }

    public List<Integer> getComparePoints() {
        return comparePoints;
    }

    public void setComparePoints(List<Integer> comparePoints) {
        this.comparePoints = comparePoints;
    }
}
