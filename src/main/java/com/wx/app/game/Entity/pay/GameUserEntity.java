package com.wx.app.game.Entity.pay;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wx.app.game.Entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName(value  = "wx_admin_game")
public class GameUserEntity extends BaseEntity {
    @TableId(value = "ID",type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(name="用户id")
    private String userId;
    @ApiModelProperty(name="open_id")
    private String openId;
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
    private Integer gameId;
}
