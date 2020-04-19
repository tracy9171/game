package com.wx.app.game.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wx.app.game.Entity.GameEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName GameMapper
 * @Description 游戏管理
 * @Author Administrator
 * @Date 2020-03-22 0:13
 * @Version 1.0
 */
@Mapper
public interface GameMapper extends BaseMapper<GameEntity> {
}
