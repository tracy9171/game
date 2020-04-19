package com.wx.app.game.Entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName BaseEntity
 * @Description TODO
 * @Author Administrator
 * @Date 2020-03-20 16:06
 * @Version 1.0
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdDate =LocalDateTime.now();
    private LocalDateTime updatedDate =LocalDateTime.now();

    @ApiModelProperty(name="逻辑删除表示，1正常，2已删除")
    @TableLogic
    private int isDelete=1;
}
