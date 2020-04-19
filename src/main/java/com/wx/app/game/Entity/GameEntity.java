package com.wx.app.game.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName GameEntity
 * @Description 游戏数据
 * @Author Administrator
 * @Date 2020-03-21 23:36
 * @Version 1.0
 */
@Data
@TableName(value  = "wx_admin_game")
public class GameEntity extends BaseEntity{
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(name="游戏名称")
    private String gameName;

    @ApiModelProperty(name="小程序AppId")
    private String appId;

    @ApiModelProperty(name="支付方式,关联支付盒子")
    private Integer payMethod;

    @ApiModelProperty(name="审核切换,1开启，2停用")
    private Integer checkStatus;

    @ApiModelProperty(name="马甲关联游戏：父游戏ID")
    private Integer parentGameId;

    @ApiModelProperty(name="回调地址")
    private String callbackUrl;

    @ApiModelProperty(name="渠道链接")
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

    @ApiModelProperty(name="小程序路径")
    private String miniUrl;

    @ApiModelProperty(name="跳转类型:1直跳、2二维码、3客服跳转")
    private Integer skipType;

    @ApiModelProperty(name="切换系统:1安卓系统、2IOS系统、3全选")
    private Integer systemType;
}
