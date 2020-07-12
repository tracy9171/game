package com.wx.app.game.service.payService;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.game.Entity.pay.GameUserEntity;
import com.wx.app.game.dto.pay.GameUserVo;

public interface GameUserService extends IService<GameUserEntity> {

    /**
     * 用户登陆
     * @param vo
     * @return
     */
    R userLogin(GameUserVo vo);
}
