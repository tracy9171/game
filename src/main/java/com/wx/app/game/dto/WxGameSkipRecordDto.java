package com.wx.app.game.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName WxGameSkipRecordDto
 * @Description 玩家跳转
 * @Author Administrator
 * @Date 2020-03-29 13:54
 * @Version 1.0
 */
@Data
public class WxGameSkipRecordDto implements Serializable {
    @ApiModelProperty(name="玩家ID")
    private String userId;

    @ApiModelProperty(name="玩家账户")
    private String gameAccount;

    @ApiModelProperty(name="游戏ID")
    private Integer gameId;

    @ApiModelProperty(name="登陆系统来源：1安卓系统、2IOS系统")
    private Integer systemType;
}
