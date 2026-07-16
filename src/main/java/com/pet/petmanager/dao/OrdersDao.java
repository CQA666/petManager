package com.pet.petmanager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pet.petmanager.VO.OrdersVO;
import com.pet.petmanager.entity.Orders;
import org.apache.ibatis.annotations.Param;

public interface OrdersDao extends BaseMapper<Orders> {
    Page<OrdersVO> selectOrdersByPage(Page<OrdersVO> page, 
                                      @Param("orderNo") String orderNo,
                                      @Param("userId") Integer userId, 
                                      @Param("goodsName") String goodsName, 
                                      @Param("userName") String userName);
}
