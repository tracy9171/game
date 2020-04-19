package com.wx.app.game.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import reactor.util.annotation.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName RoleDto
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-25 23:40
 * @Version 1.0
 */
@Data
public class RoleDto implements Serializable {

    @ApiModelProperty(name="角色ID")
    @NonNull
    private String roleId;

    @ApiModelProperty(name="角色区服")
    private String roleArea;

    @ApiModelProperty(name="角色名称")
    private String roleName;

    @ApiModelProperty(name="玩家游戏ID")
    private Long gameId;

    @ApiModelProperty(name="玩家账号(游戏账号)")
    private String gameAccount;

    @ApiModelProperty(name="游戏名称")
    private String gameName;

    @ApiModelProperty(name="操作系统:1安卓系统、2IOS系统")
    private Integer systems;

    @ApiModelProperty(name="注册时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
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
