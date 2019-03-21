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
 * 2018/12/12    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */

package com.tools;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jimi.together.base.basic.model.BaseQuery;
import com.jimi.together.base.constant.BasicErrorCodeEnum;
import com.jimi.together.base.util.JsonUtils;
import com.jimi.together.base.util.StringUtils;
import com.jimi.together.base.util.crypto.Md5Util;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MultiValueMap;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * @author wanghe
 * @version 1.0
 * @Date 2019-1-2 14:10:03 用来用作app的测试
 */
@AutoConfigureMockMvc
public class AppMockMvcTest extends BaseTest {

    protected static final String phone = "13800000000";
    protected static String code = "";
    protected static final String newPwd = Md5Util.md5Hex("123456");
    protected static final String username = "pjcadmin";
    protected static final String phoneType = "HW";

    @Autowired
    protected MockMvc mvc;

    /**
     * OAuth2 token
     */
    protected static String access_token;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void before() throws Exception {
        if (access_token != null) {
            return;
        }
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post("/app/user/getPhoneCode").param("phone", phone)
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        code = JSONObject.parseObject(result.getResponse().getContentAsString()).getString("data");

        result = mvc
                .perform(MockMvcRequestBuilders.post("/oauth/token")
                        .header("Authorization",
                                "Basic OWQwMGM2ZTI4OWJjNDkyYzo1ZjQ1Mjc1OGEyMjY0YzhhYTVhYjNmNThmYWRhNGE5MQ==")
                        // 参数
                        .param("grant_type", "pin").param("phone", phone).param("code", code)
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        if (jsonNode.get("code").asInt() == BasicErrorCodeEnum.SUCCESS.getCode()) {
            access_token = jsonNode.get("data").get("access_token").asText();
            return;
        }
        throw new LoginException("Login Failed!!!");
    }

    private MockHttpServletRequestBuilder authorization(MockHttpServletRequestBuilder builder) {
        return builder.header("Authorization", "Bearer " + access_token).accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }

    public MockHttpServletRequestBuilder headers(MockHttpServletRequestBuilder builder, Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return builder.headers(httpHeaders);
    }

    public MockHttpServletRequestBuilder get(String urlTemplate) {
        return authorization(MockMvcRequestBuilders.get(urlTemplate));
    }

    public MockHttpServletRequestBuilder get(String urlTemplate, BaseQuery query) {
        return param(authorization(MockMvcRequestBuilders.get(urlTemplate)), query);
    }

    public MockHttpServletRequestBuilder get(String urlTemplate, MultiValueMap<String, String> params) {
        return authorization(MockMvcRequestBuilders.get(urlTemplate)).params(params);
    }

    public MockHttpServletRequestBuilder post(String urlTemplate) {
        return authorization(MockMvcRequestBuilders.post(urlTemplate));
    }

    public MockHttpServletRequestBuilder post(String urlTemplate, MultiValueMap<String, String> params) {
        return authorization(MockMvcRequestBuilders.post(urlTemplate)).params(params);
    }

    public MockHttpServletRequestBuilder post(String urlTemplate, BaseQuery query) {
        return param(authorization(MockMvcRequestBuilders.post(urlTemplate)), query);
    }

    /**
     * 将Query作为参数反射到builder中。
     *
     * @param builder
     * @param query
     * @return org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
     * @author zhangduanfeng
     * @date 2018/12/29 11:13
     */
    public MockHttpServletRequestBuilder param(MockHttpServletRequestBuilder builder, BaseQuery query) {
        if (query == null) {
            return builder;
        }
        Map<String, Object> map = JsonUtils.json2Map(JsonUtils.toJsonString(query));

        Objects.requireNonNull(map).forEach((key, value) -> {
            String keyStr = String.valueOf(key);
            if (StringUtils.isEmpty(keyStr)) {
                return;
            }
            String valueStr;
            if (value == null) {
                valueStr = null;
            } else {
                valueStr = String.valueOf(value);
            }
            builder.param(keyStr, valueStr);
        });
        return builder;
    }

    public MockHttpServletRequestBuilder delete(String urlTemplate) {
        return authorization(MockMvcRequestBuilders.delete(urlTemplate));
    }

    public MvcResult result(MockHttpServletRequestBuilder builder) {
        try {
            MvcResult mvcResult = mvc.perform(builder).andReturn();
            logRequest(mvcResult);
            return mvcResult;
        } catch (Exception e) {
            return null;
        }
    }

    public String stringResult(MockHttpServletRequestBuilder builder) {
        try {
            MvcResult mvcResult = mvc.perform(builder).andReturn();

            logRequest(mvcResult);
            return mvcResult.getResponse().getContentAsString();
        } catch (Exception e) {
            return "";
        }
    }

    @SuppressWarnings({"unchecked", "Duplicates"})
    public <T> T result(MockHttpServletRequestBuilder builder, Class<T> tClass) {
        MvcResult mvcResult = null;
        try {
            mvcResult = mvc.perform(builder).andReturn();
        } catch (Exception ignored) {
        }

        if (mvcResult == null) {
            return null;
        }

        if (tClass == null || tClass == MvcResult.class) {
            return (T) mvcResult;
        }

        try {
            logRequest(mvcResult);

            JsonNode jsonNode = objectMapper.readTree(mvcResult.getResponse().getContentAsByteArray());

            if (jsonNode.has("data")) {
                return objectMapper.convertValue(jsonNode.get("data"), tClass);
            }
        } catch (IOException ignored) {
        }
        return null;
    }

    @SuppressWarnings({"unchecked", "Duplicates"})
    public <T> T result(MockHttpServletRequestBuilder builder, TypeReference<T> typeReference) {
        MvcResult mvcResult = null;
        try {
            mvcResult = mvc.perform(builder).andReturn();
        } catch (Exception ignored) {
        }

        if (mvcResult == null) {
            return null;
        }

        try {
            logRequest(mvcResult);

            JsonNode jsonNode = objectMapper.readTree(mvcResult.getResponse().getContentAsByteArray());

            if (jsonNode.has("data")) {
                return objectMapper.convertValue(jsonNode.get("data"), typeReference);
            }

        } catch (IOException ignored) {

        }
        return null;
    }

    /**
     * 打印URL，参数和响应结果
     *
     * @param mvcResult
     * @return void
     * @author zhangduanfeng
     * @date 2018/12/29 11:10
     */
    private void logRequest(MvcResult mvcResult) throws IOException {
        Map<String, String[]> params = mvcResult.getRequest().getParameterMap();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String[]> entry : params.entrySet()) {
            stringBuilder.append(entry.getKey()).append("=").append(Arrays.toString(entry.getValue())).append("\n");
        }

        // logger.info("\nRequest.uri: {} \nRequest.Params: \n{} \nResponse.Content: \n{}",
        // mvcResult.getRequest().getRequestURI(), stringBuilder,
        // objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectMapper.readTree(mvcResult
        // .getResponse().getContentAsString())));
    }

    public MvcResult simpleHandel(String url, MultiValueMap params) throws Exception {
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.post(url).header("Authorization", "Bearer " + access_token)
                        // 参数
                        .params(params).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                // 状态码校验
                .andReturn();
        return result;
    }

    public MvcResult simpleGetHandel(String url, MultiValueMap params) throws Exception {
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get(url).header("Authorization", "Bearer " + access_token)
                        // 参数
                        .params(params).accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                // 状态码校验
                .andReturn();
        return result;
    }

}
