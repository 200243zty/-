package com.xs.controller;

import com.xs.common.Result;
import com.xs.common.cache.Cache;
import com.xs.domain.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @Cache(name = "无参数的Test")
    @GetMapping("/test")
    public void test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication:" + authentication.toString());
    }

    @GetMapping("/test/{id}")
    @Cache(name ="无参数的Test")
    public Result test(@PathVariable Long id) {
        Consumer consumer =new Consumer();
        consumer.setUsername("234");
        consumer.setSex(0);
        return Result.ok("成功", consumer);
    }
}
