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
 * 2018/11/26    zhangduanfeng         Create the class
 * http://www.jimilab.com/
 */


package com.tools.common.basic;

import com.github.pagehelper.PageInfo;

import com.tools.common.basic.model.BaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;

/**
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/11/26 20:03
 */

public abstract class AbstractController implements BaseApi {
   /* protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    *//**
     * Web成功结果
     *
     * @return com.jimi.together.base.basic.model.dto.ResultDTO
     * @author zhangduanfeng
     * @date 2018/12/10 18:45
     *//*
    protected static <T extends BaseVO> ResultDTO<T> success() {
        return ResultDTO.of(BasicErrorCodeEnum.SUCCESS);
    }

    *//**
     * Web成功结果
     *
     * @return com.jimi.together.base.basic.model.dto.ResultDTO<T>
     * @author zhangduanfeng
     * @date 2018/12/10 18:46
     *//*
    protected static <T extends BaseVO> ResultDTO<T> success(T data) {
        return ResultDTO.of(data).errorCode(BasicErrorCodeEnum.SUCCESS);
    }

    *//**
     * 集合查询结果
     *
     * @param data 集合查询结果
     * @return com.jimi.together.base.basic.model.dto.ListResultDTO<T>
     * @author zhangduanfeng
     * @date 2018/12/10 18:46
     *//*
    protected static <T extends BaseVO> ListResultDTO<T> success(List<T> data) {
        return ListResultDTO.of(data).errorCode(BasicErrorCodeEnum.SUCCESS);
    }


    *//**
     * Web分页成功结果
     *
     * @param pageInfo {@link PageInfo} Mybatis插件的分页结果
     * @return com.jimi.together.base.basic.model.dto.PageResultDTO<T>
     * @author zhangduanfeng
     * @date 2018/12/11 14:24
     *//*
    protected static <T extends BaseVO> PageResultDTO<T> success(PageInfo<T> pageInfo) {
        return PageResultDTO.of(pageInfo.getList())
                .errorCode(BasicErrorCodeEnum.SUCCESS)
                .page(pageInfo.getPageNum())
                .size(pageInfo.getSize())
                .totalPage(pageInfo.getPages())
                .totalSize(pageInfo.getTotal());
    }

    *//**
     * Web分页成功结果
     *
     * @param pageInfo {@link PageInfo} Mybatis插件的分页结果
     * @param tClass   如果分页数据是其它结果类型，传入VO类型可以将其结果转换为VO
     * @return com.jimi.together.base.basic.model.dto.PageResultDTO<T>
     * @author zhangduanfeng
     * @date 2018/12/11 14:24
     *//*
    *//*protected static <T extends BaseVO> PageResultDTO<T> success(PageInfo<?> pageInfo, Class<T> tClass) {
        return PageResultDTO.of(BeanUtils.copyList(pageInfo.getList(), tClass))
                .errorCode(BasicErrorCodeEnum.SUCCESS)
                .page(pageInfo.getPageNum())
                .size(pageInfo.getSize())
                .totalPage(pageInfo.getPages())
                .totalSize(pageInfo.getTotal());
    }*//*

    *//**
     * Web分页成功结果
     *
     * @param pageInfo {@link PageInfo} Mybatis插件的分页结果
     * @param function 如果分页数据是其它结果类型，可以利用function将数据进行转换，或者做一些额外的处理。
     * @return com.jimi.together.base.basic.model.dto.PageResultDTO<T>
     * @author zhangduanfeng
     * @date 2018/12/11 14:24
     *//*
    protected static <T extends BaseVO> PageResultDTO<T> success(PageInfo<?> pageInfo,
                                                                 Function<List<?>, List<T>> function) {
        List<T> results = function.apply(pageInfo.getList());
        return PageResultDTO.of(results)
                .errorCode(BasicErrorCodeEnum.SUCCESS)
                .page(pageInfo.getPageNum())
                .size(pageInfo.getSize())
                .totalPage(pageInfo.getPages())
                .totalSize(pageInfo.getTotal());
    }*/


}
