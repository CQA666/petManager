package com.pet.petmanager.controller;


import com.pet.petmanager.entity.Orders;
import com.pet.petmanager.service.OrdersService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/page")
    public Result selectPage(@RequestParam(defaultValue = "") String orderNo,
                             @RequestParam(defaultValue = "") String username,
                             @RequestParam(defaultValue = "")String goodsName,
                             @RequestParam(defaultValue = "")String userRole,
                             @RequestParam(defaultValue = "")Integer userId,
                             @RequestParam(defaultValue = "1")Integer currentPage,
                             @RequestParam(defaultValue = "10")Integer size){
       return ordersService.selectPage(orderNo,username,goodsName,userRole,userId,currentPage,size);
    }
    @RequestMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam List<Integer> ids) {
        return ordersService.deleteBatchOrders(ids);
    }
    @RequestMapping("/update")
    public Result updateOrder(@RequestBody Orders order) {
        return ordersService.updateOrder(order);
    }
}
