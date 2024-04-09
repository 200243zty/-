package com.xs.mapper;

import com.xs.domain.ConsumerSongOperation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 电脑
* @description 针对表【consumer_song_operation】的数据库操作Mapper
* @createDate 2024-03-03 13:40:09
* @Entity com.xs.domain.ConsumerSongOperation
*/
@Mapper
public interface ConsumerSongOperationMapper extends BaseMapper<ConsumerSongOperation> {

    List<ConsumerSongOperation> getAllUserPreference();
}




