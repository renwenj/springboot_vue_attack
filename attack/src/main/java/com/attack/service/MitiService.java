package com.attack.service;

import com.attack.entity.Miti;
import com.attack.entity.Tech;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface MitiService extends IService<Miti> {
    IPage pageCC(IPage<Miti> page, Wrapper wrapper);
}
