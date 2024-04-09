package com.xs.domain.message;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 *浏览器发送给服务端websocket的数据
 */
@Data
public class Message {
    private String senderId;

    private String receiverId;

    private String context;

    private String img;
}
