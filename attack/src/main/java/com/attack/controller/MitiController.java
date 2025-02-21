package com.attack.controller;


import com.attack.common.QueryPageParam;
import com.attack.common.Result;
import com.attack.entity.Miti;
import com.attack.service.MitiService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/miti")
public class MitiController {

    @Autowired
    private MitiService mitiService;
    @PostMapping("/save")
    public Result save(@RequestBody Miti miti){
        return mitiService.save(miti)?Result.suc():Result.fail();
    }
    
    @PostMapping("/update")
    public Result update(@RequestBody Miti miti){
        return mitiService.updateById(miti)?Result.suc():Result.fail();
    }
    
    @GetMapping("/del")
    public Result del(@RequestParam String no){
        return mitiService.removeById(no)?Result.suc():Result.fail();
    }

    @PostMapping("/saveOrMod")
    public Result saveOrUpdate(@RequestBody Miti miti){
        return mitiService.saveOrUpdate(miti)?Result.suc():Result.fail();
    }

    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no){
        List list=mitiService.lambdaQuery().eq(Miti::getNo,no).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param=query.getParam();
        String no=(String) param.get("no");
        Page<Miti> page=new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Miti> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(no) && !"null".equals(no)){
            lambdaQueryWrapper.eq(Miti::getNo,no);
        }

        IPage result=mitiService.pageCC(page,lambdaQueryWrapper);
        return Result.suc(result.getRecords(), result.getTotal());
    }
}
