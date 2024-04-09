package com.xs.controller;

import com.xs.common.Result;
import com.xs.domain.Mv;
import com.xs.service.MvService;
import org.simpleframework.xml.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mv")
public class MVController {

    @Autowired
    private MvService mvService;

    @PostMapping("/add")
    public Result addMV(@RequestBody Mv mv) {
        boolean save = mvService.save(mv);
        if (save) {
            return Result.ok("添加MV成功");
        }
        return Result.error("添加失败");
    }

    @GetMapping("/getAll")
    public Result getMv() {
        List<Mv> list = mvService.list();
        return Result.ok("获取所有Mv成功", list);
    }

    @GetMapping("getAllByPage/{currentPage}")
    public Result getMvByPage(@PathVariable Integer currentPage) {
        return mvService.getMyByPage(currentPage);
    }

    @GetMapping("/delete/{id}")
    public Result deleteMv(@PathVariable Long id) {
        boolean b = mvService.removeById(id);
        if (b) {
            return Result.ok("删除成功");
        }
        return Result.error("删除失败");
    }

    @PostMapping("/update")
    public Result updateMV(@RequestBody Mv mv) {
        boolean b = mvService.updateById(mv);
        if (b) {
            return Result.ok("更新成功");
        }
        return Result.error("更新失败");
    }
}
