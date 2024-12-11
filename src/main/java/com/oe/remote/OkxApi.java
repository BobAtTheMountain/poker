package com.oe.remote;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oe.contant.OeConstant;
import com.oe.objects.models.Kline;
import com.oe.objects.models.OkxHttpResult;
import com.oe.objects.params.GetKeyLineParam;

import com.common.utils.HttpClientUtils;
import com.common.utils.JsonUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class OkxApi {

    public static List<Kline> getKeyLines(GetKeyLineParam param) throws IOException {
        String result = HttpClientUtils.Get(OeConstant.OKX_DOMAIN_CN, OeConstant.OKX_API_INDEX_CANDLES, param.getParams(), null);
        if (result == null) {
            return new ArrayList<>();
        }
        Gson gson = new Gson();
        // 创建TypeToken来表示Box<String>类型
        Type boxType = new TypeToken<OkxHttpResult<List<List<String>>>>(){}.getType();
        OkxHttpResult<List<List<String>>> box = gson.fromJson(result, boxType);
        List<Kline> kLines = new ArrayList<>();
        if (CollectionUtils.isEmpty(box.getData())) {
            return kLines;
        }
        for (List<String> data : box.getData()) {
            Kline kline = Kline.ParseFromList(data, param.getBar(), param.getInstId());
            if (kline != null) {
                kLines.add(kline);
            }
        }
        return kLines;
    }

    public static void main(String[] args) throws IOException {
        GetKeyLineParam param = new GetKeyLineParam();
        param.setBar("1D");
        param.setInstId("BTC-USD");
        List<Kline> klines = getKeyLines(param) ;
        System.out.println(JsonUtils.toJson(klines));
    }
}
