package com.pet.petmanager.controller;


import com.pet.petmanager.VO.AdoptVO;
import com.pet.petmanager.service.AdoptService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adopt")
public class AdoptController {

    @Autowired
    private AdoptService adoptService;

    //领养分页查询
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "") String name,
                             @RequestParam(defaultValue = "1")Integer currentPage,
                             @RequestParam(defaultValue = "10")Integer size){
        return adoptService.selectPage(name,currentPage,size);

    }

    @PutMapping("update")
    public Result updateAdopt(@RequestBody AdoptVO adoptVO){
        return adoptService.updateAdopt(adoptVO);
    }

    //批量删除功能
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam List<Integer> ids){
        //直接调用service层进行根据id批量删除数据
        boolean b = adoptService.removeBatchByIds(ids);
        //判断是否删除成功
        if (b){
            return Result.success();
        }
        return Result.error("-1","删除失败");
    }


}
