package com.wx.app.game.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName PlayUserDto
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-24 20:55
 * @Version 1.0
 */
@Data
public class PlayUserDto implements Serializable {

    @ApiModelProperty(name="玩家ID")
    private String userId;

    @ApiModelProperty(name="玩家账号")
    private String gameAccount;

    @ApiModelProperty(name="玩家昵称")
    private String nickName;

    @ApiModelProperty(name="注册游戏")
    private String registerGame;

    @ApiModelProperty(name="注册渠道")
    private String registerChannel;

    @ApiModelProperty(name="注册IP")
    private String registerIp;

    @ApiModelProperty(name="状态:1试玩用户，2正式用户")
    private Integer status;

    @ApiModelProperty(name="真实姓名")
    private String realName;

    @ApiModelProperty(name="绑定手机号")
    private String mobile;

    @ApiModelProperty(name="注册时间")
    private LocalDateTime registerDate;

    @ApiModelProperty(name="系统来源：1安卓，2ios")
    private Long systemType;

}
