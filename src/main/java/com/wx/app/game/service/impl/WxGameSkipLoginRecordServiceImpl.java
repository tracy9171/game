package com.wx.app.game.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.game.Entity.WxGameSkipLoginRecordEntity;
import com.wx.app.game.mapper.WxGameSkipLoginRecordMapper;
import com.wx.app.game.service.WxGameSkipLoginRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ClassName WxGameLoginRecordServiceImpl
 * @Description 平台跳转过来的进入游戏人数
 * @Author Administrator
 * @Date 2020-03-29 13:27
 * @Version 1.0
 */
@Service
@Slf4j
public class WxGameSkipLoginRecordServiceImpl extends ServiceImpl<WxGameSkipLoginRecordMapper, WxGameSkipLoginRecordEntity> implements WxGameSkipLoginRecordService {

}
