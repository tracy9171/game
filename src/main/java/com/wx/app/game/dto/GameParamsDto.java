package com.wx.app.game.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName GameParamsDto
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-22 0:26
 * @Version 1.0
 */
@Data
public class GameParamsDto implements Serializable {
    private Long id;
    private String gameName;
    private String appId;
    private Integer payMethod;
    private Integer checkStatus;
    private Long parentGameId;
    private String callbackUrl;
    private String channelUrl;
    @ApiModelProperty(name="游戏版本")
    private String version;

    @ApiModelProperty(name="小程序密钥")
    private String appSecret;

    @ApiModelProperty(name="收款商户账号")
    private String mchId;

    @ApiModelProperty(name="API签名密钥")
    private String apiSecret;

    @ApiModelProperty(name="米大师支付应用ID")
    private String miDasId;

    @ApiModelProperty(name="米大师现网AppKey")
    private String miDasAppKey;

    @ApiModelProperty(name="米大师沙箱AppKey")
    private String miDasBoxAppKey;

    @ApiModelProperty(name="IOS充值提示")
    private String iosAlter;

    @ApiModelProperty(name="人民币元宝比例")
    private String rmbToyb;

    @ApiModelProperty(name="分享图片")
    private String shareImgUrl;
}
