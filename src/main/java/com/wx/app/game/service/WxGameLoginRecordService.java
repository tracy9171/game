package com.wx.app.game.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.game.Entity.WxGameLoginRecordEntity;
import com.wx.app.game.dto.WxGameLoginRecordDto;

/**
 * @ClassName WxGameLoginRecordService
 * @Description 用户登陆记录
 * @Author Administrator
 * @Date 2020-03-29 13:26
 * @Version 1.0
 */
public interface WxGameLoginRecordService extends IService<WxGameLoginRecordEntity> {

    /**
     * 新增用户登陆记录
     * @param dto
     * @return
     */
    R saveWxGameLoginRecord(WxGameLoginRecordDto dto);
}
