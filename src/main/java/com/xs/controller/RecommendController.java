package com.xs.controller;

import com.xs.common.Result;
import com.xs.domain.Song;
import com.xs.service.ConsumerSongOperationService;
import com.xs.service.RecommendService;
import org.apache.mahout.cf.taste.common.TasteException;
import org.simpleframework.xml.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recommend")
public class RecommendController {

    //推荐的歌曲数
    @Value("${recommend.num}")
    private Integer recommendNum;

    @Autowired
    private RecommendService recommendService;

    @Autowired
    private ConsumerSongOperationService consumerSongOperationService;


    @GetMapping("/byUserCF/{userId}")
    public Result recommendSongByUserCf(@PathVariable String userId) throws TasteException {
        List<Song> list = consumerSongOperationService.recommendItems(userId);
        return Result.ok("用户协同过滤推荐成功", list);
    }

    @GetMapping("/byItemCF/{userId}")
    public Result recommendSongByItemCf(@PathVariable String userId) {
        List<Song> recommendSongs = recommendService.recommend(userId);
        return Result.ok("基于物品的协同过滤成功",recommendSongs);
    }


}
