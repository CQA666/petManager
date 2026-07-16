package com.pet.petmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("goods")
public class Goods {

    /** 主键ID，自增 */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 商品名称 */
    private String name;

    /** 商品图片路径 */
    private String img;

    /** 商品描述 */
    private String desrc;

    /** 商品价格 */
    private Double price;

    /** 库存数量 */
    private Integer num;
}