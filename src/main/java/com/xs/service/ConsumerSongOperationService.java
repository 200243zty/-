package com.xs.service;

import com.xs.domain.ConsumerSongOperation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xs.domain.Song;
import org.apache.mahout.cf.taste.common.TasteException;

import java.util.List;

/**
* @author 电脑
* @description 针对表【consumer_song_operation】的数据库操作Service
* @createDate 2024-03-03 13:40:09
*/
public interface ConsumerSongOperationService extends IService<ConsumerSongOperation> {

    List<Song> recommendItems(String userId) throws TasteException;
}
