package com.xs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xs.common.Result;
import com.xs.domain.Comment;
import com.xs.domain.Consumer;
import com.xs.domain.MessageEvent;
import com.xs.domain.ReplyAction;
import com.xs.mapper.ReplyActionMapper;
import com.xs.service.CommentService;
import com.xs.service.ConsumerService;
import com.xs.service.ReplyActionService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/reply")
@Slf4j
public class ReplyActionController {

    @Resource
    private ReplyActionMapper replyActionMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private ReplyActionService replyActionService;

    //事件发布器
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @GetMapping("/all/{userId}")
    public Result getAllReply(@PathVariable String userId) {
        LambdaQueryWrapper<ReplyAction> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ReplyAction::getReceiverId, userId);
        //未阅读的话，前端区分
//        lqw.eq(ReplyAction::getFlag, 1);
        lqw.eq(ReplyAction::getType, 1);
        List<ReplyAction> replyActions = replyActionMapper.selectList(lqw);
        for (ReplyAction replyAction : replyActions) {
            Comment comment = commentService.getById(replyAction.getCommentId());
            Comment reply = commentService.getById(replyAction.getReplyId());
            Consumer replyer = consumerService.getById(replyAction.getSenderId());
            replyAction.setReceiverComment(comment);
            replyAction.setReplyComment(reply);
            replyAction.setSender(replyer);
        }
        return Result.ok("获取所有回复成功", replyActions);
    }

    @PostMapping("/addReply")
    public Result addReplyAction(@RequestBody ReplyAction replyAction) {
        boolean save = replyActionService.save(replyAction);

        //构建完成的ReplyAction并发布这个事件
        Long id = replyAction.getId();
        ReplyAction byId = replyActionService.getById(id);
        log.warn("ById:" + byId);
        Consumer sender = consumerService.getById(byId.getSenderId());
        Consumer receiver = consumerService.getById(byId.getReceiverId());
        Comment comment = commentService.getById(byId.getCommentId());
        Comment reply = commentService.getById(byId.getReplyId());
        byId.setSender(sender);
        byId.setReceiver(receiver);
        byId.setReceiverComment(comment);
        byId.setReplyComment(reply);

        MessageEvent<ReplyAction> messageEvent = new MessageEvent<>(this, byId);
        log.warn("messageEvent:" + messageEvent);
        eventPublisher.publishEvent(messageEvent);

        if (save) {
            return Result.ok("回复动作成功,向对方发送了消息");
        }
        return Result.error("回复动作失败");
    }

    //接口只做别人消息的回复
    @GetMapping("/getMyReply/{userId}")
    public Result getMyReply(@PathVariable String userId) {
        LambdaQueryWrapper<ReplyAction> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ReplyAction::getReceiverId, userId);
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

    @GetMapping("/getMyReply/unviewed/{userId}")
    public Result getMyReplyUnviewed(@PathVariable String userId) {
        return replyActionService.getMyReplyUnviewed(userId);

    }
}
