/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2018.
 * ALL RIGHTS RESERVED.
 *
 * No part of this publication may be reproduced, stored in a retrieval system, or transmitted,
 * on any form or by any means, electronic, mechanical, photocopying, recording,
 * or otherwise, without the prior written permission of ShenZhen JiMi Network Technology Co., Ltd.
 *
 * Amendment History:
 *
 * Date                   By              Description
 * -------------------    -----------     -------------------------------------------
 * 2018/10/19    yaojianping         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.util.http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaojianping
 * @version 1.0
 * @date 2018/10/19 11:31
 */
public class HttpClientUtil {

    private static final String UTF_8 = "UTF-8";

    private static int socketTimeout = 5000;
    private static int connectionTimeout = 3000;

    /**
     * 设置响应超时时间，毫秒值
     *
     * @return void
     * @author yaojianping
     * @date 2018/10/22 18:51
     */
    public static void setSocketTimeout(int socketTimeout) {
        HttpClientUtil.socketTimeout = socketTimeout;
    }

    /**
     * 设置连接超时时间，毫秒值
     *
     * @return void
     * @author yaojianping
     * @date 2018/10/22 18:51
     */
    public static void setConnectionTimeout(int connectionTimeout) {
        HttpClientUtil.connectionTimeout = connectionTimeout;
    }

    /**
     * 获取http请求默认参数
     *
     * @return org.apache.http.client.config.RequestConfig
     * @author yaojianping
     * @date 2018/10/22 17:32
     */
    private static RequestConfig getDefaultConfig() {
        return RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectionTimeout)
                .setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
    }

    /**
     * 发送http get请求
     *
     * @param url    请求url地址
     * @param params 请求参数
     * @return com.jimi.tuqiang.base.domain.ResultModel
     * @author yaojianping
     * @date 2018/10/22 20:18
     */
    public static ResultModel<String> sendGet(String url, Map<String, String> params) throws IOException {
        return sendGet(url, null, params);
    }

    /**
     * 发送http get请求
     *
     * @param url     请求url地址
     * @param headers 请求头
     * @param params  请求参数
     * @return com.jimi.tuqiang.base.domain.ResultModel
     * @author yaojianping
     * @date 2018/10/22 20:19
     */
    public static ResultModel<String> sendGet(String url, Map<String, String> headers, Map<String, String> params) throws IOException {
        final RequestConfig requestConfig = getDefaultConfig();
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        HttpEntity httpentity = null;
        try {
            url = url + (null == params ? "" : HttpUtil.assemblyParams(params));
            HttpGet get = new HttpGet(url);
            get.setHeaders(assemblyHeader(headers));
            get.setConfig(requestConfig);
            httpclient = HttpClients.createDefault();
            response = httpclient.execute(get);
            httpentity = response.getEntity();

            ResultModel<String> result = new ResultModel<>();
            result.setCode(response.getStatusLine().getStatusCode());
            result.setData(EntityUtils.toString(httpentity, UTF_8));
            return result;
        } finally {
            close(response);
            close(httpclient);
            EntityUtils.consume(httpentity);
        }
    }

    /**
     * 表单方式发送http post请求
     *
     * @param url    请求url地址
     * @param params 请求参数
     * @return com.jimi.tuqiang.base.domain.ResultModel
     * @author yaojianping
     * @date 2018/10/22 20:20
     */
    public static ResultModel<String> sendPost(String url, Map<String, String> params) throws IOException {
        return sendPost(url, null, params);
    }

    /**
     * 表单方式发送http post请求
     *
     * @param url     请求url地址
     * @param headers 请求头
     * @param params  请求参数
     * @return com.jimi.tuqiang.base.domain.ResultModel
     * @author yaojianping
     * @date 2018/10/22 20:21
     */
    private static ResultModel<String> sendPost(String url, Map<String, String> headers, Map<String, String> params) throws IOException {
        final RequestConfig requestConfig = getDefaultConfig();
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        HttpEntity httpentity = null;
        try {
            HttpPost post = new HttpPost(url);
            post.setHeaders(assemblyHeader(headers));
            post.setConfig(requestConfig);
            List<NameValuePair> paramsList = new ArrayList<>();
            for (String temp : params.keySet()) {
                paramsList.add(new BasicNameValuePair(temp, params.get(temp)));
            }
            post.setEntity(new UrlEncodedFormEntity(paramsList, UTF_8));
            httpclient = HttpClients.createDefault();
            response = httpclient.execute(post);
            httpentity = response.getEntity();

            ResultModel<String> result = new ResultModel<>();
            result.setCode(response.getStatusLine().getStatusCode());
            result.setData(EntityUtils.toString(httpentity, UTF_8));
            return result;
        } finally {
            close(response);
            close(httpclient);
            EntityUtils.consume(httpentity);
        }
    }

    /**
     * json方式发送http post请求
     *
     * @param url     请求url地址
     * @param jsonStr json格式参数
     * @return com.jimi.tuqiang.base.domain.ResultModel
     * @author yaojianping
     * @date 2018/10/23 9:42
     */
    public static ResultModel<String> sendPostJson(String url, String jsonStr) throws IOException {
        final RequestConfig requestConfig = getDefaultConfig();
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        HttpEntity httpentity = null;
        Map<String, String> headers = new HashMap<>(2);
        headers.put("Content-type", "application/json; charset=utf-8");
        headers.put("Accept", "application/json");
        try {
            HttpPost post = new HttpPost(url);
            post.setHeaders(assemblyHeader(headers));
            post.setConfig(requestConfig);
            post.setEntity(new StringEntity(jsonStr, UTF_8));
            httpclient = HttpClients.createDefault();
            response = httpclient.execute(post);
            httpentity = response.getEntity();

            ResultModel<String> result = new ResultModel<>();
            result.setCode(response.getStatusLine().getStatusCode());
            result.setData(EntityUtils.toString(httpentity, UTF_8));
            return result;
        } finally {
            close(response);
            close(httpclient);
            EntityUtils.consume(httpentity);
        }
    }

    /**
     * 组装头部信息
     *
     * @param headers
     * @return
     */
    public static Header[] assemblyHeader(Map<String, String> headers) {
        headers = HttpUtil.getDefaultHeader(headers);

        Header[] allHeader = new BasicHeader[headers.size()];
        int i = 0;
        for (String str : headers.keySet()) {
            allHeader[i] = new BasicHeader(str, headers.get(str));
            i++;
        }
        return allHeader;
    }

    /**
     * 关闭流
     *
     * @param obj 需关闭的对象
     * @return void
     * @author yaojianping
     * @date 2018/10/23 9:42
     */
    private static void close(Closeable obj) {
        if (null != obj) {
            try {
                obj.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
