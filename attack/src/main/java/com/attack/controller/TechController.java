package com.attack.controller;


import com.attack.common.QueryPageParam;
import com.attack.common.Result;
import com.attack.entity.Tech;
import com.attack.entity.User;
import com.attack.service.TechService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/tech")
public class TechController {

    @Autowired
    private TechService techService;
    @PostMapping("/save")
    public Result save(@RequestBody Tech tech){
        return techService.save(tech)?Result.suc():Result.fail();
    }
    
    @PostMapping("/update")
    public Result update(@RequestBody Tech tech){
        return techService.updateById(tech)?Result.suc():Result.fail();
    }
    
    @GetMapping("/del")
    public Result del(@RequestParam String no){
        return techService.removeById(no)?Result.suc():Result.fail();
    }

    @PostMapping("/saveOrMod")
    public Result saveOrUpdate(@RequestBody Tech tech){
        return techService.saveOrUpdate(tech)?Result.suc():Result.fail();
    }

    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no){
        List list=techService.lambdaQuery().eq(Tech::getNo,no).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param=query.getParam();
        String no=(String) param.get("no");
        Page<Tech> page=new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Tech> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(no) && !"null".equals(no)){
            lambdaQueryWrapper.eq(Tech::getNo,no);
        }

        //IPage result=userService.pageC(page);
        IPage result=techService.pageCC(page,lambdaQueryWrapper);
        return Result.suc(result.getRecords(), result.getTotal());
    }
}
