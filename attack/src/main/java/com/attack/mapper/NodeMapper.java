package com.attack.mapper;

import com.attack.entity.Node;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NodeMapper extends BaseMapper<Node> {
    IPage pageCC(IPage<Node> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
