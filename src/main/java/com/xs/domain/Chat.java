package com.xs.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 
 * @TableName chat
 */
@TableName(value ="chat")
@Data
public class Chat implements Serializable {
    /**
     * 
     */
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long senderId;

    /**
     * 
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long receiverId;

    /**
     * 
     */
    private String context;

    /**
     * 
     */
    private String img;

    /**
     * 
     */
    private Date createTime;

    /**
     * 0 未读 1已读
     */
    private Integer type;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}