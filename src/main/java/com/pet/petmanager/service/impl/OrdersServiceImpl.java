package com.pet.petmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.VO.OrdersVO;
import com.pet.petmanager.dao.GoodsDao;
import com.pet.petmanager.dao.OrdersDao;
import com.pet.petmanager.entity.Orders;
import com.pet.petmanager.enums.OrderStatus;
import com.pet.petmanager.enums.UserRole;
import com.pet.petmanager.service.OrdersService;
import com.pet.petmanager.utils.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersDao, Orders> implements OrdersService {

    @Autowired
    private OrdersDao ordersDao;

    @Override
    public Result selectPage(String orderNo, String username, String goodsName, String userRole, Integer userId, Integer currentPage, Integer size) {

        //判断是否为管理员
        if(!UserRole.USER.toString().equals(userRole))
            userId=null;
        Page<OrdersVO> ordersVOPage = ordersDao.selectOrdersByPage(new Page<>(currentPage, size), orderNo, userId, goodsName, username);

        return Result.success(ordersVOPage);
    }

    @Override
    public Result deleteBatchOrders(List<Integer> ids) {
        //调用BaseMapper中的deleteBatchIds方法批量删除
        int res = ordersDao.deleteBatchIds(ids);
        //判断是否删除成功
        if (res > 0) {
            //返回成功结果
            return Result.success();
        } else {
            //返回失败结果
            return Result.error("-1", "删除失败");
        }
    }

    @Override
    public Result updateOrder(Orders order) {
        // 根据订单 ID 查询订单
        Orders newOrders = ordersDao.selectById(order.getId());
        // 获取订单状态
        String status = order.getStatus();
        // 判断订单状态是否合法
        if (status == null) {
            return Result.error("-1", "订单状态不能为空");
        }

        try {
            // 将字符串转为枚举，校验是否为合法状态值
            OrderStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            return Result.error("-1", "订单状态错误：" + status);
        }
        // 判断订单是否存在
        if (newOrders == null) {
            return Result.error("-1", "订单不存在");
        }
        // 更新订单状态
        newOrders.setStatus(order.getStatus());
        // 更新订单信息
        int res = ordersDao.updateById(newOrders);
        // 判断是否更新成功
        if (res > 0) {
            // 返回成功结果
            return Result.success(order);
        } else {
            // 返回失败结果
            return Result.error("-1", "更新失败");
        }

    }
}
