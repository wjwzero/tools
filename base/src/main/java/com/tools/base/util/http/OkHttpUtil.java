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
 * 2018/10/25    yaojianping         Create the class
 * http://www.jimilab.com/
 */


package com.tools.base.util.http;

import okhttp3.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

/**
 * @author yaojianping
 * @version 1.0
 * @date 2018/10/25 11:31
 */
public class OkHttpUtil {

    public static final MediaType MEDIATYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static OkHttpClient client =
            new OkHttpClient().newBuilder().connectTimeout(3, TimeUnit.SECONDS).readTimeout(45, TimeUnit.SECONDS).writeTimeout(5, TimeUnit.SECONDS).build();

    /**
     * 发送http get请求
     *
     * @param url    请求url地址
     * @param params 请求参数
     * @return com.jimi.tuqiang.base.domain.ResultModel
     * @author yaojianping
     * @date 2018/10/29 11:50
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
     * @date 2018/10/29 11:50
     */
    public static ResultModel<String> sendGet(String url, Map<String, String> headers, Map<String, String> params) throws IOException {
        url = url + (null == params ? "" : HttpUtil.assemblyParams(params));
        Request.Builder builder = new Request.Builder();

        // 组装头信息
        assemblyHeader(builder, headers);

        Request request = builder.url(url).build();
        Response response = client.newCall(request).execute();

        ResultModel<String> result = new ResultModel<>();
        result.setCode(response.code());
        result.setData(response.body().string());
        return result;
    }

    /**
     * 表单方式发送http post请求
     *
     * @param url    请求url地址
     * @param params 请求参数
     * @return com.jimi.tuqiang.base.domain.ResultModel
     * @author yaojianping
     * @date 2018/10/29 11:50
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
     * @date 2018/10/29 11:51
     */
    public static ResultModel<String> sendPost(String url, Map<String, String> headers, Map<String, String> params) throws IOException {
        Request.Builder builder = new Request.Builder();
        // 组装头信息
        assemblyHeader(builder, headers);

        FormBody.Builder formBody = new FormBody.Builder();
        Iterator<Entry<String, String>> iter = params.entrySet().iterator();
        Entry<String, String> entry = null;
        while (iter.hasNext()) {
            entry = iter.next();
            formBody.add(entry.getKey(), entry.getValue());
        }

        Request request = builder.url(url).post(formBody.build()).build();
        Response response = client.newCall(request).execute();

        ResultModel<String> result = new ResultModel<>();
        result.setCode(response.code());
        result.setData(response.body().string());
        return result;
    }

    /**
     * json方式发送http post请求
     *
     * @param url     请求url地址
     * @param jsonStr json格式参数
     * @return com.jimi.tuqiang.base.domain.ResultModel
     * @author yaojianping
     * @date 2018/10/29 11:51
     */
    public static ResultModel<String> sendPostJson(String url, String jsonStr) throws IOException {
        Request.Builder builder = new Request.Builder();
        // 组装头信息
        assemblyHeader(builder, null);

        RequestBody requestBody = RequestBody.create(MEDIATYPE_JSON, jsonStr);

        Request request = builder.url(url).post(requestBody).build();
        Response response = client.newCall(request).execute();

        ResultModel<String> result = new ResultModel<>();
        result.setCode(response.code());
        result.setData(response.body().string());
        return result;
    }

    /**
     * 组装头部信息
     *
     * @param headers
     * @return
     */
    public static void assemblyHeader(Request.Builder builder, Map<String, String> headers) {
        headers = HttpUtil.getDefaultHeader(headers);

        Iterator<Entry<String, String>> iter = headers.entrySet().iterator();
        Entry<String, String> entry = null;
        while (iter.hasNext()) {
            entry = iter.next();
            builder.header(entry.getKey(), entry.getValue());
        }
    }
}
