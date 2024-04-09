package com.xs.controller.ws;

import cn.hutool.db.nosql.redis.RedisDS;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xs.common.Result;
import com.xs.domain.*;
import com.xs.domain.message.Message;
import com.xs.mapper.ChatMapper;
import com.xs.mapper.ReplyActionMapper;
import com.xs.service.ChatService;
import com.xs.service.CommentService;
import com.xs.service.ConsumerService;
import com.xs.service.ReplyActionService;
import com.xs.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.ognl.OgnlContext;
import org.checkerframework.checker.units.qual.A;
import org.simpleframework.xml.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.management.relation.Relation;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 纯文本Websocket
 */
@ServerEndpoint(value = "/websocket/{userId}",encoders = {WebsocketEncoder.class})
@RestController
@Slf4j
public class WebSocketServer {

    //TODO 约定客户端->服务端互相发送消息的格式
    //TODO 服务端向客户端推送消息的格式,默认当前暂时不考虑推送系统消息,都是推送其它用法对该用户的消息
    private static Map<String, Session> clients = new ConcurrentHashMap<>();
    //websocket种的客户端session
    private Session session;

    private String userId;


    //WebSocket的实例是有多个的，无法用bean管理
//    private static ReplyActionService replyActionService;
//
//    @Autowired
//    private void setReplyActionService(ReplyActionService replyActionService) {
//        WebSocketServer.replyActionService = replyActionService;
//    }

    private static RedisTemplate<String,Object> redisTemplate;

    @Resource
    private void setRedisTemplate(RedisTemplate<String,Object> redisTemplate) {
        WebSocketServer.redisTemplate=redisTemplate;
    }

    private static ReplyActionService replyActionService;

    @Autowired
    private void setReplyActionService(ReplyActionService replyActionService) {
        WebSocketServer.replyActionService=replyActionService;
    }

    private static ChatService chatService;
    @Autowired

    private void setChatService(ChatService chatService) {
        WebSocketServer.chatService=chatService;
    }

    private static ChatMapper chatMapper;

    @Autowired
    private void setChatMapper(ChatMapper chatMapper) {
        WebSocketServer.chatMapper=chatMapper;
    }


    /**
     * 连接建立成功调用的方法,加上了userId,前端连接websocket地址的时候带上userId
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) throws EncodeException, IOException {
        /**
         * 对用户进行一次消息推送 点赞评论,歌曲/歌单 评论留言, 关注, 私信
         */
        log.info("userId:"+userId);
        this.userId=userId;
        this.session = session;
        if (clients.containsKey(userId)) {
            clients.remove(userId);
            clients.put(userId, session);
        }else {
            clients.put(userId, session);
        }
//TODO 逻辑更改了,如果你不在线，那么有人给你法律消息我们是不会有推送这个动作的，这些消息都去前端主动去查询的，这些逻辑就简化了很大





        //该用户登录之后就连接websocket 获取最新的用户信息推
    }


    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) throws IOException {
        clients.remove(userId);
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session,@PathParam("userId") String userId) throws Exception {
        log.info("message:"+message);
        //实时的回复推送
        Message msg = JSON.parseObject(message, Message.class);
        Session receiver =null;
        for (String id : clients.keySet()) {
            if (msg.getReceiverId().equals(id)) {
                receiver = clients.get(id);
            }
        }
        //用户在线就把信息发送给他
        if (Objects.nonNull(receiver)) {
            session.getBasicRemote().sendObject(msg);
        }
        Chat chat =new Chat();
        chat.setSenderId(Long.parseLong(msg.getSenderId()));
        chat.setReceiverId(Long.parseLong(msg.getReceiverId()));
        chat.setContext(msg.getContext());
        chat.setImg(msg.getImg());
        chat.setType(1);
        chatService.save(chat);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error,@PathParam("userId") String userId) {
        System.out.println("发生错误！ID：" + session.getId());
        error.printStackTrace();
    }

    @EventListener
    public void sendMessage(MessageEvent<ReplyAction> messageEvent) throws EncodeException, IOException {
        ReplyAction content = messageEvent.getContent();
        Long receiverId = content.getReceiverId();
        Session receiver=null;
        for (String s : clients.keySet()) {
            if (receiverId.toString().equals(s)) {
                receiver = clients.get(s);
            }
        }
        if (Objects.nonNull(receiver)) {
            //用户在线，实时推送消息
            receiver.getBasicRemote().sendObject(Result.ok("推送content", content));
        }
        else{
            log.error("目标用户不在线");
        }
    }

}