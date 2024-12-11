package com.oe.objects.models;

public class DayPolicyResult {

    private String instId;
    private String date;
    private Integer num;
    private String desc;

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String toPageDesc() {
        return instId + "(" + date + "), 建议购买" + num + "份, 原因: [" + desc + "]";
    }
}
