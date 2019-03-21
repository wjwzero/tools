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
 * 2018/12/29    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.jimi.together.base.basic.model.BaseQuery;
import com.jimi.together.base.basic.model.BaseResultDTO;
import com.jimi.together.base.basic.model.BaseVO;
import com.jimi.together.base.basic.model.dto.ListResultDTO;
import com.jimi.together.base.basic.model.dto.NoDataResultDTO;
import com.jimi.together.base.basic.model.dto.PageResultDTO;
import com.jimi.together.base.basic.model.dto.ResultDTO;
import com.jimi.together.base.constant.BasicErrorCodeEnum;
import com.jimi.together.base.exception.ServiceException;
import com.jimi.together.base.util.CollectionUtils;
import com.jimi.together.base.util.JsonUtils;
import com.jimi.together.base.util.StringUtils;
import com.jimi.together.base.util.http.RequestParameterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

import javax.servlet.http.Part;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/12/29 18:04
 */
@SuppressWarnings("unchecked")
public class Requests {



    private static final Logger LOGGER = LoggerFactory.getLogger(Requests.class);
    /**
     * 在Test类启动时实现登录并传入access_token
     */
    private static String accessToken;
    /**
     * MockMvc调用类
     */
    private static MockMvc mvc;
    /**
     * builder类
     */
    private MockHttpServletRequestBuilder builder;

    /**
     * 私有构造函数
     */
    private Requests(MockHttpServletRequestBuilder builder) {
        this.builder = builder;
        authorization(Requests.accessToken);
        builder.contentType(MediaType.APPLICATION_JSON_UTF8);
    }

    /**
     * 传入accessToken
     */
    static void setAccessToken(String accessToken) {
        Requests.accessToken = accessToken;
    }

