package com.xs.service;

import com.xs.common.Result;
import com.xs.domain.RecentSong;
import com.xs.domain.RecentSongList;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 电脑
* @description 针对表【recent_song_list】的数据库操作Service
* @createDate 2024-03-04 13:23:26
*/
public interface RecentSongListService extends IService<RecentSongList> {

    Result addRecentSongList(RecentSongList recentSongList);

    Result getRecentSongListByUserId(Long id);
}
