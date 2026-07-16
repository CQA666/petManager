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
@TableName("home_slider")
public class HomeSlider {

    /** 轮播图的唯一标识符（自增主键） */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 轮播图的名称或标题 */
    private String name;

    /** 对轮播图内容的简短描述 */
    private String desrc;

    /** 轮播图的图片链接 */
    private String img;
}