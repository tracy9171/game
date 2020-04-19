package com.wx.app.game.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName BoxParamsDto
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-23 0:27
 * @Version 1.0
 */
@Data
public class BoxPageParamsDto extends BaseDto {

    @ApiModelProperty(name="游戏名称")
    private String gameName;

    @ApiModelProperty(name="游戏状态:1上线，2下线")
    private Integer gameStatus;
}
