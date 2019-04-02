package com.tools.report.mapper;

import com.tools.report.domain.entity.TodayWorkInfoDO;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface TodayWorkInfoMapper {

     TodayWorkInfoDO getById(Long Id);

     TodayWorkInfoDO getByUserId(Long userId);

     TodayWorkInfoDO getByTags(String tags);

     List<TodayWorkInfoDO> getAll();

     Integer insert(TodayWorkInfoDO todayWorkInfo);

     Integer deleteById(Long id);

     Integer update(TodayWorkInfoDO todayWorkInfo);


}
