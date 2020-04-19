package com.wx.app.game.Entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 游戏跳转记录
 * @author 
 */
@Data
@TableName(value  = "wx_game_skip_record")
public class WxGameSkipRecordEntity  extends BaseEntity {

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
}