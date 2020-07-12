package com.wx.app.game.controller.pay;


import com.baomidou.mybatisplus.extension.api.R;
import com.wx.app.game.dto.pay.GameUserVo;
import com.wx.app.game.service.payService.GameUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 微信登陆
 */
@RequestMapping("/wx")
@RestController
public class LoginController {

    @Autowired
    private GameUserService gameUserServiceImpl;

    @ApiOperation(value = "用户登陆")
    @PostMapping("login")
    public R login(@Valid @RequestBody GameUserVo vo){
        return gameUserServiceImpl.userLogin(vo);
    }

}
