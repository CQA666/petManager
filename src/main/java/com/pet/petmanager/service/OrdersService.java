package com.pet.petmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.Orders;
import com.pet.petmanager.utils.Result;

import java.util.List;

public interface OrdersService extends IService<Orders> {
    Result selectPage(String orderNo, String username, String goodsName, String userRole, Integer userId, Integer currentPage, Integer size);

    Result deleteBatchOrders(List<Integer> ids);

    Result updateOrder(Orders order);
}
