package com.wx.app.game.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 平台跳转过来的进入游戏人数
 * @author 
 */
@Data
@TableName(value  = "wx_game_skip_login_record")
public class WxGameSkipLoginRecordEntity extends BaseEntity {

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

    @ApiModelProperty(name="1平台跳转登陆人数，2游戏登陆人数")
    private Integer type;
}