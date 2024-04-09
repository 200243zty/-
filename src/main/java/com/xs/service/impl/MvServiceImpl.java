package com.xs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xs.common.Result;
import com.xs.domain.Mv;
import com.xs.service.MvService;
import com.xs.mapper.MvMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 电脑
* @description 针对表【mv】的数据库操作Service实现
* @createDate 2024-03-16 15:49:41
*/
@Service
public class MvServiceImpl extends ServiceImpl<MvMapper, Mv> implements MvService{

    @Autowired
    private MvMapper mvMapper;

    @Override
    public Result getMyByPage(Integer currentPage) {
        IPage<Mv> page = new Page<>(currentPage, 10);
        LambdaQueryWrapper<Mv> lqw =new LambdaQueryWrapper<>();
        IPage<Mv> mvIPage = mvMapper.selectPage(page, lqw);
        List<Mv> records = mvIPage.getRecords();
        if (!records.isEmpty()) {
            return Result.ok("查询成功", records);
        }
        return Result.error("查询失败");
    }
}




