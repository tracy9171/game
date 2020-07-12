package com.wx.app.game.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.wx.app.game.aspect.NeedCheck;
import com.wx.app.game.dto.IdDto;
import com.wx.app.game.dto.RoleDto;
import com.wx.app.game.dto.RolePageDto;
import com.wx.app.game.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ClassName RoleController
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-26 0:31
 * @Version 1.0
 */
@Api(description = "角色列表")
@CrossOrigin
@RestController
@RequestMapping("/wx/role")
public class RoleController {

    @Autowired
    private RoleService roleServiceImpl;

    //@NeedCheck(needCheck=true)
    @ApiOperation(value = "保存角色信息")
    @PostMapping("save")
    public R saveRole(@Valid @RequestBody RoleDto dto){
        return roleServiceImpl.addRole(dto);
    }

    //@NeedCheck(needCheck=true)
    @ApiOperation(value = "获取分页角色类表")
    @PostMapping("pageList")
    public R selectRoleList(@RequestBody RolePageDto dto) {
        return roleServiceImpl.getRoleList(dto);
    }

    //@NeedCheck(needCheck=true)
    @ApiOperation(value = "获取分页角色类表")
    @PostMapping("selectOneByRoleId")
    public R selectOneByRoleId(@RequestBody RoleDto dto) {
        return roleServiceImpl.selectOneByRoleId(dto);
    }

    //@ApiOperation(value = "更新角色信息")
    @PostMapping("update")
    public R update(@RequestBody RoleDto dto){
        return roleServiceImpl.updateRole(dto);
    }

    @ApiOperation(value = "更新角色信息")
    @PostMapping("getRoleByUserId")
    public R getRoleByUserId(@RequestBody RoleDto dto){
        return roleServiceImpl.getRoleByUserId(dto);
    }
}