    /**
     * 设置 accessToken请求头
     *
     * @author zhangduanfeng
     * @date 2019/1/6 1:43
     */
    public void authorization(String accessToken) {
        builder.header("Authorization", "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON_UTF8);
    }

    /**
     * 传入MockMvc
     */
    static void setMockMvc(MockMvc mvc) {
        Requests.mvc = mvc;
    }

    /**
     * 上传文件，所有请求参数由该方法传入。
     *
     * @param urlTemplate URI
     * @param file        文件
     * @param parts       其它参数
     * @return com.jimi.together.Requests
     * @author zhangduanfeng
     * @date 2019/1/6 1:37
     */
    public static Requests upload(String urlTemplate, MockMultipartFile file, Part... parts) {
        if(null != parts && parts.length > 0){
            return new Requests(MockMvcRequestBuilders.multipart(urlTemplate).file(file).part(parts));
        }else{
            return new Requests(MockMvcRequestBuilders.multipart(urlTemplate).file(file));
        }
    }

    /**
     * 上传多个文件，所有请求参数由该方法传入。
     *
     * @param urlTemplate URI
     * @param files       文件
     * @param parts       其它参数
     * @return com.jimi.together.Requests
     * @author zhangduanfeng
     * @date 2019/1/6 1:37
     */
    public static Requests upload(String urlTemplate, List<MockMultipartFile> files, Part... parts) {
        MockMultipartHttpServletRequestBuilder multipartBuilder = MockMvcRequestBuilders.multipart(urlTemplate);
        if (CollectionUtils.isNotEmpty(files)) {
            files.forEach(multipartBuilder::file);
        }
        if(null != parts && parts.length > 0){
            return new Requests(multipartBuilder.part(parts));
        }else{
            return new Requests(multipartBuilder);
        }
    }

    /**
     * 简单的get请求
     *
     * @param urlTemplate URI
     * @return com.jimi.together.Requests
     * @author zhangduanfeng
     * @date 2019/1/6 1:41
     */
    public static Requests get(String urlTemplate) {
        return new Requests(MockMvcRequestBuilders.get(urlTemplate));
    }

    /**
     * 以{@link BaseQuery}作为参数的get请求
     *
     * @param urlTemplate URI
     * @param query       {@link BaseQuery}
     * @return com.jimi.together.Requests
     * @author zhangduanfeng
     * @date 2019/1/6 1:41
     */
    public static Requests get(String urlTemplate, BaseQuery query) {
        return new Requests(MockMvcRequestBuilders.get(urlTemplate)).params(query);
    }

    /**
     * 以{@link MultiValueMap}作为参数的get请求
     *
     * @param urlTemplate URI
     * @param params      {@link MultiValueMap}
     * @return com.jimi.together.Requests
     * @author zhangduanfeng
     * @date 2019/1/6 1:42
     */
    public static Requests get(String urlTemplate, MultiValueMap<String, String> params) {
        return new Requests(MockMvcRequestBuilders.get(urlTemplate).params(params));
    }

    /**
     * post请求
     *
     * @param urlTemplate URI
     * @return com.jimi.together.Requests
     * @author zhangduanfeng
     * @date 2019/1/6 1:42
     */
    public static Requests post(String urlTemplate) {
        return new Requests(MockMvcRequestBuilders.post(urlTemplate));
    }

    /**
     * 以{@link MultiValueMap}作为参数的post请求
     *
     * @param urlTemplate URI
     * @param params      {@link MultiValueMap}
     * @return com.jimi.together.Requests
     * @author zhangduanfeng
     * @date 2019/1/6 1:42
     */
    public static Requests post(String urlTemplate, MultiValueMap<String, String> params) {
        return new Requests(MockMvcRequestBuilders.post(urlTemplate).params(params));
    }

    /**
     * 以{@link BaseQuery}作为参数的post请求
     *
     * @param urlTemplate URI
     * @param query       {@link BaseQuery}
     * @return com.jimi.together.Requests
     * @author zhangduanfeng
     * @date 2019/1/6 1:42
     */
    public static Requests post(String urlTemplate, BaseQuery query) {
        return new Requests(MockMvcRequestBuilders.post(urlTemplate)).params(query);
    }

    /**
     * 以{@link BaseQuery}作为参数的post请求，请求内容会被放置在请求body中
     *
     * @param urlTemplate URI
     * @param query       {@link BaseQuery}，将该参数序列化为JSON后放置在请求body中
     * @return com.jimi.together.Requests
     * @author zhangduanfeng
     * @date 2019/1/10 10:09
     */
    public static Requests content(String urlTemplate, BaseQuery query) {
        return new Requests(MockMvcRequestBuilders.post(urlTemplate)).content(query);
    }

    /**
     * 以{@link BaseQuery}作为参数的post请求，请求内容会被放置在请求body中
     *
     * @param query {@link BaseQuery}，将该参数序列化为JSON后放置在请求body中
     * @return com.jimi.together.Requests
     * @author zhangduanfeng
     * @date 2019/1/10 10:11
     */
    public Requests content(BaseQuery query) {
        builder.content(JsonUtils.toJsonString(query));
        return this;
    }

    /**
     * 添加单个请求头
     *
     * @param name  请求头名称
     * @param value 请求头值
     * @return com.jimi.together.Requests
     * @author zhangduanfeng
     * @date 2019/1/6 1:44
     */
    public Requests header(String name, String value) {
        builder.header(name, value);
        return this;
    }

    /**
     * 添加多个请求头
     *
     * @param headers 请求头Map字典
     * @return com.jimi.together.Requests
     * @author zhangduanfeng
     * @date 2019/1/6 1:45
     */
    public Requests headers(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        builder.headers(httpHeaders);
        return this;
    }

    /**
     * 设置单个参数，非空校验
     *
     * @param name  参数名
     * @param value 参数值
     * @return com.jimi.together.Requests
     * @author zhangduanfeng
     * @date 2019/1/6 1:45
     */
    public Requests param(String name, @NotNull String value) {
        if (name == null) {
            return this;
        }
        builder.param(name, value);
        return this;
    }

    /**
     * 以{@link BaseQuery}作为请求参数
     *
     * @param query {@link BaseQuery}
     * @return com.jimi.together.Requests
     * @author zhangduanfeng
     * @date 2019/1/6 1:46
     */
    public Requests params(BaseQuery query) {
        if (query == null) {
            return this;
        }
        Map<String, String> map = RequestParameterUtils.parse(query);
        map.forEach((key, value) -> {
            String keyStr = String.valueOf(key);
            if (StringUtils.isEmpty(keyStr)) {
                return;
            }
            String valueStr;
            if (value != null) {
                valueStr = String.valueOf(value);
                builder.param(keyStr, valueStr);
            }
        });

        return this;
    }

    /**
     * 以{@link MultiValueMap}作为请求参数
     *
     * @param params {@link MultiValueMap}
     * @return com.jimi.together.Requests
     * @author zhangduanfeng
     * @date 2019/1/6 1:46
     */
    public Requests params(MultiValueMap<String, String> params) {
        if (params == null) {
            return this;
        }
        builder.params(params);
        return this;
    }

    /**
     * 返回 {@link MvcResult}结果
     *
     * @return org.springframework.test.web.servlet.MvcResult
     * @author zhangduanfeng
     * @date 2019/1/6 1:47
     */
    public MvcResult mvcResult() {
        return execute();
    }

    /**
     * 返回纯字符串结果
     *
     * @return java.lang.String
     * @author zhangduanfeng
     * @date 2019/1/6 1:47
     */
    public String stringResult() {
        try {
            return execute().getResponse().getContentAsString();
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 将结果中的data封装为某个对象。
     *
     * @param tClass 对象类
     * @return java.util.Optional<T>
     * @author zhangduanfeng
     * @date 2019/1/6 1:47
     */
    public <T> Optional<T> result(Class<T> tClass) {
        MvcResult mvcResult = execute();
        if (tClass == null || tClass == MvcResult.class) {
            return Optional.of((T) mvcResult);
        }

        JsonNode jsonNode = readJsonNode(mvcResult);
        if (Objects.isNull(jsonNode) || !jsonNode.hasNonNull("data")) {
            return Optional.empty();
        }

        return Optional.ofNullable(JsonUtils.getJacksonMapper().convertValue(jsonNode.get("data"), tClass));
    }

    /**
     * 将结果中的data封装为某个复杂对象（多泛型）。
     *
     * @param typeReference {@link TypeReference}
     * @return java.util.Optional<T>
     * @author zhangduanfeng
     * @date 2019/1/6 1:48
     */
    public <T> Optional<T> result(TypeReference<T> typeReference) {
        MvcResult mvcResult = execute();

        JsonNode jsonNode = readJsonNode(mvcResult);
        if (Objects.isNull(jsonNode) || !jsonNode.hasNonNull("data")) {
            return Optional.empty();
        }
        return Optional.ofNullable(JsonUtils.getJacksonMapper().convertValue(jsonNode.get("data"), typeReference));
    }

    /**
     * 获取完整返回结果，含ResultDTO相关
     *
     * @param typeReference 继承自BaseResultDTO的泛型。 ResultDTO或ListResultDTO或PageResultDTO等等
     * @return T
     * @author zhangduanfeng
     * @date 2019/1/6 1:59
     */
    public <T extends BaseResultDTO<?>> T fullResult(TypeReference<T> typeReference) {
        MvcResult mvcResult = execute();
        String content = "";
        try {
            content = mvcResult.getResponse().getContentAsString();
            return JsonUtils.getJacksonMapper().readValue(content, typeReference);
        } catch (IOException e) {
            try {
                T t = (T) typeReference.getType().getClass().newInstance();
                return t;
            } catch (InstantiationException | IllegalAccessException e1) {
                e1.printStackTrace();
            }
        }
        throw new ServiceException(BasicErrorCodeEnum.FAILED, content);
    }

    public <T extends BaseVO> ResultDTO<T> voResult(TypeReference<ResultDTO<T>> typeReference) {
        MvcResult mvcResult = execute();
        try {
            String content = mvcResult.getResponse().getContentAsString();
            return JsonUtils.getJacksonMapper().readValue(content, typeReference);
        } catch (IOException e) {
            ResultDTO t = new ResultDTO();
            t.errorCode(BasicErrorCodeEnum.FAILED);
            return t;
        }
    }

    public NoDataResultDTO noDataResult() {
        MvcResult mvcResult = execute();
        try {
            String content = mvcResult.getResponse().getContentAsString();
            return JsonUtils.getJacksonMapper().readValue(content, NoDataResultDTO.class);
        } catch (IOException e) {
            NoDataResultDTO t = new NoDataResultDTO();
            t.errorCode(BasicErrorCodeEnum.FAILED);
            return t;
        }
    }

    public <T extends BaseVO> PageResultDTO<T> pageResult(TypeReference<PageResultDTO<T>> typeReference) {
        MvcResult mvcResult = execute();
        try {
            String content = mvcResult.getResponse().getContentAsString();
            return JsonUtils.getJacksonMapper().readValue(content, typeReference);
        } catch (IOException e) {
            PageResultDTO t = new PageResultDTO();
            t.errorCode(BasicErrorCodeEnum.FAILED);
            return t;
        }
    }

    public <T extends BaseVO> ListResultDTO<T> listResult(TypeReference<ListResultDTO<T>> typeReference) {
        MvcResult mvcResult = execute();
        try {
            String content = mvcResult.getResponse().getContentAsString();
            return JsonUtils.getJacksonMapper().readValue(content, typeReference);
        } catch (IOException e) {
            ListResultDTO t = new ListResultDTO();
            t.errorCode(BasicErrorCodeEnum.FAILED);
            return t;
        }
    }

//    public StringResultDTO stringResult() {
//        MvcResult mvcResult = httpInvoke();
//        try {
//            String content = mvcResult.getResponse().getContentAsString();
//            return JsonUtils.getJacksonMapper().readValue(content, new TypeReference<StringResultDTO>() {
//            });
//        } catch (IOException e) {
//            StringResultDTO t = new StringResultDTO();
//            t.errorCode(BasicErrorCodeEnum.FAILED);
//            return t;
//        }
//    }


    /**
     * 私有方法，将结果转换为{@link JsonNode}
     *
     * @param mvcResult 请求结果
     * @return com.fasterxml.jackson.databind.JsonNode
     * @author zhangduanfeng
     * @date 2019/1/6 1:48
     */
    private JsonNode readJsonNode(MvcResult mvcResult) {
        try {
            return JsonUtils.getJacksonMapper().readTree(mvcResult.getResponse().getContentAsByteArray());
        } catch (IOException ignored) {
        }
        return null;
    }

    /**
     * 私有方法，执行并获取结果{@link MvcResult}
     *
     * @return org.springframework.test.web.servlet.MvcResult
     * @author zhangduanfeng
     * @date 2019/1/6 1:49
     */
    public MvcResult execute() {
        MvcResult mvcResult;
        String content;
        long time;
        try {
            long start = System.currentTimeMillis();
            mvcResult = mvc.perform(builder).andReturn();
            long end = System.currentTimeMillis();
            time = end - start;
            content = mvcResult.getResponse().getContentAsString();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        Map<String, String[]> paramMap = mvcResult.getRequest().getParameterMap();
        Map<String, String> params = new LinkedHashMap<>(paramMap.size());
        paramMap.forEach((k, v) -> params.put(k, CollectionUtils.join(v, ",")));

        String body = "{}";
        try {
            body = mvcResult.getRequest().getContentAsString();
        } catch (UnsupportedEncodingException ignored) {
        }

        try {
            LOGGER.info("\nRequest.uri: {} \nRequest.Params: \n{} \nRequest.Body: \n{} \n耗时: {}毫秒 \nResponse.Content:" +
                            " \n{}",
                    mvcResult.getRequest().getRequestURI(),
                    JsonUtils.getJacksonMapper().writerWithDefaultPrettyPrinter()
                            .writeValueAsString(params),
                    JsonUtils.getJacksonMapper().writerWithDefaultPrettyPrinter()
                            .writeValueAsString(JsonUtils.json2Map(body))
                    , time,
                    JsonUtils.getJacksonMapper().writerWithDefaultPrettyPrinter()
                            .writeValueAsString(JsonUtils.getJacksonMapper()
                                    .readTree(content)));
        } catch (IOException ignored) {
        }

        return mvcResult;
    }
}
