package com.wx.app.game.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 交易订单
 * @author 
 */
@Data
@TableName(value  = "wx_game_order")
public class WxGameOrderEntity  implements  Serializable{

    @TableId(value = "ID",type = IdType.AUTO)
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
     * 玩家id
     */
    private String userId;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 订单金额
     */
    private BigDecimal orderMoney;

    /**
     * 实收金额
     */
    private BigDecimal payMoney;

    /**
     * 下单时间
     */
    private Date placeOrderDate;

    /**
     * 1：安卓系统、2：IOS系统
     */
    private Integer systems;

    /**
     * 1未支付、2支付成功、3支付失败
     */
    private Integer orderStatus;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 关联游戏id
     */
    private Integer gameId;
    /**
     * 支付状态说明
     */
    private String remark;

    private static final long serialVersionUID = 1L;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDate =LocalDateTime.now();
    private LocalDateTime updatedDate =LocalDateTime.now();

    /**
     * 通知CP状态、0未通知，1已通知
     */
    private Integer notifyStatus;
    /**
     * 支付时间
     */
    private LocalDateTime payDate;
}