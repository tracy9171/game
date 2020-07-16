package com.wx.app.game.pay.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
/**
* @Description: 预下单接受类
* @author:
* @date :2020-06-13 16:26:01
* @params:
 */
@Data
public class PayParamsDto implements Serializable {
    //系统类型
    @NotNull
    private Integer systems;
    //角色id
    @NotBlank
    private String roleId;
    //用户id
    @NotBlank
    private String userId;
    //CP订单号
    @NotBlank
    private  String  cpOrderNO;
    //游戏名称
    @NotBlank
    private String gameName;
    //产品价格
    @NotNull
    private BigDecimal payment;
    //支付code
    @NotBlank
    private String code;
    //游戏Id
    @NotBlank
    private Integer gameId;
    //产品名称
    @NotBlank
    private String productName;
    @NotBlank
    private String token;
}
