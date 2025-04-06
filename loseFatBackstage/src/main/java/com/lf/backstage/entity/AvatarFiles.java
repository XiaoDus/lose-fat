package com.lf.backstage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 文件管理
 * </p>
 *
 * @author 小肚
 * @since 2025-03-18
 */
@Getter
@Setter
@ApiModel(value = "File对象", description = "文件管理")
@TableName("file")
public class AvatarFiles implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("下载链接")
    private String url;

    @ApiModelProperty("文件md5")
    private String md5;

    @TableField(exist = false)
    private String userName;
}
