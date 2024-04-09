package com.xs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xs.common.Result;
import com.xs.domain.Chat;
import com.xs.domain.ReplyAction;
import com.xs.mapper.ChatMapper;
import com.xs.service.ReplyActionService;
import com.xs.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
public class MessageQueryController {



    @Autowired
    private ReplyActionService replyActionService;

    @Autowired
    private ChatMapper chatMapper;



    @GetMapping("/getMyUnViewedReply/{userId}")
    public Result getMyUnViewedReply(@PathVariable String userId){
//        1.replyAction
        Result myReplyUnviewed = replyActionService.getMyReplyUnviewed(userId);
        return Result.ok("获取我未读的回复成功", myReplyUnviewed);
//        List<ReplyAction> replyActions= (List<ReplyAction>) myReplyUnviewed.getData();
//        if (Objects.nonNull(replyActions)) {
//            session.getBasicRemote().sendObject(replyActions);
//        }

    }

    /**
     * 获取我未读的私信
     * @return
     */
    @GetMapping("/getMyUnviewedMessage/{userId}")
    public Result getMyUnviewedMessage(@PathVariable String userId){
        //缓存我沟通过的人

        //2.私信
        LambdaQueryWrapper<Chat> lqw =new LambdaQueryWrapper<>();
        lqw.eq(Chat::getReceiverId, userId).eq(Chat::getType, 1);
        List<Chat> chats = chatMapper.selectList(lqw);
        return Result.ok("success",chats);
    }


}
