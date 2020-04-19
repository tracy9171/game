package com.wx.app.game.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.wx.app.game.aspect.NeedCheck;
import com.wx.app.game.dto.IdDto;
import com.wx.app.game.dto.WxGameOrderDto;
import com.wx.app.game.service.GameOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName GameOrderController
 * @Description TODO
 * @Author Administrator
 * @Date 2020-04-01 0:05
 * @Version 1.0
 */
@Api(description = "游戏信息")
@CrossOrigin
@RestController
@RequestMapping("/wx/order")
public class GameOrderController {

    @Autowired
    private GameOrderService gameOrderServiceImpl;

    //@NeedCheck(needCheck=true)
    @ApiOperation(value = "分页查询游戏订单")
    @PostMapping("/pageList")
    public R getGameOrderList(@RequestBody WxGameOrderDto dto){
        return gameOrderServiceImpl.getGameOrderList(dto);
    }

    @NeedCheck(needCheck=true)
    @ApiOperation(value = "新增游戏订单")
    @PostMapping("/save")
    public R addGameOrder(@RequestBody WxGameOrderDto dto){
        return gameOrderServiceImpl.addGameOrder(dto);
    }

    @NeedCheck(needCheck=true)
    @ApiOperation(value = "更新订单状态")
    @PostMapping("/updateByOrderNo")
    public R updateOrderStatusByOrderNo(@RequestBody WxGameOrderDto dto){
        return gameOrderServiceImpl.updateOrderStatusByOrderNo(dto);
    }
}
