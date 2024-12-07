package com.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

public class HttpClientUtils {

    private static PoolingHttpClientConnectionManager cm;

    public static void init() {
        cm = new PoolingHttpClientConnectionManager();
        // 同时链接多少个请求
        cm.setMaxTotal(20);
        cm.setDefaultMaxPerRoute(50);
    }

    public static CloseableHttpClient getHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(3000)
                .setConnectionRequestTimeout(30000).build();
        return HttpClients.custom().setConnectionManager(cm).
                setDefaultRequestConfig(requestConfig).build();
    }

    public static String Get(String domain, String urlPath, Map<String, String> params, Map<String, String> headers) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        String url = domain + urlPath + "?" + mapToQueryString(params);
        HttpGet httpGet = new HttpGet(url);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue());
            }
        }
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity!= null) {
            return EntityUtils.toString(entity);
        }
        return null;
    }

    public static String PostJson(String domain, String urlPath, Map<String, String> params, Map<String, String> headers) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        String url = domain + urlPath;
        HttpPost httpPost = new HttpPost(url);
        String postJsonParam = JsonUtils.toJson(params);
        StringEntity entity = new StringEntity(postJsonParam);
        httpPost.setEntity(entity);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        httpPost.setHeader("Content-Type", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity!= null) {
            String result = EntityUtils.toString(responseEntity);
            System.out.println("响应内容: " + result);
            return result;
        }
        return null;
    }

    private static String mapToQueryString(Map<String, String> params) {
        if (params == null) {
            return "";
        }
        StringBuilder returnStr = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            returnStr.append(key).append("=").append(value).append("&");
        }
        return returnStr.toString();
    }
}
