package com.attack.mapper;

import com.attack.entity.Miti;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MitiMapper extends BaseMapper<Miti> {
    IPage pageCC(IPage<Miti> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
