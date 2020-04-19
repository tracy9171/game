package com.wx.app.game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName TokenDto
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-21 0:17
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
public class TokenDto implements Serializable {
    private String token;
}
