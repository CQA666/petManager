package com.pet.petmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@TableName("orders")
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    /**
     * 订单的唯一标识符，自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单编号，唯一字符串标识
     */
    private String orderNo;

    /**
     * 关联的商品（货品）ID
     */
    private Integer goodsId;

    /**
     * 商品购买数量
     */
    private Integer num;

    /**
     * 下单用户的ID，外键关联用户表
     */
    private Integer userId;

    /**
     * 订单状态，可取值：
     * - "Completed"：订单已完成
     * - "W_Ship"：待发货（等待物流处理）
     */
    private String status;

    /**
     * 订单创建时间，格式为 yyyy-MM-dd HH:mm:ss（东八区）
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp time;

    /**
     * 订单总金额，保留两位小数
     */
    @TableField(exist = false)
    private BigDecimal totalAmount;


}
