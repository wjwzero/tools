package com.report.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.report.domain.entity.TodayWorkInfoDO;
import com.report.mapper.TodayWorkInfoMapper;
import java.util.List;

@Service
public class TodayWorkInfoService {
    @Resource
    private TodayWorkInfoMapper todayWorkInfoMapper;

    public TodayWorkInfoDO getById(Long id){
        return todayWorkInfoMapper.getById(id);
    }

    public TodayWorkInfoDO getByUserId(Long userId){
        return todayWorkInfoMapper.getByUserId(userId);
    }

    public TodayWorkInfoDO getByTags(String tags){
        return todayWorkInfoMapper.getByTags(tags);
    }

    public List<TodayWorkInfoDO> getAll(){
        return todayWorkInfoMapper.getAll();
    }

    public Integer insert(TodayWorkInfoDO todayWorkInfo){
        return todayWorkInfoMapper.insert(todayWorkInfo);
    }

    public Integer deleteById(Long id){
        return todayWorkInfoMapper.deleteById(id);
    }

    public Integer update(TodayWorkInfoDO todayWorkInfo){
        return todayWorkInfoMapper.update(todayWorkInfo);
    }

}
