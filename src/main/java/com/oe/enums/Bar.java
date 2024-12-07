package com.oe.enums;

public enum Bar {

    MINUTE_1("1m"),
    MINUTE_3("3m"),
    MINUTE_5("5m"),
    MINUTE_15("15m"),
    MINUTE_30("30m"),
    HOUR_1("1H"),
    HOUR_2("2H"),
    HOUR_4("4H"),
    HOUR_6("6H"),
    HOUR_12("m1"),
    DAY_1("m1"),
    WEEK_1("m1"),
    MONTH_1("m1"),
    UTC_HOUR_6("m1"),
    UTC_HOUR_12("m1"),
    UTC_DAY_1("m1"),
    UTC_WEEK_1("m1"),
    UTC_MONTH_1("m1");

    String barName;

    Bar(String barName) {
        this.barName = barName;
    }

}
