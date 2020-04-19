package com.wx.app.game.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName GnamePageParamsDto
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-23 21:53
 * @Version 1.0
 */
@Data
public class GamePageParamsDto extends BaseDto {
    @ApiModelProperty(name="游戏名称")
    private String gameName;

    @ApiModelProperty(name="马甲关联游戏：父游戏ID")
    private Long parentGameId;

}
