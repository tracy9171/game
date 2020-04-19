package com.wx.app.game.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @ClassName IdDto
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-22 0:32
 * @Version 1.0
 */
@Data
public class IdDto implements Serializable {
    @NotEmpty
    private Long id;
}
