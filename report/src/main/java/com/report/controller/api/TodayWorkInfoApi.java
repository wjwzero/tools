package com.report.controller.api;

import com.report.domain.vo.TodayWorkInfoVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.tools.common.basic.BaseApi;


@RestController
@RequestMapping("/todayWorkInfo")
@Api(tags = "work")
public interface TodayWorkInfoApi extends BaseApi{

    @ApiOperation(value = "根据id获得数据", tags = "work")
    @RequestMapping(value = "/getById",method = RequestMethod.GET)
    ResponseEntity getById(Long id);


    @ApiOperation(value = "用户id，t_user_info的ID", tags = "work")
    @RequestMapping(value = "/getByUserId",method = RequestMethod.GET)
    ResponseEntity getByUserId(Long userId);

    @ApiOperation(value = "类型", tags = "work")
    @RequestMapping(value = "/getByTags",method = RequestMethod.GET)
    ResponseEntity getByTags(String tags);


    @ApiOperation(value = "获得所有信息", tags = "work")
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    ResponseEntity getAll();


    @ApiOperation(value = "新增信息", tags = "work")
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    ResponseEntity insert(TodayWorkInfoVO todayWorkInfo);


    @ApiOperation(value = "根据ID删除信息", tags = "work")
    @RequestMapping(value = "/deleteById",method = RequestMethod.POST)
    ResponseEntity deleteById(Long id);

    @ApiOperation(value = "更新信息", tags = "work")
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    ResponseEntity update(TodayWorkInfoVO todayWorkInfo);

}
