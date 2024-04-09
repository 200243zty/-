package com.xs.controller;

import com.xs.common.Result;
import com.xs.service.SongListService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class SongListControllerTest {
    @Resource
    private SongListService songListService;

    @Test
    void getSongListByPage() {

    }

    @Test
    void getAllSongList() {
        Result allSongList = songListService.getAllSongList();
        Object data = allSongList.getData();
        System.out.println(data.toString());
    }

    @Test
    void getSongListByName() {
    }

    @Test
    void addSongList() {
    }

    @Test
    void updateSongList() {
    }

    @Test
    void deleteSongList() {
    }

    @Test
    void deleteAllSongList() {
    }

    @Test
    void getSongListCount() {
    }
}