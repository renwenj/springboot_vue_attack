package com.attack.service;

import com.attack.entity.Tech;
import com.attack.entity.User;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface TechService extends IService<Tech> {
    IPage pageCC(IPage<Tech> page, Wrapper wrapper);
}
