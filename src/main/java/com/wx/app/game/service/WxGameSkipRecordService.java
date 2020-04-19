package com.wx.app.game.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.app.game.Entity.WxGameSkipRecordEntity;
import com.wx.app.game.dto.WxGameSkipRecordDto;

/**
 * @ClassName WxGameSkipRecordSerivce
 * @Description 用户跳转记录
 * @Author Administrator
 * @Date 2020-03-29 13:29
 * @Version 1.0
 */
public interface WxGameSkipRecordService extends IService<WxGameSkipRecordEntity> {
    /**
     * 新增跳转记录
     * @param dto
     * @return
     */
    R addWxGameSkip(WxGameSkipRecordDto dto);
}
