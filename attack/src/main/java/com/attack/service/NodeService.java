package com.attack.service;

import com.attack.entity.Node;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface NodeService extends IService<Node> {
    IPage pageCC(IPage<Node> page, Wrapper wrapper);
}
