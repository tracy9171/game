package com.wx.app.game.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.wx.app.game.aspect.NeedCheck;
import com.wx.app.game.dto.GamePageParamsDto;
import com.wx.app.game.dto.IdDto;
import com.wx.app.game.service.GameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @ClassName GameController
 * @Description 游戏管理
 * @Author Administrator
 * @Date 2020-03-22 10:19
 * @Version 1.0
 */
@Api(description = "游戏信息")
@CrossOrigin
@RestController
@RequestMapping("/wx/game")
public class GameController {

    @Autowired
    private GameService gameServiceImpl;

    @NeedCheck(needCheck=true)
    @ApiOperation(value = "获取单条游戏信息")
    @PostMapping("/getOne")
    public R getOneGame(@RequestBody IdDto dto){
        return gameServiceImpl.getOneGame(dto);
    }

    @NeedCheck(needCheck=true)
    @ApiOperation(value = "分页获取游戏列表")
    @PostMapping("/pageList")
    public R getGameList(@RequestBody GamePageParamsDto dto){
        return gameServiceImpl.getGameList(dto);
    }

    @NeedCheck(needCheck=true)
    @ApiOperation(value = "根据父游戏ID查询关联游戏")
    @PostMapping("/childList")
    public R selectChildrenGame(@RequestBody GamePageParamsDto dto){
        return gameServiceImpl.selectChildrenGame(dto);
    }

    @NeedCheck(needCheck=true)
    @ApiOperation(value = "查询所有游戏列表")
    @PostMapping("/allList")
    public R allList(){
        return gameServiceImpl.allList();
    }

}
