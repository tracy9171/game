package com.wx.app.game.pay.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PayParamsDto implements Serializable {

    private String address_id;
    private String address_name;
    private String address_mobile;
    private String shop_id;
    private String offline;
    private String cart_id_list;
    private String goods_info;
    private String user_coupon_id;
    private String content;
    private String formId;
    @NotNull
    private String gameName;
    @NotNull
    private BigDecimal payment;
    @NotNull
    private String code;
}
