package com.wx.app.game.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName RoleEntity
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-25 23:40
 * @Version 1.0
 */
@Data
@TableName(value  = "wx_admin_play_role")
public class RoleEntity extends BaseEntity{

    @TableId(value = "ID",type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(name="角色ID")
    private String roleId;

    @ApiModelProperty(name="玩家游戏ID")
    private Long gameId;

    @ApiModelProperty(name="玩家账号(游戏账号)")
    private String gameAccount;

    @ApiModelProperty(name="游戏名称")
    private String gameName;

    @ApiModelProperty(name="角色区服")
    private String roleArea;

    @ApiModelProperty(name="角色名称")
    private String roleName;

    @ApiModelProperty(name="操作系统:1安卓系统、2IOS系统")
    private Integer systems;

    @ApiModelProperty(name="注册时间")
    private LocalDateTime registerDate;

    @ApiModelProperty(name="玩家ID")
    private String userId;

    @ApiModelProperty(name="注册渠道")
    private String registerChannel;

    @ApiModelProperty(name="等级")
    private Integer grade;

    @ApiModelProperty(name="充值金额")
    private BigDecimal rechargeMoney;

    @ApiModelProperty(name="战力")
    private String battle;
}
