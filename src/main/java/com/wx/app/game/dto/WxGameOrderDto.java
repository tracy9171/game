package com.wx.app.game.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 交易订单
 * @author 
 */
@Data
public class WxGameOrderDto extends BaseDto {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * cp订单号
     */
    private String cpOrderNo;

    /**
     * 玩家id
     */
    private String userId;

    /**
     * 玩家游戏id
     */
   // private String gameId;

    /**
     * 玩家账号
     */
    //private String gameAccount;

    /**
     * 角色名称
     */
    //private String roleName;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 游戏名称
     */
    //private String gameName;

    /**
     * 订单金额
     */
    private Double orderMoney;

    /**
     * 实收金额
     */
    private Double payMoney;

    /**
     * 下单时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date placeOrderDate;

    /**
     * 游戏区服
     */
    //private String gameArea;

    /**
     * 1：安卓系统、2：IOS系统
     */
    private Integer systems;

    /**
     * 1未支付、2支付成功、3支付失败
     */
    private Integer orderStatus;
}