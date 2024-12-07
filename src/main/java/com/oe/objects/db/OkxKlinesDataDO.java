package com.oe.objects.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("okx_klines_data")
public class OkxKlinesDataDO {
    @Id
    Long id;
    // 开始时间
    private Long timestampMilli;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTimestampMilli() {
        return timestampMilli;
    }

    public void setTimestampMilli(Long timestampMilli) {
        this.timestampMilli = timestampMilli;
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
