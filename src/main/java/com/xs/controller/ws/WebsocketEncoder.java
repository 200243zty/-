package com.xs.controller.ws;

import com.alibaba.fastjson.JSON;
import com.xs.common.Result;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class WebsocketEncoder implements Encoder.Text<Result> {

    @Override
    public String encode(Result result) throws EncodeException {
        return JSON.toJSONString(result);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
