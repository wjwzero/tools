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


package com.tools.base.basic.model.dto;



import com.tools.base.basic.model.BaseResultDTO;
import com.tools.base.basic.model.BaseVO;
import com.tools.base.constant.BasicErrorCodeEnum;
import com.tools.base.exception.IErrorCode;

import java.util.Collection;
import java.util.List;

/**
 * web返回结果集
 *
 * @author zhangduanfeng
 * @version 1.0
 * @date 2018/11/26 20:15
 */
public final class PageResultDTO<T extends BaseVO> extends BaseResultDTO<Collection<T>> {
    private static final long serialVersionUID = -301351068199759789L;
    /**
     * 数据对象
     */
    @io.swagger.annotations.ApiModelProperty(required = true, value = "响应数据长度")
    private Long size;
    @io.swagger.annotations.ApiModelProperty(required = true, value = "页码")
    private Long page;
    @io.swagger.annotations.ApiModelProperty(required = true, value = "总页数")
    private Long totalPage;
    @io.swagger.annotations.ApiModelProperty(required = true, value = "数据总长度")
    private Long totalSize;

    public PageResultDTO() {
        super();
    }

    public PageResultDTO(Integer code, String message) {
        super(code, message);
    }

    public PageResultDTO(IErrorCode errorCode) {
        super(errorCode);
    }


    /**
     * 获取一个空的返回结果DTO
     *
     * @return com.jimi.together.base.basic.model.dto.ResultDTO<T>
     * @author zhangduanfeng
     * @date 2018/12/7 14:23
     */
    public static <T extends BaseVO> PageResultDTO<T> success() {
        return new PageResultDTO<>(BasicErrorCodeEnum.SUCCESS);
    }

    /**
     * 获取一个返回结果DTO
     *
     * @param errorCode {@link IErrorCode}
     * @return com.jimi.together.base.basic.model.dto.ResultDTO<T>
     * @author zhangduanfeng
     * @date 2018/12/7 14:23
     */
    public static <T extends BaseVO> PageResultDTO<T> of(IErrorCode errorCode) {
        return new PageResultDTO<>(errorCode);
    }

    /**
     * 获取一个返回结果DTO
     *
     * @param data 继承自{@link BaseVO}的数据集合
     * @return com.jimi.together.base.basic.model.dto.ResultDTO<T>
     * @author zhangduanfeng
     * @date 2018/12/7 14:24
     */
    public static <T extends BaseVO> PageResultDTO<T> of(List<T> data) {
        PageResultDTO<T> dto = new PageResultDTO<>();
        dto.setData(data);
        return dto;
    }

    /**
     * 设置ErrorCode
     *
     * @param errorCode {@link IErrorCode}
     * @return com.jimi.together.base.basic.model.dto.ResultDTO<T>
     * @author zhangduanfeng
     * @date 2018/12/7 14:24
     */
    public PageResultDTO<T> errorCode(IErrorCode errorCode) {
        if (errorCode == null) {
            return this;
        }
        setCode(errorCode.getCode());
        setMessage(errorCode.getMessage());
        return this;
    }

    /**
     * 设置code
     *
     * @param code 返回code
     * @return com.jimi.together.base.basic.model.dto.ResultDTO<T>
     * @author zhangduanfeng
     * @date 2018/12/7 14:24
     */
    public PageResultDTO<T> code(int code) {
        setCode(code);
        return this;
    }

    /**
     * 设置message
     *
     * @param message 返回消息
     * @return com.jimi.together.base.basic.model.dto.ResultDTO<T>
     * @author zhangduanfeng
     * @date 2018/12/7 14:25
     */
    public PageResultDTO<T> message(String message) {
        setMessage(message);
        return this;
    }

    public PageResultDTO<T> data(Collection<T> data) {
        setData(data);
        return this;
    }

    /**
     * 设置数据长度，页面查询数量
     *
     * @param size 页面查询数量
     * @return com.jimi.together.base.basic.model.dto.PageResultDTO<T>
     * @author zhangduanfeng
     * @date 2018/12/7 14:41
     */
    public PageResultDTO<T> size(long size) {
        setSize(size);
        return this;
    }

    /**
     * 设置页面
     *
     * @param page 设置页面
     * @return com.jimi.together.base.basic.model.dto.PageResultDTO<T>
     * @author zhangduanfeng
     * @date 2018/12/7 14:41
     */
    public PageResultDTO<T> page(long page) {
        setPage(page);
        return this;
    }

    /**
     * 设置总页面数
     *
     * @param totalPage 设置总页面数
     * @return com.jimi.together.base.basic.model.dto.PageResultDTO
     * @author zhangduanfeng
     * @date 2018/12/7 14:41
     */
    public PageResultDTO<T> totalPage(long totalPage) {
        setTotalPage(totalPage);
        return this;
    }

    /**
     * 设置数据总长度
     *
     * @param totalSize 数据总长度
     * @return com.jimi.together.base.basic.model.dto.PageResultDTO<T>
     * @author zhangduanfeng
     * @date 2018/12/7 14:42
     */
    public PageResultDTO<T> totalSize(long totalSize) {
        setTotalSize(totalSize);
        return this;
    }


    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

}
