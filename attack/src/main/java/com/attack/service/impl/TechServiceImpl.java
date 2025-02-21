package com.attack.service.impl;

import com.attack.entity.Tech;
import com.attack.entity.User;
import com.attack.mapper.TechMapper;
import com.attack.mapper.UserMapper;
import com.attack.service.TechService;
import com.attack.service.UserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TechServiceImpl extends ServiceImpl<TechMapper, Tech> implements TechService {
    @Autowired
    private TechMapper techMapper;
    public IPage pageCC(IPage<Tech> page, Wrapper wrapper) {
        return techMapper.pageCC(page,wrapper);
    }
}
