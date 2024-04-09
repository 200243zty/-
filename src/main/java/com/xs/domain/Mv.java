package com.xs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 
 * @TableName mv
 */
@TableName(value ="mv")
@Data
public class Mv implements Serializable {
    /**
     * 
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 歌手id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long singerId;

    /**
     * mv名称
     */
    private String name;

    /**
     * 简介
     */
    private String introduction;

    /**
     * mv图片
     */
    private String pic;

    /**
     * mv地址
     */
    private String url;

    /**
     * 播放量
     */
    private Integer playCount;

    /**
     * 收藏量
     */
    private Integer collectCount;

    /**
     * 评论量
     */
    private Integer commentCount;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}