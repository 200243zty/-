package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.Result;
import com.xs.domain.RecentSong;
import com.xs.domain.RecentSongList;
import com.xs.service.RecentSongListService;
import com.xs.mapper.RecentSongListMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
* @author 电脑
* @description 针对表【recent_song_list】的数据库操作Service实现
* @createDate 2024-03-04 13:23:26
*/
@Service
public class RecentSongListServiceImpl extends ServiceImpl<RecentSongListMapper, RecentSongList>
    implements RecentSongListService{

    @Resource
    private RecentSongListMapper recentSongListMapper;

    @Override
    public Result addRecentSongList(RecentSongList recentSongList) {
        //查找该歌单是否已经再最近播放的歌单列表中
        LambdaQueryWrapper<RecentSongList> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(RecentSongList::getSongListId, recentSongList.getSongListId());
        RecentSongList existRecentSongList = recentSongListMapper.selectOne(queryWrapper);
        if (Objects.nonNull(existRecentSongList)) {
            existRecentSongList.setCount(existRecentSongList.getCount() + 1);
            existRecentSongList.setUpdateTime(LocalDateTime.now());
            int flag = recentSongListMapper.updateById(existRecentSongList);
            if (flag>0) {
                return Result.ok("添加成功");
            }
            return Result.error("添加失败");
        }
        else{
            int insert = recentSongListMapper.insert(existRecentSongList);
            if (insert > 0) {
                return Result.ok("添加成功");
            }
            return Result.error("添加失败");
        }
    }

    @Override
    public Result getRecentSongListByUserId(Long id) {
        List<RecentSongList> recentSongListByUserId= recentSongListMapper.getRecentSongListByUserId(id);
        return Result.ok("查询成功",recentSongListByUserId);
    }
}




