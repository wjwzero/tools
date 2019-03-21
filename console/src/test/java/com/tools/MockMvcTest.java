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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jimi.together.base.constant.BasicErrorCodeEnum;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URI;

/**
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/12/12 16:53
 */
@AutoConfigureMockMvc
public class MockMvcTest extends BaseTest {

    /**
     * OAuth2 token
     */
    protected static String access_token;
    @Autowired
    protected MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void before() throws Exception {
        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post("/oauth/token")
                        .header("Authorization", "Basic M2Y1ODhlYTgyZjZmNGNlYTo5ODc4NWUyNDBmMGE0ZmNjOWI2NWQ3NzM0ZmZm")
                        //参数
                        .param("grant_type", "password")
                        .param("username", "admin")
                        .param("password", "56da7f9dd5ea962de7830225ad4e1214")
                        //登录session
                        //.session((MockHttpSession)session)
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andReturn();
        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        logger.info("Login.Result: \n{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode));
        if (jsonNode.get("code").asInt() == BasicErrorCodeEnum.SUCCESS.getCode()) {
            access_token = jsonNode.get("data").get("access_token").asText();
            Requests.setAccessToken(access_token);
            Requests.setMockMvc(mvc);
            return;
        }
        throw new LoginException("Login Failed!!!");
    }

    private MockHttpServletRequestBuilder authorization(MockHttpServletRequestBuilder builder) {
        return builder.header("Authorization", "Bearer " + access_token)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
    }


    /**
     * API测试示例
     *
     * @param uriTemplate 测试资源地址
     * @param httpMethod  HTTP执行方法  GET、POST
     * @param params      入参
     * @param testMessage 测试信息
     * @return org.springframework.test.web.servlet.MvcResult
     * @author wangjianwei
     * @date 2018/12/29 16:36
     */
    public MvcResult apiTestExamples(String uriTemplate, HttpMethod httpMethod, MultiValueMap<String, String> params,
                                     String testMessage) {
        MvcResult mvcResult = null;
        try {
            // default构造访问权限饰词 反射强行构建
            Class clazz = MockHttpServletRequestBuilder.class;
            Constructor c1 = clazz.getDeclaredConstructor(new Class[]{HttpMethod.class, URI.class});
            c1.setAccessible(true);
            MockHttpServletRequestBuilder mockHttpServletRequestBuilder =
                    (MockHttpServletRequestBuilder) c1.newInstance(new Object[]{httpMethod, new URI(uriTemplate)});
            mockHttpServletRequestBuilder.params(params);
            mockHttpServletRequestBuilder.header("Content-Type","application/json");
            mvcResult = mvc.perform(authorization(mockHttpServletRequestBuilder)).andReturn();
            logResult(mvcResult, testMessage, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO 业务验证handle
        return mvcResult;
    }

    private void logResult(MvcResult mvcResult, String testMessage, MultiValueMap<String, String> params) throws IOException {
        logger.info("\n ========================{}========================= \n【示例地址】: {} \n【示例入参信息JSON】: \n{} " +
                        "\n【示例响应信息JSON】: \n{}", testMessage,
                mvcResult.getRequest().getRequestURI(),
                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(params.toSingleValueMap()),
                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectMapper.readTree(mvcResult.getResponse().getContentAsString())));
    }

    protected String writePrettyJsonString(Object obj) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}
