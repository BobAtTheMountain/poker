package com.oe.objects.models;

import com.oe.objects.db.OkxKlinesDataDO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

public class Kline {
    // 开始时间
    private Long timeStampMilli;
    // 开盘价格
    private Double openPrice;
    // 最高价格
    private Double highPrice;
    // 最低价格
    private Double lowPrice;
    // 收盘价格
    private Double closePrice;
    // 是否完结
    private Boolean confirm;
    // BAR
    private String bar;
    // instId
    private String instId;

    public OkxKlinesDataDO toDataDO() {
        OkxKlinesDataDO klineDataDO = new OkxKlinesDataDO();
        klineDataDO.setTimestampMilli(timeStampMilli);
        klineDataDO.setOpenPrice(openPrice);
        klineDataDO.setHighPrice(highPrice);
        klineDataDO.setLowPrice(lowPrice);
        klineDataDO.setClosePrice(closePrice);
        klineDataDO.setConfirm(confirm);
        klineDataDO.setBar(bar);
        klineDataDO.setInstId(instId);
        return klineDataDO;
    }

    public static Kline ParseFromList(List<String> data, String bar, String instId) {
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        if (data.size() < 6) {
            throw new RuntimeException("data 长度小于6");
        }
        Kline kline = new Kline();
        kline.bar = bar;
        kline.timeStampMilli = Long.parseLong(data.getFirst());
        kline.openPrice = Double.parseDouble(data.get(1));
        kline.highPrice = Double.parseDouble(data.get(2));
        kline.lowPrice = Double.parseDouble(data.get(3));
        kline.closePrice = Double.parseDouble(data.get(4));
        kline.confirm = Objects.equals(data.get(5), "1");
        kline.instId = instId;

        return kline;
    }

    public Long getTimeStampMilli() {
        return timeStampMilli;
    }

    public void setTimeStampMilli(Long timeStampMilli) {
        this.timeStampMilli = timeStampMilli;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Double highPrice) {
        this.highPrice = highPrice;
    }

    public Double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(Double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }
}
