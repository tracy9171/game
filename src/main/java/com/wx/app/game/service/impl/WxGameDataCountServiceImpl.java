package com.wx.app.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.game.Entity.WxGameDataCountEntity;
import com.wx.app.game.constant.StringPools;
import com.wx.app.game.dto.WxGameDataDto;
import com.wx.app.game.mapper.WxGameDataCountMapper;
import com.wx.app.game.service.WxGameDataCountService;
import com.wx.app.game.utils.RedisLocks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 保存或更新用户数据
 */
@Slf4j
@Service
public class WxGameDataCountServiceImpl extends ServiceImpl<WxGameDataCountMapper, WxGameDataCountEntity> implements WxGameDataCountService {
    /**
     * 查询今天是否有记录
     * @return
     */
    @Override
    public WxGameDataCountEntity getOneByToDay() {
        return getOne(new QueryWrapper<WxGameDataCountEntity>().ge("create_time", LocalDate.now()).orderByDesc("create_time"));
    }

    /**
     * 更新数据
     * @param entity
     * @return
     */
    @Override
    public void updateDataById(WxGameDataCountEntity entity) {
        if (entity == null)return;
        log.info("updateDataById={}",entity);


        try {
            WxGameDataCountEntity oneByToDay = getOneByToDay();
            if (oneByToDay == null){
                entity.setCreateTime(new Date());
                save(entity);
                return ;
            }
            UpdateWrapper<WxGameDataCountEntity> updateWrapper = new UpdateWrapper<>();
            //安卓平台跳转数
            if(entity.getASkipCount() != null)
                updateWrapper.set("a_skip_count",entity.getASkipCount() + oneByToDay.getASkipCount());

            //IOS平台跳转数
            if (entity.getISkipCount() != null)
                updateWrapper.set("i_skip_count",entity.getISkipCount() + oneByToDay.getISkipCount());

            //安卓创角数
            if (entity.getARoleCount() != null)
                updateWrapper.set("a_role_count",entity.getARoleCount() + oneByToDay.getARoleCount());

            //IOS创角数
            if (entity.getIRoleCoun() != null)
                updateWrapper.set("i_role_coun",entity.getIRoleCoun() + oneByToDay.getIRoleCoun());

            //安卓登录人数
            if (entity.getASkipLoginCount() != null)
                updateWrapper.set("a_skip_login_count",entity.getASkipLoginCount() + oneByToDay.getASkipLoginCount());

            //ios登录人数
            if (entity.getISkipLoginCount() != null)
                updateWrapper.set("i_skip_login_count",entity.getISkipLoginCount() + oneByToDay.getISkipLoginCount());

            //安卓新增用户数
            if (entity.getAUserCount() != null)
                updateWrapper.set("a_user_count",entity.getAUserCount() + oneByToDay.getAUserCount());

            //ios新增用户数
            if (entity.getIUserCount() != null)
                updateWrapper.set("i_user_count",entity.getIUserCount() + oneByToDay.getIUserCount());

            //安卓新增用户付费数
            if (entity.getANewUserPayment() != null)
                updateWrapper.set("a_new_user_payment",entity.getANewUserPayment() + oneByToDay.getANewUserPayment());

            //ios新增用户付费数
            if (entity.getINewUserPayment() != null)
                updateWrapper.set("i_new_user_payment",entity.getINewUserPayment() + oneByToDay.getINewUserPayment());

            //安卓新增用户付费额
            if (entity.getANewUserAmount() != null)
                updateWrapper.set("a_new_user_amount",entity.getANewUserAmount() + oneByToDay.getANewUserAmount());

            //ios新增用户付费额
            if (entity.getINewUserAmount() != null)
                updateWrapper.set("i_new_user_amount",entity.getINewUserAmount() + oneByToDay.getINewUserAmount());

            //安卓总付费人数
            if (entity.getAAllPayment() != null)
                updateWrapper.set("a_all_payment",entity.getAAllPayment() + oneByToDay.getAAllPayment());

            //ios总付费人数
            if (entity.getIAllPayment() != null)
                updateWrapper.set("i_all_payment",entity.getIAllPayment() + oneByToDay.getIAllPayment());

            //安卓总付费金额
            if (entity.getAAllAmount() != null)
                updateWrapper.set("a_all_amount",entity.getAAllAmount() + oneByToDay.getAAllAmount());

            //ios总付费金额
            if (entity.getIAllAmount() != null)
                updateWrapper.set("i_all_amount",entity.getIAllAmount() + oneByToDay.getIAllAmount());

            //安卓活跃登录人数
            if (entity.getALoginRecord() != null)
                updateWrapper.set("a_login_record",entity.getALoginRecord() + oneByToDay.getALoginRecord());

            //ios活跃登录人数
            if (entity.getILoginRecord() != null)
                updateWrapper.set("i_login_record",entity.getILoginRecord() + oneByToDay.getILoginRecord());

            //安卓活跃付费数
            if (entity.getALoginRecordAmount() != null)
                updateWrapper.set("a_login_record_amount",entity.getALoginRecordAmount() + oneByToDay.getALoginRecordAmount());

            //ios活跃付费数
            if (entity.getILoginRecordAmount() != null)
                updateWrapper.set("i_login_record_amount",entity.getILoginRecordAmount() + oneByToDay.getILoginRecordAmount());
            updateWrapper.eq("id",oneByToDay.getId());
            update(new WxGameDataCountEntity(),updateWrapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int updateData(WxGameDataDto dto) {
        return baseMapper.updateData(dto);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public int updateDataLeLock(WxGameDataDto dto) {
        WxGameDataCountEntity countEntity = baseMapper.selectByIdLock(dto.getId());
        System.out.println("countEntity="+countEntity);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dto.setaSkipCount(countEntity.getASkipCount() + dto.getaSkipCount());
        return baseMapper.updateDataLeLock(dto);
    }

    @Override
    public WxGameDataCountEntity selectByIdLock(Integer id) {
        return baseMapper.selectById(id);
    }
}
