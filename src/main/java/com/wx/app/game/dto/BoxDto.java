package com.wx.app.game.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName GameBoxDto
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-22 23:59
 * @Version 1.0
 */
@Data
public class BoxDto implements Serializable {

    private Long id;

    @ApiModelProperty(name="游戏ID")
    private Long gameId;

    @ApiModelProperty(name="游戏名称")
    private String gameName;

    @ApiModelProperty(name="小程序AppId")
    private String appId;

    @ApiModelProperty(name="渠道地址")
    private String channelUrl;

    @ApiModelProperty(name="小程序密钥")
    private String appSecret;

    @ApiModelProperty(name="收款商户号")
    private String mchId;

    @ApiModelProperty(name="api密钥")
    private String apiSecret;

    @ApiModelProperty(name="游戏状态:1上线，2下线")
    private Integer gameStatus;

    @ApiModelProperty(name="审核切换")
    private Integer checkStatus;

    @ApiModelProperty(name="回调地址")
    private String callbackUrl;

    @ApiModelProperty(name="小程序路径")
    private String miniUrl;

    @ApiModelProperty(name="跳转类型")
    private int skipType;

    @ApiModelProperty(name="切换系统")
    private int systemType;
}
