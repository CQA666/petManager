package com.pet.petmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//使用lombok插件生成对应get,set方法
@Data
//生成无参构造方法
@NoArgsConstructor
//生成有参构造方法
@AllArgsConstructor
@TableName("animal")
public class Animal {

    @TableId(type = IdType.AUTO)
    /** 自增主键 */
    private Integer id;

    /** 宠物头像 */
    private String img;

    /** 宠物昵称 */
    private String name;

    /** 宠物性别 */
    private String sex;

    /** 宠物年龄 */
    private Integer age;

    /** 宠物种类 */
    private String type;

    /** 宠物状态 */
    private String status;

    /** 注册日期 */
    private LocalDate registrationDate;

    /** 行为特征 */
    private String behaviorTraits;

    /** 绝育状况 */
    private String sterilizationStatus;

    /** 颜色 */
    private String color;

    /** 训练状况 */
    private String trainingLevel;

    /** 特殊要求 */
    private String specialNeeds;

    /** 描述信息 */
    private String descr;

    /** 宠物品种 */
    private String breed;
}