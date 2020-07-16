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
 * 补单信息
 * @author 
 */
@Data
@TableName(value  = "wx_replacement_order")
public class WxReplacementOrderEntity  implements Serializable {

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
     * 用户id
     */
    private String userId;

    /**
     * 玩家游戏id
     */
   // private Integer gameId;

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
    private BigDecimal orderMoney;

    /**
     * 支付金额
     */
    private BigDecimal payMoney;

    /**
     * 下单时间
     */
    private Date placeOrderDate;

    /**
     * 游戏区服
     */
   // private String gameArea;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 1未通知、2通知CP成功、3通知CP失败
     */
    private Integer notifyStatus;
    /**
     * 游戏关联id
     */
    private Integer gameId;
    /**
     * 支付状态
     */
    private Integer orderStatus;
    /**
     * 支付状态失败原因
     */
    private String remark;
    private static final long serialVersionUID = 1L;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDate =LocalDateTime.now();
    private LocalDateTime updatedDate =LocalDateTime.now();

}