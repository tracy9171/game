package com.wx.app.game.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.game.Entity.RoleEntity;
import com.wx.app.game.dto.IdDto;
import com.wx.app.game.dto.RoleDto;
import com.wx.app.game.dto.RolePageDto;

/**
 * @ClassName RoleService
 * @Description 角色列表
 * @Author Administrator
 * @Date 2020-03-25 23:39
 * @Version 1.0
 */
public interface RoleService extends IService<RoleEntity> {

    /**
     * 新增角色
     * @param dto
     * @return
     */
    R addRole(RoleDto dto);

    /**
     * 分页查询角色列表
     * @param dto
     * @return
     */
    R getRoleList(RolePageDto dto);

    /**
     * 根据角色ID查询角色信息
     * @param dto
     * @return
     */
    R selectOneByRoleId(RoleDto dto);
}
