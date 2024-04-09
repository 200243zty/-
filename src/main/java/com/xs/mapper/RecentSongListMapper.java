package com.xs.mapper;

import com.xs.domain.RecentSongList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 电脑
* @description 针对表【recent_song_list】的数据库操作Mapper
* @createDate 2024-03-04 13:23:26
* @Entity com.xs.domain.RecentSongList
*/
@Mapper
public interface RecentSongListMapper extends BaseMapper<RecentSongList> {


    List<RecentSongList> getRecentSongListByUserId(Long id);
}




