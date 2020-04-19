package com.wx.app.game.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.wx.app.game.aspect.NeedCheck;
import com.wx.app.game.dto.WxReplacementOrderDto;
import com.wx.app.game.service.ReplacementOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ReplacementOrderController
 * @Description TODO
 * @Author Administrator
 * @Date 2020-04-01 0:40
 * @Version 1.0
 */
@Api(description = "补单列表")
@CrossOrigin
@RestController
@RequestMapping("/wx/replacement")
public class ReplacementOrderController {

    @Autowired
    private ReplacementOrderService replacementOrderServiceImpl;

    @NeedCheck(needCheck=true)
    @ApiOperation(value = "分页查询补单列表")
    @PostMapping("/pageList")
    public R getRepOrderList(@RequestBody WxReplacementOrderDto dto){
        return replacementOrderServiceImpl.getRepOrderList(dto);
    }

    @NeedCheck(needCheck=true)
    @ApiOperation(value = "新增补单列表")
    @PostMapping("/save")
    public R addRepOrder(@RequestBody WxReplacementOrderDto dto){
        return replacementOrderServiceImpl.addRepOrder(dto);
    }

    @NeedCheck(needCheck=true)
    @ApiOperation(value = "编辑补单列表")
    @PostMapping("/updateRepOrder")
    public R updateRepOrderStatusByOrderNo(@RequestBody WxReplacementOrderDto dto){
        return replacementOrderServiceImpl.updateRepOrderStatusByOrderNo(dto);
    }

    @NeedCheck(needCheck=true)
    @ApiOperation(value = "根据订单ID查询补单信息")
    @PostMapping("/getRepOrderByOrderNo")
    public R getRepOrderByOrderNo(@RequestBody WxReplacementOrderDto dto){
        return replacementOrderServiceImpl.getRepOrderByOrderNo(dto);
    }
}
