package com.wx.app.game.controller;

import com.baomidou.mybatisplus.extension.api.R;

import com.wx.app.game.aspect.NeedCheck;
import com.wx.app.game.dto.BoxPageParamsDto;
import com.wx.app.game.dto.IdDto;
import com.wx.app.game.service.BoxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @ClassName BoxController
 * @Description 盒子管理
 * @Author Administrator
 * @Date 2020-03-24 20:46
 * @Version 1.0
 */
@Api(description = "盒子管理")
@CrossOrigin
@RestController
@RequestMapping("/wx/box")
public class BoxController {

    @Autowired
    private BoxService boxServiceImpl;


    @NeedCheck(needCheck=true)
    @ApiOperation(value = "获取单条盒子信息")
    @PostMapping("/getOne")
    public R getOne(@RequestBody IdDto dto){
        return boxServiceImpl.getOne(dto);
    }

    @NeedCheck(needCheck=true)
    @ApiOperation(value = "获取分页盒子信息")
    @PostMapping("/pageList")
    public R getBoxList(@RequestBody BoxPageParamsDto dto){
        return boxServiceImpl.getBoxList(dto);
    }

    @NeedCheck(needCheck=true)
    @ApiOperation(value = "获取盒子列表")
    @PostMapping("/allList")
    public R getBoxAllList(){
        return boxServiceImpl.getBoxAllList();
    }


}
