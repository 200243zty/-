package com.xs.controller;

import com.xs.common.Result;
import com.xs.domain.Song;
import com.xs.service.ConsumerSongOperationService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecommendControllerTest {

    @Autowired
    private ConsumerSongOperationService consumerSongOperationService;

    @Test
    public void testRecommend() throws TasteException {
        List<Song> songs = consumerSongOperationService.recommendItems("1");
        System.out.println(songs);

    }
}