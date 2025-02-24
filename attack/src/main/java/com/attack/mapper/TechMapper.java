package com.attack.mapper;

import com.attack.entity.Tech;
import com.attack.entity.User;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface TechMapper extends BaseMapper<Tech> {
    IPage pageCC(IPage<Tech> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}