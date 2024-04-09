package com.xs.mapper;

import com.xs.domain.Chat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 电脑
* @description 针对表【chat】的数据库操作Mapper
* @createDate 2024-03-17 14:12:39
* @Entity com.xs.domain.Chat
*/
@Mapper
public interface ChatMapper extends BaseMapper<Chat> {

}




