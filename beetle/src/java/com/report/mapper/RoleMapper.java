package com.report.mapper;

import com.report.entity.Role;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface RoleMapper {
    String tableName = "role";

     @Select("SELECT id, username, password, nickname, phone, email, img, is_disabled, gmt_create, gmt_modified, created_by, modified_by FROM " + tableName + " WHERE id = #{id}")
     Role getById(Integer Id);


     @Select("SELECT id, username, password, nickname, phone, email, img, is_disabled, gmt_create, gmt_modified, created_by, modified_by FROM " + tableName + " WHERE username = #{username}")
     Role getByUsername(String username);


     @Select("SELECT id, username, password, nickname, phone, email, img, is_disabled, gmt_create, gmt_modified, created_by, modified_by FROM " + tableName + " WHERE phone = #{phone}")
     Role getByPhone(String phone);


     @Select("SELECT id, username, password, nickname, phone, email, img, is_disabled, gmt_create, gmt_modified, created_by, modified_by FROM " + tableName)
     List<Role> getAll();

     @Insert("INSERT INTO " + tableName + "(id, username, password, nickname, phone, email, img, is_disabled, gmt_create, gmt_modified, created_by, modified_by) VALUES (#{id}, #{username}, #{password}, #{nickname}, #{phone}, #{email}, #{img}, #{isDisabled}, #{gmtCreate}, #{gmtModified}, #{createdBy}, #{modifiedBy})")
     Integer insert(Role role);

     @Delete("DELETE FROM " + tableName + " WHERE id = #{id}")
     Integer deleteById(Integer id);
     @Update("UPDATE " + tableName + " SET username=#{username}, password=#{password}, nickname=#{nickname}, phone=#{phone}, email=#{email}, img=#{img}, is_disabled=#{isDisabled}, gmt_create=#{gmtCreate}, gmt_modified=#{gmtModified}, created_by=#{createdBy}, modified_by=#{modifiedBy} WHERE id = #{id}")
     Integer update(Role role);


}
