package com.report.entity;

import java.util.Date;
import java.util.List;

public class Role {
    /**
    * 主键
    */
     private Integer id;
    /**
    * 用户名、登录名（即账号）
    */
     private String username;
    /**
    * 用户密码（加密之后）
    */
     private String password;
    /**
    * 用户昵称
    */
     private String nickname;
    /**
    * 用户电话，平台用户手机可以为空，但校验唯一
    */
     private String phone;
    /**
    * 用户邮箱
    */
     private String email;
    /**
    * 平台用户登录app端的头像地址
    */
     private String img;
    /**
    * 是否禁用，默认1. 1=启用;0=禁用
    */
     private Integer isDisabled;
    /**
    * 创建时间, 插入时更新, 不允许修改
    */
     private Date gmtCreate;
    /**
    * 修改时间, 根据当前时间戳更新
    */
     private Date gmtModified;
    /**
    * 创建人ID
    */
     private Integer createdBy;
    /**
    * 修改人ID
    */
     private Integer modifiedBy;
     public Integer getId(){
           return this.id;
     }
     public void setId(Integer id){
           this.id = id;
     }
     public String getUsername(){
           return this.username;
     }
     public void setUsername(String username){
           this.username = username;
     }
     public String getPassword(){
           return this.password;
     }
     public void setPassword(String password){
           this.password = password;
     }
     public String getNickname(){
           return this.nickname;
     }
     public void setNickname(String nickname){
           this.nickname = nickname;
     }
     public String getPhone(){
           return this.phone;
     }
     public void setPhone(String phone){
           this.phone = phone;
     }
     public String getEmail(){
           return this.email;
     }
     public void setEmail(String email){
           this.email = email;
     }
     public String getImg(){
           return this.img;
     }
     public void setImg(String img){
           this.img = img;
     }
     public Integer getIsDisabled(){
           return this.isDisabled;
     }
     public void setIsDisabled(Integer isDisabled){
           this.isDisabled = isDisabled;
     }
     public Date getGmtCreate(){
           return this.gmtCreate;
     }
     public void setGmtCreate(Date gmtCreate){
           this.gmtCreate = gmtCreate;
     }
     public Date getGmtModified(){
           return this.gmtModified;
     }
     public void setGmtModified(Date gmtModified){
           this.gmtModified = gmtModified;
     }
     public Integer getCreatedBy(){
           return this.createdBy;
     }
     public void setCreatedBy(Integer createdBy){
           this.createdBy = createdBy;
     }
     public Integer getModifiedBy(){
           return this.modifiedBy;
     }
     public void setModifiedBy(Integer modifiedBy){
           this.modifiedBy = modifiedBy;
     }

@Override
    public String toString() {
        return "Role{" +
            "  id:" + id + "  username:" + username + "  password:" + password + "  nickname:" + nickname + "  phone:" + phone + "  email:" + email + "  img:" + img + "  isDisabled:" + isDisabled + "  gmtCreate:" + gmtCreate + "  gmtModified:" + gmtModified + "  createdBy:" + createdBy + "  modifiedBy:" + modifiedBy + 
        "}";
    }
  }
