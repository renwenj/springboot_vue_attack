package com.attack.service.impl;

import com.attack.entity.Miti;
import com.attack.mapper.MitiMapper;
import com.attack.service.MitiService;
import com.attack.service.TechService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MitiServiceImpl extends ServiceImpl<MitiMapper, Miti> implements MitiService {
    @Autowired
    private MitiMapper mitiMapper;
    public IPage pageCC(IPage<Miti> page, Wrapper wrapper) {
        return mitiMapper.pageCC(page,wrapper);
    }
}
