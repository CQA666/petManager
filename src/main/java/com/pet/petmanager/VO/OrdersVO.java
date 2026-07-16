package com.pet.petmanager.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.pet.petmanager.entity.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersVO extends Orders {

    /**
     * 用户名（非数据库字段，用于前端展示）
     */
    @TableField(exist = false)
    private String username;

    /**
     * 商品名称（非数据库字段，用于关联查询展示）
     */
    @TableField(exist = false)
    private String goodsName;

    /**
     * 订单总金额（非数据库字段，通常由商品单价 × 数量计算得出）
     */
    @TableField(exist = false)
    private BigDecimal totalAmount;
}
