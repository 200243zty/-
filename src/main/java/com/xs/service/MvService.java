package com.xs.service;

import com.xs.common.Result;
import com.xs.domain.Mv;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 电脑
* @description 针对表【mv】的数据库操作Service
* @createDate 2024-03-16 15:49:41
*/
public interface MvService extends IService<Mv> {

    Result getMyByPage(Integer currentPage);
}
