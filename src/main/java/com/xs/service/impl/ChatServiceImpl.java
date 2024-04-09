package com.xs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.domain.Chat;
import com.xs.service.ChatService;
import com.xs.mapper.ChatMapper;
import org.springframework.stereotype.Service;

/**
* @author 电脑
* @description 针对表【chat】的数据库操作Service实现
* @createDate 2024-03-17 14:12:39
*/
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat>
    implements ChatService{

}




