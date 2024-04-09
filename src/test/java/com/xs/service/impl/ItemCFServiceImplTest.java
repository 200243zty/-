package com.xs.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ItemCFServiceImplTest {

    @Autowired
    private ItemCFServiceImpl itemCFService;

//    @Test
//    public void test() {
//        Map<String, Double> recommend = itemCFService.recommend("2");
//        System.out.println(recommend.toString());
//    }

}