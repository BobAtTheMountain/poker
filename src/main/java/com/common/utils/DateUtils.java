package com.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String formatByTimeStamp(long timestamp) {
        Date d = new Date(timestamp);
        return formatByTimeStamp(d);
    }

    public static String formatByTimeStamp(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }

}
