package com.wx.app.game.service.payService.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wx.app.game.Entity.pay.GameUserEntity;
import com.wx.app.game.constant.StringPools;
import com.wx.app.game.dto.pay.GameUserDto;
import com.wx.app.game.dto.pay.GameUserVo;
import com.wx.app.game.mapper.pay.GameUserMapper;
import com.wx.app.game.service.payService.GameUserService;
import com.wx.app.game.utils.RedisClient;
import com.wx.app.game.utils.TokenUtil;
import com.wx.app.game.utils.pay.MobileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;


/**
 * 用户信息
 */
@Slf4j
@Service
public class GameUserServiceImpl extends ServiceImpl<GameUserMapper, GameUserEntity> implements GameUserService {

    @Autowired
    private RedisClient redisClient;
    /**
     * 用户登陆
     * @param vo
     * @return
     */
    @Override
    public R userLogin(GameUserVo vo) {
        String openId = MobileUtil.getGameOpenId(vo.getCode());
        if (StringUtils.isEmpty(openId)){
            return R.failed("登陆失败");
        }
        GameUserEntity userEntity = getOne(new QueryWrapper<GameUserEntity>().eq("open_id", openId).eq("game_id",vo.getGameId()));
        if (userEntity == null){
            userEntity =  new GameUserEntity();
            BeanUtils.copyProperties(vo,userEntity);
            userEntity.setCreatedBy(vo.getNickname());
            userEntity.setUpdatedBy(vo.getNickname());
            userEntity.setOpenId(openId);
            userEntity.setUserId(UUID.randomUUID().toString().replaceAll("-",""));
            save(userEntity);
        }
        String token = TokenUtil.getToken(userEntity.getUserId());
        String key = StringPools.LOGIN_KEY + userEntity.getUserId();
        redisClient.set(key,userEntity.getUserId(),StringPools.EXP);
        GameUserDto result = new GameUserDto();
        BeanUtils.copyProperties(userEntity,result);
        result.setToken(token);
        return R.ok(result);
    }
}
