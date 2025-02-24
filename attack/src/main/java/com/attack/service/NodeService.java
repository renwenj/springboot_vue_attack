package com.attack.service;

import com.attack.entity.Node;
import com.attack.entity.User;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface NodeService extends IService<Node> {

    List<Node> listAll();
    IPage pageCC(IPage<Node> page, Wrapper wrapper);
}
