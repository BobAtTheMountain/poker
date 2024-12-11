package com.oe.contant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OeConstant {

    public static String OKX_DOMAIN = "https://www.okx.com";
    public static String OKX_DOMAIN_CN = "https://www.cnouyi.group";
    public static String OKX_API_INDEX_CANDLES = "/api/v5/market/index-candles";


    public static String BTC_USDT = "BTC-USDT";
    public static String ETH_USDT = "ETH-USDT";
    public static String SOL_USDT = "SOL-USDT";

    public static List<String> SupportedInstIds = new ArrayList<String>();

    static {
        SupportedInstIds.add(BTC_USDT);
        SupportedInstIds.add(ETH_USDT);
        SupportedInstIds.add(SOL_USDT);
    }


    public static Map<String, Boolean> KLineBarMap = new HashMap<>();

    static {
        KLineBarMap.put("1m", true);
        KLineBarMap.put("3m", true);
        KLineBarMap.put("5m", true);
        KLineBarMap.put("15m", true);
        KLineBarMap.put("30m", true);
        KLineBarMap.put("1H", true);
        KLineBarMap.put("2H", true);
        KLineBarMap.put("4H", true);
        KLineBarMap.put("6H", true);
        KLineBarMap.put("12H", true);
        KLineBarMap.put("1D", true);
        KLineBarMap.put("1W", true);
        KLineBarMap.put("1M", true);
        KLineBarMap.put("6Hutc", true);
        KLineBarMap.put("12Hutc", true);
        KLineBarMap.put("1Dutc", true);
        KLineBarMap.put("1Wutc", true);
        KLineBarMap.put("1Mutc", true);
    }

}
