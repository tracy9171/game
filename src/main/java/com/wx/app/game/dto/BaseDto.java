package com.wx.app.game.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName BaseDto
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-30 21:49
 * @Version 1.0
 */
@Data
public class BaseDto implements Serializable {
    @ApiModelProperty(name="当前页")
    private Integer page ;

    @ApiModelProperty(name="页大小")
    private Integer limit;
}
