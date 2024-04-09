package com.xs.controller;

import com.xs.common.Result;
import com.xs.domain.ConsumerSongOperation;
import com.xs.service.ConsumerSongOperationService;
import com.xs.vo.ConsumerSongVo;
import com.xs.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/songAction")
@Slf4j
@RestController
public class ConsumerSongOperationController {

    @Resource
    private ConsumerSongOperationService consumerSongOperationService;

    @GetMapping("/test")
    public String test(){
        return "123";
    }

    @PostMapping("/doplay")
    public Result doPlay(@RequestBody ConsumerSongVo consumerSongVo) {
        ConsumerSongOperation consumerSongOperation = new ConsumerSongOperation();
        BeanUtils.copyProperties(consumerSongVo, consumerSongOperation);
        //完播
        consumerSongOperation.setOperationType(0);
        boolean save = consumerSongOperationService.save(consumerSongOperation);
        if (save) {
            return Result.ok("记录用户完播成功");
        }
        return Result.ok("不能重复记录");
    }

    @PostMapping("/doCollect")
    public Result doCollect(@RequestBody ConsumerSongVo consumerSongVo) {
        log.warn(consumerSongVo.toString());
        ConsumerSongOperation consumerSongOperation = new ConsumerSongOperation();
        BeanUtils.copyProperties(consumerSongVo, consumerSongOperation);
        //收藏
        consumerSongOperation.setOperationType(1);
        consumerSongOperationService.save(consumerSongOperation);
        return Result.ok("记录用户收藏成功");
    }

    @PostMapping("/doComment")
    public Result doComment(@RequestBody ConsumerSongVo consumerSongVo) {
        log.warn(consumerSongVo.toString());

        ConsumerSongOperation consumerSongOperation = new ConsumerSongOperation();
        BeanUtils.copyProperties(consumerSongVo, consumerSongOperation);
        //评论
        consumerSongOperation.setOperationType(2);
        consumerSongOperationService.save(consumerSongOperation);
        return Result.ok("记录用户评论成功");
    }
}
