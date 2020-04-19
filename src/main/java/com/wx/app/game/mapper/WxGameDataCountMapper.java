package com.wx.app.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.app.game.Entity.WxGameDataCountEntity;
import com.wx.app.game.dto.WxGameDataDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WxGameDataCountMapper extends BaseMapper<WxGameDataCountEntity> {

    int updateData(WxGameDataDto dto);
    int updateDataLeLock(WxGameDataDto dto);

    WxGameDataCountEntity selectByIdLock(Integer id);
}
