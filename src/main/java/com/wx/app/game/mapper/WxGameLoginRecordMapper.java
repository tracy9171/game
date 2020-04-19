package com.wx.app.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.app.game.Entity.WxGameLoginRecordEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName WxGameLoginRecordMapper
 * @Description 用户登陆记录
 * @Author Administrator
 * @Date 2020-03-29 13:25
 * @Version 1.0
 */
@Mapper
public interface WxGameLoginRecordMapper extends BaseMapper<WxGameLoginRecordEntity> {
}
