package com.attack.controller;


import com.attack.common.QueryPageParam;
import com.attack.common.Result;
import com.attack.entity.Node;
import com.attack.service.NodeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/node")
public class NodeController {

    @Autowired
    private NodeService nodeService;
    @PostMapping("/save")
    public Result save(@RequestBody Node node){
        return nodeService.save(node)?Result.suc():Result.fail();
    }
    
    @PostMapping("/update")
    public Result update(@RequestBody Node node){
        return nodeService.updateById(node)?Result.suc():Result.fail();
    }
    
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return nodeService.removeById(id)?Result.suc():Result.fail();
    }

    @PostMapping("/saveOrMod")
    public Result saveOrUpdate(@RequestBody Node node){
        return nodeService.saveOrUpdate(node)?Result.suc():Result.fail();
    }

    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String name){
        List list=nodeService.lambdaQuery().eq(Node::getName,name).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param=query.getParam();
        String name=(String) param.get("name");
        Page<Node> page=new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Node> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Node::getName,name);
        }

        IPage result=nodeService.pageCC(page,lambdaQueryWrapper);
        return Result.suc(result.getRecords(), result.getTotal());
    }
}
