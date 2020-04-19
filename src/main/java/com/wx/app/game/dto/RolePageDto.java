package com.wx.app.game.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName RoleDto
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-25 23:47
 * @Version 1.0
 */
@Data
public class RolePageDto extends BaseDto {

    @ApiModelProperty(name="角色ID")
    private String roleId;

    @ApiModelProperty(name="角色区服")
    private String roleArea;

    @ApiModelProperty(name="角色名称")
    private String roleName;

    @ApiModelProperty(name="玩家账号(游戏账号)")
    private String gameAccount;

    @ApiModelProperty(name="游戏名称(传游戏ID)")
    private String gameId;

    @ApiModelProperty(name="操作系统:1安卓系统、2IOS系统")
    private Integer systems;

    @ApiModelProperty(name="查询开始注册时间")
    private LocalDateTime registerBeginDate;

    @ApiModelProperty(name="查询结束注册时间")
    private LocalDateTime registerEndDate;
}
