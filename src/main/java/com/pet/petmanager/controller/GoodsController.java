package com.pet.petmanager.controller;


import com.pet.petmanager.entity.Goods;
import com.pet.petmanager.service.GoodsService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/page")
    public Result getGoodsByPage(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size) {
        //调用service层方法获取商品列表数据
        return goodsService.getGoodsByPage(name, currentPage, size);
    }
    @RequestMapping("/save")
    public Result saveGoods(@RequestBody Goods goods){
        return goodsService.saveGoods(goods);
    }

    @RequestMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam List<Integer> ids) {
        return goodsService.deleteBatchGoods(ids);
    }

    @RequestMapping("/update")
    public Result updateById(@RequestBody Goods goods) {
        return goodsService.updateGoods(goods);
    }

    /**
     * 获取所有商品信息
     * @return
     */
    @RequestMapping("/selectAll")
    public Result selectAll() {
        return goodsService.selectAllGoods();
    }
}
