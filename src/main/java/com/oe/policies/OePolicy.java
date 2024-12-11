package com.oe.policies;

import com.common.utils.DateUtils;
import com.oe.objects.db.OkxKlinesDataDO;
import com.oe.objects.models.DayPolicyResult;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class OePolicy {

    public static DayPolicyResult getPolicyV1(List<OkxKlinesDataDO> data) {
        int num = 1;
        String desc = "";
        DayPolicyResult result = new DayPolicyResult();
        if (CollectionUtils.isEmpty(data) || data.size() <= 1) {
            result.setDesc("查询不到额外数据,对不起,我不能做出决策,你就买一份吧;");
            result.setNum(1);
            if (!CollectionUtils.isEmpty(data)) {
                result.setInstId(data.getFirst().getInstId());
                if (data.getFirst().getConfirm()) {
                    result.setDate(DateUtils.formatByTimeStamp(data.getFirst().getTimestampMilli() + 24 * 2600 * 1000));
                }else {
                    result.setDate(DateUtils.formatByTimeStamp(System.currentTimeMillis()));
                }
            }else {
                result.setInstId("UNKNOWN");
                result.setDate(DateUtils.formatByTimeStamp(System.currentTimeMillis()));
            }
            return result;
        }

        List<Double> closePrices = new ArrayList<>();
        for (OkxKlinesDataDO dataDO : data) {
            closePrices.add(dataDO.getClosePrice());
        }

        int lastKDays = 90;
        double lastKDaysAvgPrice = -1;
        if (data.size()-1 > lastKDays) {
            double totalPrice = 0;
            for (int i=1; i<=lastKDays; i++) {
                totalPrice = totalPrice + closePrices.get(i);
            }
            lastKDaysAvgPrice = totalPrice / lastKDays;
        }

        if (data.getFirst().getOpenPrice() == 0) {
            result.setDesc("上一天的开始金额居然是0,我无法决策,对不起,你就买一份吧;");
            result.setNum(1);
            if (!CollectionUtils.isEmpty(data)) {
                result.setInstId(data.getFirst().getInstId());
            }
            return result;
        }
        double laskKAvgRate = 0;
        double laskRate = data.getFirst().getClosePrice() / data.getFirst().getOpenPrice() - 1;

        if (lastKDaysAvgPrice > 0) {
            laskKAvgRate = data.getFirst().getClosePrice() / lastKDaysAvgPrice - 1;
        }

        int lastMinus10Per = 10;
        int lastMinus6Per = 6;
        int lastMinus3Per = 2;

        if (laskRate < -0.1) {
            num += lastMinus10Per;
            desc += "当天跌超过10%,多买" + lastMinus10Per + "份;";
        }else if (laskRate < -0.06) {
            num += lastMinus6Per;
            desc += "当天跌超过6%,多买" + lastMinus6Per + "份;";
        } else if (laskRate < 0.03) {
            num += lastMinus3Per;
            desc += "当天跌超过3%,多买" + lastMinus3Per + "份;";
        }

        int lastKDayMinus60Per = 70;
        int lastKDayMinus50Per = 60;
        int lastKDayMinus40Per = 45;
        int lastKDayMinus30Per = 25;
        int lastKDayMinus20Per = 10;
        int lastKDayMinus10Per = 4;
        int lastKDayMinus5Per = 1;

        if (laskKAvgRate < -0.6) {
            num += lastKDayMinus60Per;
            desc += "过去" + lastKDays + "天跌超过60%,多买" + lastKDayMinus60Per + "份;";
        }else if (laskKAvgRate < -0.5) {
            num += lastKDayMinus50Per;
            desc += "过去" + lastKDays + "天跌超过50%,多买" + lastKDayMinus50Per + "份;";
        } else if (laskKAvgRate < -0.4) {
            num += lastKDayMinus40Per;
            desc += "过去" + lastKDays + "天跌超过40%,多买" + lastKDayMinus40Per + "份;";
        } else if (laskKAvgRate < -0.3) {
            num += lastKDayMinus30Per;
            desc += "过去" + lastKDays + "天跌超过30%,多买" + lastKDayMinus30Per + "份;";
        } else if (laskKAvgRate < -0.2) {
            num += lastKDayMinus20Per;
            desc += "过去" + lastKDays + "天跌超过20%,多买" + lastKDayMinus20Per + "份;";
        } else if (laskKAvgRate < -0.1) {
            num += lastKDayMinus10Per;
            desc += "过去" + lastKDays + "天跌超过10%,多买" + lastKDayMinus10Per + "份;";
        } else if (laskKAvgRate < -0.05) {
            num += lastKDayMinus5Per;
            desc += "过去" + lastKDays + "天跌超过5%,多买" + lastKDayMinus5Per + "份;";
        }

        result.setDesc(desc);
        result.setNum(num);
        result.setInstId(data.getFirst().getInstId());
        if (data.getFirst().getConfirm()) {
            result.setDate(DateUtils.formatByTimeStamp(data.getFirst().getTimestampMilli() + 24 * 2600 * 1000));
        }else {
            result.setDate(DateUtils.formatByTimeStamp(System.currentTimeMillis()));
        }
        return result;
    }

}
