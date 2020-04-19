package com.wx.app.game.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName WxGameLoginRecordDto
 * @Description 玩家登陆记录入参实体
 * @Author Administrator
 * @Date 2020-03-29 13:54
 * @Version 1.0
 */
@Data
public class WxGameLoginRecordDto implements Serializable {
    @ApiModelProperty(name="玩家ID")
    private String userId;

    @ApiModelProperty(name="玩家账户")
    private String gameAccount;

    @ApiModelProperty(name="游戏ID")
    private Integer gameId;

    @ApiModelProperty(name="登陆系统来源：1安卓系统、2IOS系统")
    private Integer systemType;

    @ApiModelProperty(name="1平台跳转登陆人数，2游戏登陆人数")
    private Integer type;

    @ApiModelProperty(name="角色ID")
    private String roleId;
}
