package com.wx.app.game.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName playUserEntity
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-23 22:30
 * @Version 1.0
 */
@Data
@TableName(value  = "wx_admin_play_user")
public class PlayUserEntity extends BaseEntity{

    @TableId(value = "ID",type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(name="玩家ID")
    private String userId;

    @ApiModelProperty(name="玩家账号")
    private String gameAccount;

    @ApiModelProperty(name="玩家昵称")
    private String nickName;

    @ApiModelProperty(name="注册游戏")
    private Long gameId;

    @ApiModelProperty(name="注册渠道")
    private String registerChannel;

    @ApiModelProperty(name="注册IP")
    private String registerIp;

    @ApiModelProperty(name="状态:1试玩用户，2正式用户")
    private Integer status;

    @ApiModelProperty(name="真实姓名")
    private String realName;

    @ApiModelProperty(name="绑定手机号")
    private String mobile;

    @ApiModelProperty(name="注册时间")
    private LocalDateTime registerDate;

    @ApiModelProperty(name="系统来源：1安卓，2ios")
    private Long systemType;
}
