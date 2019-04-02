package com.tools.report.domain.bo;

import java.time.LocalDateTime;

import com.tools.base.basic.model.BaseBO;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class TodayWorkInfoBO extends BaseBO {
    /**
    * 主键
    */
     @NotBlank(message = "主键不能为空")
     private Long id;
    /**
    * 用户id，t_user_info的ID
    */
     @NotBlank(message = "用户id，t_user_info的ID不能为空")
     private Long userId;
    /**
    * 类型
    */
     @NotBlank(message = "类型不能为空")
     private String tags;
    /**
    * 明细
    */
     @NotBlank(message = "明细不能为空")
     private String detials;
    /**
    * 任务状态，默认0. 0=进行中;1=已暂停;2=已停止;4=已完成
    */
     @NotBlank(message = "任务状态，默认0. 0=进行中;1=已暂停;2=已停止;4=已完成不能为空")
     private Integer status;
    /**
    * 是否禁用，默认1. 1=启用;0=禁用
    */
     @NotBlank(message = "是否禁用，默认1. 1=启用;0=禁用不能为空")
     private Integer isDisabled;
    /**
    * 计划时间
    */
     @NotBlank(message = "计划时间不能为空")
     private LocalDateTime gmtPlan;
    /**
    * 计划变更时间
    */
     @NotBlank(message = "计划变更时间不能为空")
     private LocalDateTime gmtPlanChange;
    /**
    * 创建时间, 插入时更新, 不允许修改
    */
     @NotBlank(message = "创建时间, 插入时更新, 不允许修改不能为空")
     private LocalDateTime gmtCreate;
    /**
    * 修改时间, 根据当前时间戳更新
    */
     @NotBlank(message = "修改时间, 根据当前时间戳更新不能为空")
     private LocalDateTime gmtModified;
    /**
    * 创建人ID
    */
     @NotBlank(message = "创建人ID不能为空")
     private Integer createdBy;
    /**
    * 修改人ID
    */
     @NotBlank(message = "修改人ID不能为空")
     private Integer modifiedBy;
  }
