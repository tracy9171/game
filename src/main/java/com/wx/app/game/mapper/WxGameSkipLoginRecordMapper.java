package com.wx.app.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.app.game.Entity.WxGameSkipLoginRecordEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName WxGameLoginRecordMapper
 * @Description 平台跳转过来的进入游戏人数
 * @Author Administrator
 * @Date 2020-03-29 13:25
 * @Version 1.0
 */
@Mapper
public interface WxGameSkipLoginRecordMapper extends BaseMapper<WxGameSkipLoginRecordEntity> {
}
