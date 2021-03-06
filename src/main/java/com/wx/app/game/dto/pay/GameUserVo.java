package com.wx.app.game.dto.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class GameUserVo {
    @NotBlank
    private String code;
    @NotNull
    private Integer gameId;
    @ApiModelProperty(name="头像地址")
    private String avatar;
    @ApiModelProperty(name="昵称")
    private String nickname;
    @ApiModelProperty(name="微信账户")
    private String username;
    @ApiModelProperty(name="性别：0未知、1男、2女")
    private String gender;
    @ApiModelProperty(name="城市")
    private String city;
    @ApiModelProperty(name="省份")
    private String province;
    @ApiModelProperty(name="国家")
    private String country;
}
