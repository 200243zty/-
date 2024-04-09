package com.xs.service;

import com.xs.common.Result;
import com.xs.domain.ReplyAction;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 电脑
* @description 针对表【reply_action】的数据库操作Service
* @createDate 2024-03-07 15:55:20
*/
public interface ReplyActionService extends IService<ReplyAction> {

    Result getMyReplyUnviewed(String userId);
}
