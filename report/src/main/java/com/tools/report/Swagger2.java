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
 * 2018/12/6    yaojianping         Create the class
 * http://www.jimilab.com/
 */


package com.tools.report;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Value("${swagger.show}")
    private boolean isShow;

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("导出接口")
                .description("客户端RESTful web api，提供接口服务")
                .version("1.0.0")
                .contact(new Contact("XX官网", "http://www.xxx.com", ""))
                .build();
    }


    @Bean
    public Docket oauthApi() {
        // 公共参数
        ParameterBuilder clientAppAndSecretPar = new ParameterBuilder()
                .name("Authorization")
                .defaultValue("Basic Base64(${appKey}:${appSecret})")
                .description("服务器下发的AppKey(ClientId)和AppSecret(ClientSecret)的Base64编码。")
                .required(true)
                .parameterType("header")
                .modelRef(new ModelRef("string"));

        ParameterBuilder timePar = new ParameterBuilder()
                .name("timestamp")
                .description("时间戳")
                .required(false)
                .parameterType("header")
                .defaultValue(String.valueOf(Clock.systemUTC().millis()))
                .modelRef(new ModelRef("string"));



        List<Parameter> parList = new ArrayList<>();
        parList.add(clientAppAndSecretPar.build());
        parList.add(timePar.build());
        parList.add(new ParameterBuilder().name("grant_type").defaultValue("password")
                .parameterType("query").modelRef(new ModelRef("string")).required(true).build());
        parList.add(new ParameterBuilder().name("username")
                .parameterType("query").modelRef(new ModelRef("string")).required(true).build());
        parList.add(new ParameterBuilder().name("password")
                .parameterType("query").modelRef(new ModelRef("string")).required(true).build());


        return new Docket(DocumentationType.SWAGGER_2)
                .enable(isShow)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
//                .apis(RequestHandlerSelectors.basePackage("com.jimi.together.web.controller"))
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 以/oauth/开始的url不需要token参数
//                .paths(PathSelectors.regex("^(?=/oauth/).*$"))
                .paths(PathSelectors.ant("/oauth/token"))
                .build()
                .globalOperationParameters(parList).groupName("鉴权模块");
    }

    @Bean
    public Docket bussApi() {
        // 公共参数
        ParameterBuilder tokenPar = new ParameterBuilder()
                .name("Authorization")
                .defaultValue("Authorization: Bearer ${YourAccessToken}")
                .description("服务器下发的AccessToken")
                .required(true)
                .parameterType("header")
                .modelRef(new ModelRef("string"));

        ParameterBuilder contentTypePar = new ParameterBuilder()
                .name("Content-Type")
                .description("强制使用Content-Type: application/json;charset=UTF-8")
                .required(true)
                .parameterType("header")
                .defaultValue("application/json;charset=UTF-8")
                .modelRef(new ModelRef("string"));

        ParameterBuilder timePar = new ParameterBuilder()
                .name("timestamp")
                .description("时间戳")
                .required(false)
                .parameterType("header")
                .defaultValue(String.valueOf(Clock.systemUTC().millis()))
                .modelRef(new ModelRef("string"));



        List<Parameter> parList = new ArrayList<>();
        parList.add(tokenPar.build());
        parList.add(contentTypePar.build());
        parList.add(timePar.build());


        return new Docket(DocumentationType.SWAGGER_2)
                .enable(isShow)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.jimi.together.web.controller"))
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 非/oauth/开始的url需要token参数
                .paths(PathSelectors.regex("^(?!/oauth/)(?!/app/).*$"))
                .build()
                .globalOperationParameters(parList).groupName("业务模块");
    }

    @Bean
    public Docket appApi() {
        // 公共参数
        ParameterBuilder tokenPar = new ParameterBuilder()
                .name("Authorization")
                .defaultValue("Bearer ${YourAccessToken}")
                .description("访问令牌")
                .required(true)
                .parameterType("header")
                .modelRef(new ModelRef("string"));

        ParameterBuilder timePar = new ParameterBuilder()
                .name("timestamp")
                .description("时间戳")
                .required(false)
                .parameterType("header")
                .defaultValue(String.valueOf(Clock.systemUTC().millis()))
                .modelRef(new ModelRef("string"));

        List<Parameter> parList = new ArrayList<>();
        parList.add(tokenPar.build());
        parList.add(timePar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(isShow)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.jimi.together.web.controller"))
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 非/oauth/开始的url需要token参数
                .paths(PathSelectors.regex("^(?=/app/).*$"))
                .build()
                .globalOperationParameters(parList).groupName("APP模块");
    }

}
