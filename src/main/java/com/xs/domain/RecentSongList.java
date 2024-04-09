package com.xs.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 
 * @TableName recent_song_list
 */
@TableName(value ="recent_song_list")
@Data
public class RecentSongList implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long songListId;

    /**
     * 
     */
    private Integer count;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;



    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}