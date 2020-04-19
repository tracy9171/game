package com.wx.app.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.game.Entity.BoxEntity;
import com.wx.app.game.commom.ErrorCode;
import com.wx.app.game.dto.BoxPageParamsDto;
import com.wx.app.game.dto.IdDto;
import com.wx.app.game.mapper.BoxMapper;
import com.wx.app.game.service.BoxService;
import com.wx.app.game.utils.InitPageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * @ClassName BoxServiceImpl
 * @Description 盒子
 * @Author Administrator
 * @Date 2020-03-23 0:11
 * @Version 1.0
 */
@Slf4j
@Service
public class BoxServiceImpl extends ServiceImpl<BoxMapper, BoxEntity> implements BoxService {


    /**
     * 获取单条盒子信息
     * @param dto
     * @return
     */
    @Override
    public R getOne(IdDto dto) {
        log.info("getOne_dto={}",dto);
        try {
            if (dto == null){
                log.info("缺少必要参数");
                return R.failed(new ErrorCode(1,"缺少必要参数"));
            }
            BoxEntity data = getById(dto.getId());
            if (data != null)return R.ok(data);
        } catch (Exception e) {
            log.error("getOntBox_error={}",e.getMessage(),e);
        }
        return R.failed("查询失败");
    }

    /**
     * 分页查询
     * @param dto
     * @return
     */
    @Override
    public R getBoxList(BoxPageParamsDto dto) {
        log.info("getBoxList_dto={}",dto);
        try {
            Page page = InitPageUtils.getPage(dto.getLimit(), dto.getPage());
            QueryWrapper queryWrapper = new QueryWrapper<BoxEntity>();
            queryWrapper.like(!StringUtils.isEmpty(dto.getGameName()),"game_name",dto.getGameName());
            queryWrapper.eq(dto.getGameStatus() != null,"game_status",dto.getGameStatus());
            IPage data = page(page, queryWrapper);
            return R.ok(data);
        } catch (Exception e) {
            log.error("getBoxList_error={}",e.getMessage(),e);
        }
        return R.failed(new ErrorCode("查询失败"));
    }

    @Override
    public R getBoxAllList() {
        try {
            List<BoxEntity> list = list(new QueryWrapper<BoxEntity>().orderByDesc("created_date"));
            return R.ok(list);
        } catch (Exception e) {
            log.error("getBoxAllList_error={}",e.getMessage(),e);
        }
        return R.failed(new ErrorCode("查询失败"));
    }
}
