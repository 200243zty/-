package com.xs.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 歌单
 * TableName song_list
 */
@Data
@NoArgsConstructor
public class SongList implements Serializable {

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 歌单图片
     */
    private String pic;

    /**
     * 歌单简介
     */
    private String introduction;

    /**
     * 类型 0系统歌单 1用户歌单
     */
    private Integer type;

    /**
     * 是否私有 0公开 1私有
     */
    private Integer isPrivate;

    /**
     * 歌单创建着 管理员or 用户
     * 只有用户的歌单私有才不会被搜索到
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createrId;

    /**
     * 歌单风格id
     */
    private Integer styleId;

    /**
     * 播放次数
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long count;

    //风格
    @TableField(exist = false)
    private String style;



    @Serial
    private static final long serialVersionUID = 1L;
}