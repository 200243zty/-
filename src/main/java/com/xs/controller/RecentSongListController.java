package com.xs.controller;

import com.xs.common.Result;
import com.xs.domain.RecentSong;
import com.xs.domain.RecentSongList;
import com.xs.service.RecentSongListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recentSongList")
public class RecentSongListController {
    @Autowired
    private RecentSongListService recentSongListService;

    /**
     * 歌曲添加到最近播放的歌曲列表中，如果改歌单之前就存在，那就更改他被播放的次数和最近一次播放的时间
     *
     * @param recentSongList
     * @return
     */
    @PostMapping("/add")
    public Result addRecentSongList(@RequestBody RecentSongList recentSongList) {
        return recentSongListService.addRecentSongList(recentSongList);
    }

    /**
     * 先按照时间，再按照播放量倒叙排序取前15个歌单
     * @param id
     * @return
     */
    @GetMapping("/recentSongListOfUserId/{id}")
    public Result getRecentSongListByUserId(@PathVariable Long id) {
        return recentSongListService.getRecentSongListByUserId(id);
    }
}
