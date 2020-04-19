package com.wx.app.game.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName BoxEntity
 * @Description 盒子
 * @Author Administrator
 * @Date 2020-03-22 21:09
 * @Version 1.0
 */
@Data
@TableName(value  = "wx_admin_box")
public class BoxEntity extends BaseEntity{

    @TableId(value = "ID",type = IdType.AUTO)
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
