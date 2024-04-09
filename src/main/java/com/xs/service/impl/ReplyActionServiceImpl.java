package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.Result;
import com.xs.domain.Comment;
import com.xs.domain.Consumer;
import com.xs.domain.ReplyAction;
import com.xs.service.CommentService;
import com.xs.service.ConsumerService;
import com.xs.service.ReplyActionService;
import com.xs.mapper.ReplyActionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 电脑
* @description 针对表【reply_action】的数据库操作Service实现
* @createDate 2024-03-07 15:55:20
*/
@Service
public class ReplyActionServiceImpl extends ServiceImpl<ReplyActionMapper, ReplyAction>
    implements ReplyActionService{

    @Resource
    private ReplyActionMapper replyActionMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ConsumerService consumerService;

    @Override
    public Result getMyReplyUnviewed(String userId) {
        LambdaQueryWrapper<ReplyAction> lqw = new LambdaQueryWrapper<>();
        //查询所有未阅读的
        lqw.eq(ReplyAction::getReceiverId, userId).eq(ReplyAction::getFlag, 1);
        List<ReplyAction> replyActions = replyActionMapper.selectList(lqw);
        for (ReplyAction replyAction : replyActions) {
            //查询关联的两个评论
            Long commentId = replyAction.getCommentId();
            Long replyId = Long.valueOf(replyAction.getReplyId());
            Long senderId = replyAction.getSenderId();
            Comment comment = commentService.getById(commentId);
            Comment reply = commentService.getById(replyId);
            Consumer sender = consumerService.getById(senderId);
            replyAction.setReceiverComment(comment);
            replyAction.setReplyComment(reply);
            replyAction.setSender(sender);
        }
        return Result.ok("获取你的回复成功", replyActions);
    }
}




