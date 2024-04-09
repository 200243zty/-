package com.xs.controller;

import com.xs.domain.ConsumerSongOperation;
import com.xs.mapper.ConsumerSongOperationMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityContextHolderTest {

    @Resource
    private ConsumerSongOperationMapper consumerSongOperationMapper;

    @Test
    public void test(){
        List<ConsumerSongOperation> allUserPreference = consumerSongOperationMapper.getAllUserPreference();
        System.out.println(allUserPreference);
    }
}
