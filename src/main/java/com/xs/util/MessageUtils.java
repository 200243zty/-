package com.xs.util;

import com.alibaba.fastjson.JSON;
import com.xs.domain.message.ResultMessage;

public class MessageUtils {
    public static String getMessage(boolean isSystemMessage, String fromName, Object message) {
        ResultMessage result = new ResultMessage();
        result.setSystem(isSystemMessage);
        result.setMessage(message);
        if (fromName != null) {
            result.setFromName(fromName);
        }
        return JSON.toJSONString(result);
    }
}
