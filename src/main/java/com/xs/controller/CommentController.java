package com.xs.controller;

import com.xs.common.Result;
import com.xs.domain.Comment;
import com.xs.domain.Consumer;
import com.xs.dto.CommentDto;
import com.xs.service.CommentService;
import com.xs.service.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {

    @Resource
    private CommentService commentService;

    @Autowired
    private ConsumerService consumerService;
    /**
     * 添加评论
     */
    @PostMapping("/add")
    public Result addComment(@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

//    /**
//     * TODO 哪个sb写的接口，修改nm 删了不就行了
//     */
//    @PutMapping("/update")
//    public Result updateComment(@RequestBody Comment comment) {
//        return commentService.updateComment(comment);
//    }

    /**
     * 删除评论
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }

    /**
     * 查询所有评论
     */
    @GetMapping("/all")
    public Result getAllComment() {
        return commentService.getAllComment();
    }

    /**
     * 查询指定歌单所有评论
     */
    @GetMapping("/commentOfSongListId/{id}")
    public Result getAllCommentBySongListId(@PathVariable Long id) {
        //先查出所有的Rootcomment，然后在组装
        //这种不用递归的做法，由前端保证所有评论都只显示两级
        List<Comment> comments = commentService.getAllCommentBySongListId(id);
        List<Comment> rootComments =comments.stream().filter(comment -> comment.getPid()==null).collect(Collectors.toList());
        for (Comment rootComment : rootComments) {
            rootComment.setChildren(comments.stream().
                    filter(comment -> rootComment.getId().equals(comment.getPid())).collect(Collectors.toList()));
        }
        for (Comment rootComment : rootComments) {
            Long userId = rootComment.getUserId();
            Consumer byId = consumerService.getById(userId);
            String avatar = byId.getAvatar();
            rootComment.setAvatar(avatar);
            rootComment.setUserName(byId.getUsername());
        }
        for (Comment rootComment : rootComments) {
            List<Comment> childrens = rootComment.getChildren();
            for (Comment childrenComment : childrens) {
                Long userId = childrenComment.getUserId();
                Consumer byId = consumerService.getById(userId);
                childrenComment.setAvatar(byId.getAvatar());
                childrenComment.setUserName(byId.getUsername());
                //获取被回复对象的名字
                Long target = childrenComment.getTarget();
                Consumer targetUser = consumerService.getById(target);
                log.error("targetUser:"+targetUser.getUsername());
                childrenComment.setTargetName(targetUser.getUsername());
            }
        }
        return Result.ok("查询成功", rootComments);
    }

    /**
     * 查询指定歌曲所有评论
     */
    @GetMapping("/commentOfSongId/{id}")
    public Result getAllCommentBySongId(@PathVariable Long id) {
        //同上的做法
        List<Comment> comments = commentService.getAllCommentBySongId(id);
        List<Comment> rootComments =comments.stream().filter(comment -> comment.getPid()==null).collect(Collectors.toList());
        for (Comment rootComment : rootComments) {
            rootComment.setChildren(comments.stream().
                    filter(comment -> rootComment.getId().equals(comment.getPid())).collect(Collectors.toList()));
        }
        for (Comment rootComment : rootComments) {
            Long userId = rootComment.getUserId();
            Consumer byId = consumerService.getById(userId);
            String avatar = byId.getAvatar();
            rootComment.setAvatar(avatar);
            rootComment.setUserName(byId.getUsername());
        }
        for (Comment rootComment : rootComments) {
            List<Comment> childrens = rootComment.getChildren();
            for (Comment childrenComment : childrens) {
                Long userId = childrenComment.getUserId();
                Consumer byId = consumerService.getById(userId);
                childrenComment.setAvatar(byId.getAvatar());
                childrenComment.setUserName(byId.getUsername());
                //获取被回复对象的名字
                Long target = childrenComment.getTarget();
                Consumer targetUser = consumerService.getById(target);
                log.error("targetUser:"+targetUser.getUsername());
                childrenComment.setTargetName(targetUser.getUsername());
            }
        }
        return Result.ok("查询成功", rootComments);
    }

    /**
     * 给指定评论点赞
     */
    @PutMapping("/updateCommentUp")
    public Result updateCommentUp(@RequestBody CommentDto commentDto) {
        return commentService.updateCommentUp(commentDto);
    }

    /**
     * 取消点赞
     */
    @PutMapping("/cancelCommentUp")
    public Result cancelCommentUp(@RequestBody CommentDto commentDto) {
        return commentService.cancelCommentUp(commentDto);
    }
}
