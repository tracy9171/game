package com.wx.app.game.dto.pay;

import lombok.Data;

import java.io.Serializable;

@Data
public class CpParamsDto implements Serializable {
    /**
     * 游戏id
     */
    private Integer gameId;
    /**
     * cp订单号
     */
    private String cpOrderNo;
    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 付款时间
     */
    private Long payTime;
    /**
     * 加密串
     */
    private String sign;


}
