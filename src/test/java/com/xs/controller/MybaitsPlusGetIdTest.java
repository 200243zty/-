package com.xs.controller;

import com.xs.domain.Comment;
import com.xs.mapper.CommentMapper;
import com.xs.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MybaitsPlusGetIdTest {

    @Autowired
    private CommentMapper commentMapper;

    @Test
    public void testGetId(){
        Comment comment =new Comment();
        commentMapper.insert(comment);
        System.out.println(comment.getId());

    }

}
