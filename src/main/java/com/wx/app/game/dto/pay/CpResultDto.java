package com.wx.app.game.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CpResultDto implements Serializable {
    private Integer code;
    private String msg;
}
