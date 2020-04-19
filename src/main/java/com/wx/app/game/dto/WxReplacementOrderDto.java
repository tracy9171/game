package com.wx.app.game.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 补单信息
 * @author 
 */
@Data
public class WxReplacementOrderDto extends BaseDto {

    private Integer id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * cp订单号
     */
    private String cpOrderNo;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 玩家游戏id
     */
    //private String gameId;

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
     * 支付金额
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
    private String gameArea;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 1未通知、2通知CP成功、3通知CP失败
     */
    private Integer notifyStatus;

}