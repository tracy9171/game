package com.wx.app.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.game.Entity.RoleEntity;
import com.wx.app.game.Entity.WxGameDataCountEntity;
import com.wx.app.game.commom.ErrorCode;
import com.wx.app.game.dto.RoleDto;
import com.wx.app.game.dto.RolePageDto;
import com.wx.app.game.dto.WxGameLoginRecordDto;
import com.wx.app.game.mapper.RoleMapper;
import com.wx.app.game.service.RoleService;
import com.wx.app.game.service.WxGameDataCountService;
import com.wx.app.game.utils.CheckPramsUtils;
import com.wx.app.game.utils.InitPageUtils;
import com.wx.app.game.utils.RedisLocks;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @ClassName RoleServiceImpl
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-26 0:14
 * @Version 1.0
 */
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {

    @Autowired
    private WxGameDataCountService wxGameDataCountServiceImpl;
    //新增角色判断角色ID是否存在，存在不支持新增
    @Override
    public R addRole(RoleDto dto) {
        log.info("addRole_dto={}",dto);

        if (CheckPramsUtils.isEmpty(dto.getRoleId(),dto.getGameAccount(),dto.getUserId())
                || dto.getSystems() != null
                || dto.getRegisterDate() !=null){
            log.info("新增角色缺少角色ID入参");
            return R.failed(new ErrorCode(1,"缺少必要参数"));
        }
        RedisLocks lock = new RedisLocks();
        String key ="ADD_ROLE_LOCK_KEY" + dto.getRoleId();
        if (!lock.lock(key)){
            log.info("操作频繁");
            return R.failed(new ErrorCode(2,"请求太频繁"));
        }
        try {
            if (getRole(dto) != null){
                log.info("角色ID已存在");
                return R.failed(new ErrorCode(3,"该角色已存在不能新增"));
            }
            RoleEntity entity = new RoleEntity();
            BeanUtils.copyProperties(dto,entity);
            save(entity);
            doRecord(dto);
            return R.ok("");
        } catch (BeansException e) {
            log.error("addRole_error={}",e.getMessage(),e);
        }finally {
            lock.releaseLock(key);
        }
        return R.failed(new ErrorCode("新增失败"));
    }

    /**
     * 统计创角数
     * @param dto
     */
    private void doRecord(RoleDto dto){
        WxGameDataCountEntity countEntity = new WxGameDataCountEntity();
        if (dto.getSystems() == 1){//安卓创角数
            countEntity.setARoleCount(1);
        }else {
            countEntity.setIRoleCoun(1);
        }
        wxGameDataCountServiceImpl.updateDataById(countEntity);
    }

    /**
     * 分页查询角色列表
     * @param dto
     * @return
     */
    @Override
    public R getRoleList(RolePageDto dto) {
        log.info("getRoleList_dto={}",dto);
        try {
            Page page = InitPageUtils.getPage(dto.getLimit(), dto.getPage());
            QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(!StringUtils.isEmpty(dto.getGameAccount()),"game_account",dto.getGameAccount());
            queryWrapper.eq(!StringUtils.isEmpty(dto.getGameId()),"game_id",dto.getGameId());

            queryWrapper.like(!StringUtils.isEmpty(dto.getRoleArea()),"role_area",dto.getRoleArea());
            queryWrapper.like(!StringUtils.isEmpty(dto.getRoleName()),"role_name",dto.getRoleName());
            queryWrapper.eq(!StringUtils.isEmpty(dto.getSystems()),"status",dto.getSystems());
            if (dto.getRegisterBeginDate() !=null && dto.getRegisterEndDate() != null){
                queryWrapper.between("register_date",dto.getRegisterBeginDate(),dto.getRegisterEndDate());
            }
            IPage data = page(page, queryWrapper);
            return R.ok(data);
        } catch (Exception e) {
            log.error("getPlayUserList_error={}",e.getMessage(),e);
        }
        return R.failed(new ErrorCode("未知异常"));
    }

    /**
     * 根据角色ID查询角色信息
     * @param dto
     * @return
     */
    @Override
    public R selectOneByRoleId(RoleDto dto) {
        log.info("selectOneByRoleId_dto={}",dto);
        try {
            if (StringUtils.isEmpty(dto.getRoleId())){
                log.info("新增角色缺少角色ID入参");
                return R.failed(new ErrorCode(1,"缺少必要参数"));
            }
            return R.ok(getRole(dto));
        } catch (Exception e) {
            log.error("selectOneByRoleId_error={}",e.getMessage(),e);
        }
        return R.failed(new ErrorCode("查询失败"));
    }

    private RoleEntity getRole(RoleDto dto){
        return getOne(new QueryWrapper<RoleEntity>().eq("role_id", dto.getRoleId()));
    }
}
