/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2019.
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
 * 2019/1/29    wangjianwei         Create the class
 * http://www.jimilab.com/
 */


package com.tools.console.controller.api;

import com.jimi.together.base.basic.model.dto.ResultDTO;
import com.tools.common.basic.BaseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author wangjianwei
 * @version 1.0
 * @date 2019/1/29 15:15
 */
@RequestMapping("/activemq")
@Validated
@Api(tags = "activeMq模拟信息推送")
public interface ActivemqApi extends BaseApi {

    /**
     * 停止推送
     * @return RequestDTO
     * @author  wangjianwei
     * @date 2019/1/29 16:08
     */
    @RequestMapping(value = "stopPush", method = RequestMethod.POST)
    ResultDTO stopPush();

    /** 
     * 模拟activeMq 信息推送
     * @param msg
     * @param maxThreadNum
     * @param destinationName
     * @param theardSleepTime
     * @param loopNum
     * @return com.jimi.together.base.basic.model.dto.ResultDTO
     * @author  wangjianwei
     * @date 2019/1/29 15:37
     */ 
    @RequestMapping(value = "pushMsg", method = {RequestMethod.GET,RequestMethod.POST})
    ResultDTO pushMsg(@ApiParam(value = "mq信息必填", required = true) @NotEmpty(message = "mq信息非空") String msg,
                      @ApiParam(value = "最大线程数必填", required = true) @NotNull(message = "最大线程数不为空") Integer
                              maxThreadNum, @ApiParam(value = "推送指定名称", required = true) String destinationName,
                      @ApiParam(value = "线程睡眠时间", required = true) Long theardSleepTime, Integer loopNum);

    /**
     * 推送固定信息
     * @param maxThreadNum
     * @param destinationName
     * @param theardSleepTime
     * @param loopNum
     * @return
     */
    @RequestMapping(value = "pushFixedMsg", method = {RequestMethod.GET,RequestMethod.POST})
    ResultDTO pushFixedMsg(@ApiParam(value = "最大线程数必填", required = true) @NotNull(message = "最大线程数不为空") Integer
                              maxThreadNum, @ApiParam(value = "推送指定名称", required = true) String destinationName,
                      @ApiParam(value = "线程睡眠时间", required = true) Long theardSleepTime, Integer loopNum);

    /** 
     * 消费消息
     * @param destinationName 
     * @return com.jimi.together.base.basic.model.dto.ResultDTO 
     * @author  wangjianwei
     * @date 2019/1/30 16:40
     */ 
    @RequestMapping(value = "consumeMsg", method = RequestMethod.POST)
    ResultDTO consumeMsg(@ApiParam(value = "推送指定名称", required = true) String destinationName);

    /** 
     * 停止消费
     * @param destinationName
     * @return com.jimi.together.base.basic.model.dto.ResultDTO 
     * @author  wangjianwei
     * @date 2019/1/30 16:41
     */ 
    @RequestMapping(value = "shutDownConsumer", method = RequestMethod.POST)
    ResultDTO shutDownConsumer(@ApiParam(value = "推送指定名称", required = true) String destinationName);

    /**
     * 转换字符串为json格式
     * @param converString
     * @return
     */
    @RequestMapping(value = "converStringToJSON", method = RequestMethod.POST)
    ResultDTO converStringToJSON(@ApiParam(value = "解析json" , required = true) String converString,@ApiParam(value = "解析jsonKey" , required = true) String josnKey);
}
