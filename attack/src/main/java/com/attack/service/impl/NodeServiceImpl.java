package com.attack.service.impl;

import com.attack.entity.Node;
import com.attack.mapper.NodeMapper;
import com.attack.service.NodeService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node> implements NodeService {
    @Autowired
    private NodeMapper nodeMapper;
    public IPage pageCC(IPage<Node> page, Wrapper wrapper) {
        return nodeMapper.pageCC(page,wrapper);
    }
}
