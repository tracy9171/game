package com.wx.app.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.game.Entity.GameEntity;
import com.wx.app.game.commom.ErrorCode;
import com.wx.app.game.dto.GamePageParamsDto;
import com.wx.app.game.dto.IdDto;
import com.wx.app.game.mapper.GameMapper;
import com.wx.app.game.service.GameService;
import com.wx.app.game.utils.InitPageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName GameServiceImpl
 * @Description 游戏管理
 * @Author Administrator
 * @Date 2020-03-22 0:15
 * @Version 1.0
 */
@Service
@Slf4j
public class GameServiceImpl extends ServiceImpl<GameMapper, GameEntity> implements GameService {


    /**
     * 获取单条游戏记录
     * @param dto
     * @return
     */
    @Override
    public R getOneGame(IdDto dto) {
        log.info("getOneGame_dto={}",dto);
        try {
            GameEntity entity = getById(dto.getId());
            if (entity != null)return R.ok(entity);
        } catch (Exception e) {
            log.error("getOneGame_error={}",e.getMessage(),e);
        }
        return R.failed(new ErrorCode("无数据"));
    }

    /**
     * 分页查询游戏列表
     * @param dto
     * @return
     */
    @Override
    public R getGameList(GamePageParamsDto dto) {
        log.info("getGameList_dto={}",dto);
        try {
            Page page = InitPageUtils.getPage(dto.getLimit(), dto.getPage());
            QueryWrapper queryWrapper = new QueryWrapper<GameEntity>();
            queryWrapper.like(!StringUtils.isEmpty(dto.getGameName()),"game_name",dto.getGameName());
            queryWrapper.eq(!StringUtils.isEmpty(dto.getParentGameId()),"parent_game_id",dto.getParentGameId());

            IPage data = page(page, queryWrapper);
            if (data != null && !CollectionUtils.isEmpty(data.getRecords())){
                return R.ok(data);
            }
        } catch (Exception e) {
            log.error("getGameList_error={}",e.getMessage(),e);
        }
        log.info("getGameList查无数据");
        return R.failed(new ErrorCode("查无数据"));
    }

    /**
     * 根据父游戏ID查询关联游戏
     * @param dto
     * @return
     */
    @Override
    public R selectChildrenGame(GamePageParamsDto dto) {
        log.info("selectChildrenGame_dto={}",dto);
        try {
            if (dto.getParentGameId() == null){
                log.info("缺少必要参数");
                return R.failed(new ErrorCode("缺少必要参数"));
            }
            List<GameEntity> list = list(new QueryWrapper<GameEntity>().eq("parent_game_id", dto.getParentGameId()));
            return R.ok(list);
        } catch (Exception e) {
            log.error("getGameList_error={}",e.getMessage(),e);
        }
        return R.failed(new ErrorCode("查询失败"));
    }

    /**
     * 查询所有游戏
     * @return
     */
    @Override
    public R allList() {
        try {
            return R.ok(list(new QueryWrapper<GameEntity>().orderByDesc("created_date")));
        } catch (Exception e) {
            log.error("allGameList_error={}",e.getMessage(),e);
        }
        return R.failed(new ErrorCode("获取失败"));
    }


}
