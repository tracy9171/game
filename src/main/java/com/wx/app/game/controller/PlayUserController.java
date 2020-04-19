package com.wx.app.game.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.api.R;
import com.wx.app.game.aspect.NeedCheck;
import com.wx.app.game.dto.PlayUserDto;
import com.wx.app.game.dto.PlayUserPageParamsDto;
import com.wx.app.game.service.PlayUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @ClassName PlayUserController
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-25 23:58
 * @Version 1.0
 */
@Api(description = "游戏玩家")
@CrossOrigin
@RestController
@RequestMapping("/wx/user")
public class PlayUserController {

    @Autowired
    private PlayUserService playUserServiceImpl;

    @NeedCheck(needCheck=true)
    @ApiOperation(value = "获取分页数据")
    @PostMapping("selectPlayUserList")
    public R selectPlayUserList(@RequestBody PlayUserPageParamsDto dto){
        return playUserServiceImpl.getPlayUserList(dto);
    }

    @NeedCheck(needCheck=true)
    @ApiOperation(value = "获取单条玩家信息")
    @PostMapping("selectOneByUserId")
    public R selectOneByUserId(@RequestBody PlayUserPageParamsDto dto){
        return playUserServiceImpl.selectOneByUserId(dto);
    }

    @ApiOperation(value = "新增玩家信息")
    @PostMapping("addPlayUserInfo")
    public R addPlayUserInfo(@Valid @RequestBody PlayUserDto dto){
        return playUserServiceImpl.addPlayUserInfo(dto);
    }

    @ApiOperation(value = "编辑玩家信息")
    @PostMapping("updatePlayUserInfo")
    public R updatePlayUserInfo(@Valid @RequestBody PlayUserDto dto){
        return playUserServiceImpl.updatePlayUserInfo(dto);
    }
}
