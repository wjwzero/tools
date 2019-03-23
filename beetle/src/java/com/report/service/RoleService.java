package com.report.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.report.entity.Role;
import com.report.mapper.RoleMapper;
import java.util.List;

@Service
public class RoleService {
    @Resource
    private RoleMapper roleMapper;

    public Role getById(Integer id){

        return roleMapper.getById(id);
    }

    public Role getByUsername(String username){
        return roleMapper.getByUsername(username);
    }

    public Role getByPhone(String phone){
        return roleMapper.getByPhone(phone);
    }


    public List<Role> getAll(){
        return roleMapper.getAll();
    }

    public Integer insert(Role role){
        return roleMapper.insert(role);
    }

    public Integer deleteById(Integer id){
        return roleMapper.deleteById(id);
    }

    public Integer update(Role role){
        return roleMapper.update(role);
    }

}
