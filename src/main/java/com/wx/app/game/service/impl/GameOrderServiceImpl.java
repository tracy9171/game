package com.wx.app.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.game.Entity.PlayUserEntity;
import com.wx.app.game.Entity.WxGameDataCountEntity;
import com.wx.app.game.Entity.WxGameOrderEntity;
import com.wx.app.game.commom.ErrorCode;
import com.wx.app.game.dto.PlayUserDto;
import com.wx.app.game.dto.WxGameOrderDto;
import com.wx.app.game.mapper.WxGameOrderMapper;
import com.wx.app.game.service.GameOrderService;
import com.wx.app.game.service.PlayUserService;
import com.wx.app.game.service.WxGameDataCountService;
import com.wx.app.game.utils.CheckPramsUtils;
import com.wx.app.game.utils.InitPageUtils;
import com.wx.app.game.utils.RedisLocks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


/**
 * 游戏订单
 */
@Service
@Slf4j
public class GameOrderServiceImpl extends ServiceImpl<WxGameOrderMapper, WxGameOrderEntity> implements GameOrderService {

    @Autowired
    private WxGameDataCountService wxGameDataCountServiceImpl;

    @Autowired
    private PlayUserService playUserServiceImpl;

    /**
     * 分页查询游戏订单
     * @param dto
     * @return
     */
    @Override
    public R getGameOrderList(WxGameOrderDto dto) {
        log.info("getGameOrderList参数->{}",dto);
        try {
            Page page = InitPageUtils.getPage(dto.getLimit(), dto.getPage());
            QueryWrapper queryWrapper = new QueryWrapper<WxGameOrderEntity>();
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
     * 新增游戏订单
     * @param dto
     * @return
     */
    @Override
    public R addGameOrder(WxGameOrderDto dto) {
        log.info("addGameOrder_dto={}",dto);
        boolean empty = CheckPramsUtils.isEmpty(dto.getCpOrderNo(), dto.getOrderNo(),dto.getUserId(),dto.getRoleId());
        if (empty ||dto.getSystems() == null || dto.getOrderMoney() == null || dto.getPayMoney() == null){
            log.info("缺少必要参数");
            return R.failed(new ErrorCode(1,"缺少必要参数"));
        }
        RedisLocks lock = new RedisLocks();
        String key ="add_Game_Order_lock_key" + dto.getUserId() + dto.getOrderNo();
        if (!lock.lock(key)){
            log.info("同一订单频繁请求，不正常");
            return R.failed(new ErrorCode(2,"操作频繁"));
        }
        try {

            WxGameOrderEntity oneOrder = getOneOrder(dto);
            if (oneOrder !=null){
                log.info("该订单已存在");
                return R.failed(new ErrorCode(3,"订单已存在"));
            }
            WxGameOrderEntity entity = new WxGameOrderEntity();
            BeanUtils.copyProperties(dto,entity);
            save(entity);
            doRecord(dto);
            return R.ok("");
        } catch (Exception e) {
            log.error("addGameOrder_error={}",e.getMessage(),e);
        }finally {
            lock.releaseLock(key);
        }
        return R.failed(new ErrorCode("新增失败"));
    }

    private void doRecord(WxGameOrderDto dto){
        WxGameDataCountEntity countEntity = new WxGameDataCountEntity();
        PlayUserEntity playUser = playUserServiceImpl.getPlayUserByToDay(dto.getUserId());
        if (dto.getSystems() == 1){//安卓
            countEntity.setAAllPayment(1);
            countEntity.setAAllAmount(dto.getPayMoney());
            countEntity.setALoginRecordAmount(1);
            if (playUser !=null){
                countEntity.setANewUserPayment(1);
                countEntity.setANewUserAmount(dto.getPayMoney());
            }
        }else {
            countEntity.setIAllPayment(1);
            countEntity.setIAllAmount(dto.getPayMoney());
            countEntity.setILoginRecordAmount(1);
            if (playUser != null){
                countEntity.setINewUserPayment(1);
                countEntity.setINewUserAmount(dto.getPayMoney());
            }
        }
        wxGameDataCountServiceImpl.updateDataById(countEntity);
    }

    /**
     * 根据订单号和cp订单号修改订单状态
     * @param dto
     * @return
     */
    @Override
    public R updateOrderStatusByOrderNo(WxGameOrderDto dto) {
        log.info("updateOrderStatusByOrderNo_dto={}",dto);
        RedisLocks lock = new RedisLocks();
        String key ="UPDATE_ORDER_STATUS_BY_ORDER_NO_LOCK_KEY" + dto.getCpOrderNo() + dto.getOrderNo();
        if (!lock.lock(key)){
            log.info("同一订单频繁请求，不正常");
            return R.failed(new ErrorCode(2,"操作频繁"));
        }
        try {
            boolean empty = CheckPramsUtils.isEmpty(dto.getUserId(), dto.getOrderNo());
            if (empty || dto.getOrderStatus() == null){
                log.info("订单编号和cp订单号不能为空");
                return R.failed(new ErrorCode("缺少必要参数"));
            }
            WxGameOrderEntity oneOrder = getOneOrder(dto);
            if (oneOrder ==null){
                log.info("订单不存在");
                return R.failed(new ErrorCode("订单不存在"));
            }
            oneOrder.setOrderStatus(dto.getOrderStatus());
            updateById(oneOrder);
            return R.ok("");
        } catch (Exception e) {
            log.error("updateOrderStatusByOrderNo_error={}",e.getMessage(),e);
        }finally {
            lock.releaseLock(key);
        }
        return R.failed(new ErrorCode("更新失败"));
    }


    private WxGameOrderEntity getOneOrder(WxGameOrderDto dto){
        return getOne(new QueryWrapper<WxGameOrderEntity>().eq("order_no", dto.getOrderNo()).eq("user_id", dto.getUserId()));
    }

    /**
     * 根据订单号查询游戏订单
     * @param OrderNo
     * @return
     */
    @Override
    public WxGameOrderEntity getGameOrderByOrderNo(String OrderNo) {
        return getOne(new QueryWrapper<WxGameOrderEntity>().eq("order_no", OrderNo).orderByDesc("created_date"));
    }

    @Override
    public WxGameOrderEntity getGameOrderByCpOrderNo(String cpOrderNo) {
        return getOne(new QueryWrapper<WxGameOrderEntity>().eq("cp_order_no", cpOrderNo).orderByDesc("created_date"));
    }
}
