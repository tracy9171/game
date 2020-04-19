package com.wx.app.game.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName PlayUserPagePraramsDto
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-24 21:13
 * @Version 1.0
 */
@Data
public class PlayUserPageParamsDto extends BaseDto {

    @ApiModelProperty(name="用户ID")
    private String userId;

    @ApiModelProperty(name="游戏账号")
    private String gameAccount;

    @ApiModelProperty(name="昵称")
    private String nickName;

    @ApiModelProperty(name="游戏ID")
    private Long gameId;

    @ApiModelProperty(name="注册IP")
    private String registerIp;

    @ApiModelProperty(name="1试玩用户，2正式用户")
    private Integer status;

    @ApiModelProperty(name="注册渠道")
    private String registerChannel;

    @ApiModelProperty(name="查询注册开始时间")
    private LocalDateTime registerStartTime;

    @ApiModelProperty(name="查询注册结束时间")
    private LocalDateTime registerEndTime;

    @ApiModelProperty(name="时间段：1今天、2昨天、3七天、4本周、5上周、6本月、7上月、8：30天")
    private Integer timeSlot;



}
