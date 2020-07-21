package com.wx.app.game.dto.pay;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GameUserDto implements Serializable {
    private Integer id;
    @ApiModelProperty(name="用户id")
    private String userId;
    @ApiModelProperty(name="头像地址")
    private String avatar;
    @ApiModelProperty(name="用户名")
    private String username;
    @ApiModelProperty(name="昵称")
    private String nickname;
    @ApiModelProperty(name="性别：0未知、1男、2女")
    private String gender;
    @ApiModelProperty(name="城市")
    private String city;
    @ApiModelProperty(name="省份")
    private String province;
    @ApiModelProperty(name="国家")
    private String country;
    //游戏iD
    private Integer gameId;
    private String token;
}
