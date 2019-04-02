package com.report.controller;

import com.report.domain.entity.TodayWorkInfoDO;
import com.report.domain.vo.TodayWorkInfoVO;
import com.report.service.TodayWorkInfoService;
import com.report.controller.api.TodayWorkInfoApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.tools.common.basic.AbstractController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/todayWorkInfo")
public class TodayWorkInfoController extends AbstractController implements TodayWorkInfoApi{
    private Logger logger = LoggerFactory.getLogger(TodayWorkInfoController.class);

    @Resource
    private TodayWorkInfoService todayWorkInfoService;

    @Override
    @RequestMapping(value = "/getById",method = RequestMethod.GET)
    public ResponseEntity getById(Long id){
        TodayWorkInfoVO result = convert(todayWorkInfoService.getById(id),TodayWorkInfoVO.class);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
    }

    @Override
    @RequestMapping(value = "/getByUserId",method = RequestMethod.GET)
    public ResponseEntity getByUserId(Long userId){
        TodayWorkInfoVO result = convert(todayWorkInfoService.getByUserId(userId),TodayWorkInfoVO.class);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
    }
    @Override
    @RequestMapping(value = "/getByTags",method = RequestMethod.GET)
    public ResponseEntity getByTags(String tags){
        TodayWorkInfoVO result = convert(todayWorkInfoService.getByTags(tags),TodayWorkInfoVO.class);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
    }

    @Override
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public ResponseEntity getAll(){
        List<TodayWorkInfoDO> result = todayWorkInfoService.getAll();
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
    }

    @Override
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public ResponseEntity insert(TodayWorkInfoVO todayWorkInfo){
        TodayWorkInfoDO todayWorkInfoDO = convert(todayWorkInfo,TodayWorkInfoDO.class);
        Integer result = todayWorkInfoService.insert(todayWorkInfoDO);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
    }

    @Override
    @RequestMapping(value = "/deleteById",method = RequestMethod.DELETE)
    public ResponseEntity deleteById(Long id){
        Integer result = todayWorkInfoService.deleteById(id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
    }

    @Override
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public ResponseEntity update(TodayWorkInfoVO todayWorkInfo){
        TodayWorkInfoDO todayWorkInfoDO = convert(todayWorkInfo,TodayWorkInfoDO.class);
        Integer result = todayWorkInfoService.update(todayWorkInfoDO);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
    }

}
