package com.wx.app.game.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登陆记录
 * @author 
 */
@Data
@TableName(value  = "wx_game_login_record")
public class WxGameLoginRecordEntity extends BaseEntity {

    @TableId(value = "ID",type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(name="玩家ID")
    private String userId;

    @ApiModelProperty(name="玩家账户")
    private String gameAccount;

    @ApiModelProperty(name="游戏ID")
    private Integer gameId;

    @ApiModelProperty(name="登陆系统来源：1安卓系统、2IOS系统")
    private Integer systemType;

    @ApiModelProperty(name="角色ID")
    private String roleId;
}