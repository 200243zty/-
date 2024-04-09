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
 * @TableName reply_action
 */
@TableName(value = "reply_action")
@Data
public class ReplyAction implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 评论者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long senderId;

    /**
     * 消息接收者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long receiverId;

    /**
     * 被回复的评论id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentId;

    /**
     * 0 点赞了你 1回复了你
     */
    private Integer type;

    /**
     * 回复的评论id
     */
//    @JsonSerialize(using = ToStringSerializer.class)
    private String replyId;

    /**
     * 状态 0已阅 1未阅
     */
    private Integer flag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Comment receiverComment;

    @TableField(exist = false)
    private Comment replyComment;

    @TableField(exist = false)
    private Consumer sender;

    @TableField(exist = false)
    private Consumer receiver;
}