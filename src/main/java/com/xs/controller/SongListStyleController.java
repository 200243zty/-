package com.xs.controller;

import com.xs.common.Result;
import com.xs.domain.SongListStyle;
import com.xs.service.SongListStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class SongListStyleController {
    @Autowired
    private SongListStyleService songListStyleService;

    @GetMapping("admin/songListStyle/all")
    public Result getAllSongListStyle() {
        List<SongListStyle> list = songListStyleService.list();
        return Result.ok("获取所有歌单风格成功", list);
    }
    @GetMapping("/songListStyle/all")
    public Result getAllSongListStyle2() {
        List<SongListStyle> list = songListStyleService.list();
        return Result.ok("获取所有歌单风格成功", list);
    }

}
