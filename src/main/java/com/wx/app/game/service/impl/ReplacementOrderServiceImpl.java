package com.wx.app.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.game.Entity.WxGameOrderEntity;
import com.wx.app.game.Entity.WxReplacementOrderEntity;
import com.wx.app.game.commom.ErrorCode;
import com.wx.app.game.dto.WxGameOrderDto;
import com.wx.app.game.dto.WxReplacementOrderDto;
import com.wx.app.game.mapper.WxReplacementOrderMapper;
import com.wx.app.game.service.ReplacementOrderService;
import com.wx.app.game.utils.CheckPramsUtils;
import com.wx.app.game.utils.InitPageUtils;
import com.wx.app.game.utils.RedisLocks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


/**
 * 补单列表
 */
@Slf4j
@Service
public class ReplacementOrderServiceImpl extends ServiceImpl<WxReplacementOrderMapper, WxReplacementOrderEntity> implements ReplacementOrderService {

    /**
     * 分页查询补单列表
     * @param dto
     * @return
     */
    @Override
    public R getRepOrderList(WxReplacementOrderDto dto) {
        log.info("getRepOrderList参数->{}",dto);
        try{
            Page page = InitPageUtils.getPage(dto.getLimit(), dto.getPage());
            IPage data = page(page, new QueryWrapper<WxReplacementOrderEntity>().orderByDesc("created_date"));
            return R.ok(data);
        }catch (Exception e){
            log.error("getRepOrderList--》{}",e.getMessage(),e);
        }
        return R.failed(new ErrorCode("补单列表查询失败"));
    }

    /**
     * 新增补单列表
     * @param dto
     * @return
     */
    @Override
    public R addRepOrder(WxReplacementOrderDto dto) {
        log.info("addRepOrder_dto={}",dto);
        boolean empty = CheckPramsUtils.isEmpty(dto.getUserId(), dto.getOrderNo());
        if (empty){
            log.info("缺少必要参数");
            return R.failed(new ErrorCode(1,"缺少必要参数"));
        }
        RedisLocks lock = new RedisLocks();
        String key ="ADD_GAME_REP_ORDER_LOCK_KEY" + dto.getUserId() + dto.getOrderNo();
        if (!lock.lock(key)){
            log.info("addRepOrder同一订单频繁请求，不正常");
            return R.failed(new ErrorCode(2,"请求太频繁"));
        }
        try {

            WxReplacementOrderEntity oneOrder = getOneOrder(dto);
            if (oneOrder !=null){
                log.info("新增补单订单已存在");
                return R.failed(new ErrorCode(3,"订单已存在"));
            }
            WxReplacementOrderEntity entity = new WxReplacementOrderEntity();
            BeanUtils.copyProperties(dto,entity);
            save(entity);
            return R.ok("");
        } catch (Exception e) {
            log.error("addGameOrder_error={}",e.getMessage(),e);
        }finally {
            lock.releaseLock(key);
        }
        return R.failed(new ErrorCode("新增失败"));
    }

    /**
     * 更新补单列表
     * @param dto
     * @return
     */
    @Override
    public R updateRepOrderStatusByOrderNo(WxReplacementOrderDto dto) {
        log.info("updateRepOrderStatusByOrderNo_dto={}",dto);


        boolean empty = CheckPramsUtils.isEmpty(dto.getUserId(), dto.getOrderNo());
        if (empty || dto.getNotifyStatus() == null){
            log.info("订单编号和cp订单号不能为空");
            return R.failed(new ErrorCode(1,"缺少必要参数"));
        }

        RedisLocks lock = new RedisLocks();
        String key ="UPDATE_REP_ORDER_STATUS_BY_ORDER_NO_LOCK_KEY" + dto.getUserId() + dto.getOrderNo();
        if (!lock.lock(key)){
            log.info("addRepOrder同一订单频繁请求，不正常");
            return R.failed(new ErrorCode(2,"请求太频繁"));
        }
        try {
            WxReplacementOrderEntity oneOrder = getOneOrder(dto);
            if (oneOrder ==null){
                log.info("订单不存在");
                return R.failed(new ErrorCode("订单不存在"));
            }
            oneOrder.setNotifyStatus(dto.getNotifyStatus());
            updateById(oneOrder);
            return R.ok("");
        } catch (Exception e) {
            log.error("updateOrderStatusByOrderNo_error={}",e.getMessage(),e);
        }finally {
            lock.releaseLock(key);
        }
        return R.failed(new ErrorCode("更新失败"));
    }

    /**
     * 根据订单号获取补单列表
     * @param dto
     * @return
     */
    @Override
    public R getRepOrderByOrderNo(WxReplacementOrderDto dto) {
        log.info("getRepOrderByOrderNo_dto={}",dto);
        try {
            if (CheckPramsUtils.isEmpty(dto.getOrderNo())){
                log.info("参数为空");
                return R.failed(new ErrorCode("参数为空"));
            }
            WxReplacementOrderEntity data = getOne(new QueryWrapper<WxReplacementOrderEntity>().eq("order_no", dto.getOrderNo()));
            return R.ok(data);
        } catch (Exception e) {
            log.error("getRepOrderByOrderNo_error={}",e.getMessage(),e);
        }
        return R.failed(new ErrorCode("查询失败"));
    }

    private WxReplacementOrderEntity getOneOrder(WxReplacementOrderDto dto){
        return getOne(new QueryWrapper<WxReplacementOrderEntity>().eq("order_no", dto.getOrderNo()).eq("user_id", dto.getUserId()));
    }
}



