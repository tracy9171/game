package com.wx.app.game.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.wx.app.game.aspect.NeedCheck;
import com.wx.app.game.dto.WxGameLoginRecordDto;
import com.wx.app.game.dto.WxGameSkipRecordDto;
import com.wx.app.game.service.WxGameLoginRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName WxGameLoginRecordController
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-29 16:41
 * @Version 1.0
 */
@Api(description = "用户登陆记录")
@CrossOrigin
@RestController
@RequestMapping("/wx/login")
public class WxGameLoginRecordController {

    @Autowired
    private WxGameLoginRecordService wxGameLoginRecordServiceImpl;

    //@NeedCheck(needCheck=true)
    @ApiOperation(value = "用户登陆记录")
    @PostMapping("/save")
    public R addWxLogin(@RequestBody WxGameLoginRecordDto dto){
        return wxGameLoginRecordServiceImpl.saveWxGameLoginRecord(dto);
    }
}
