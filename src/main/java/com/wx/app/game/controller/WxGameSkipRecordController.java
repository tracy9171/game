package com.wx.app.game.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.wx.app.game.aspect.NeedCheck;
import com.wx.app.game.dto.WxGameSkipRecordDto;
import com.wx.app.game.service.WxGameSkipRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName WxGameSkipRecordController
 * @Description 平台跳转数记录
 * @Author Administrator
 * @Date 2020-03-29 16:27
 * @Version 1.0
 */
@Api(description = "平台跳转数记录")
@CrossOrigin
@RestController
@RequestMapping("/wx/skip")
public class WxGameSkipRecordController {

    @Autowired
    private WxGameSkipRecordService wxGameSkipRecordServiceImpl;

    @NeedCheck(needCheck=true)
    @ApiOperation(value = "平台跳转数记录")
    @PostMapping("/save")
    public R addWxGameSkip(@RequestBody WxGameSkipRecordDto dto){
        return wxGameSkipRecordServiceImpl.addWxGameSkip(dto);
    }
}
