package com.xs.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论
 * TableName comment
 */
@Data
public class Comment implements Serializable {
    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 评论类型(1歌单0歌曲)
     */
    private Integer type;

    /**
     * 歌曲id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long songId;

    /**
     * 歌单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long songListId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime createTime;

    /**
     * 评论点赞数
     */
    private Integer up;

    /**
     * 父评论id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;

    /**
     * 被回复的用户id
     */

    @JsonSerialize(using = ToStringSerializer.class)
    private Long target;


    @Serial
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private String avatar;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private List<Comment> children;

    @TableField(exist = false)
    private String targetName;
}