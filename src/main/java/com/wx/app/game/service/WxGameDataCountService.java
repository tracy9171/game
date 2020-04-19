package com.wx.app.game.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.game.Entity.WxGameDataCountEntity;
import com.wx.app.game.dto.WxGameDataDto;

public interface WxGameDataCountService extends IService<WxGameDataCountEntity> {

    WxGameDataCountEntity getOneByToDay();

    void updateDataById(WxGameDataCountEntity entity);




    int updateData(WxGameDataDto dto);

    int updateDataLeLock(WxGameDataDto dto);

    WxGameDataCountEntity selectByIdLock(Integer id);
}
