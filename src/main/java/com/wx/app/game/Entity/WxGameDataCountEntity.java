package com.wx.app.game.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * wx_game_data_count
 * @author 
 */
@Data
@TableName(value = "wx_game_data_count")
public class WxGameDataCountEntity implements Serializable {
    private Integer id;

    /**
     * 安卓平台跳转数
     */
    private Integer aSkipCount;

    /**
     * IOS平台跳转数
     */
    private Integer iSkipCount;

    /**
     * 安卓创角数
     */
    private Integer aRoleCount;

    /**
     * IOS创角数
     */
    private Integer iRoleCoun;

    /**
     * 安卓登录人数
     */
    private Integer aSkipLoginCount;

    /**
     * ios登录人数
     */
    private Integer iSkipLoginCount;

    /**
     * 安卓新增用户数
     */
    private Integer aUserCount;

    /**
     * ios新增用户数
     */
    private Integer iUserCount;

    /**
     * 安卓新增用户付费数
     */
    private Integer aNewUserPayment;

    /**
     * ios新增用户付费数
     */
    private Integer iNewUserPayment;

    /**
     * 安卓新增用户付费额
     */
    private Double aNewUserAmount;

    /**
     * ios新增用户付费额
     */
    private Double iNewUserAmount;

    /**
     * 安卓总付费人数
     */
    private Integer aAllPayment;

    /**
     * ios总付费人数
     */
    private Integer iAllPayment;

    /**
     * 安卓总付费金额
     */
    private Double aAllAmount;

    /**
     * ios总付费金额
     */
    private Double iAllAmount;

    /**
     * 安卓活跃登录人数
     */
    private Integer aLoginRecord;

    /**
     * ios活跃登录人数
     */
    private Integer iLoginRecord;

    /**
     * 安卓活跃付费数
     */
    private Integer aLoginRecordAmount;

    /**
     * ios活跃付费数
     */
    private Integer iLoginRecordAmount;

    /**
     * 创建时间
     */
    private Date createTime;
    private Date updatedTime;
    private static final long serialVersionUID = 1L;
}