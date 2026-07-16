package com.pet.petmanager.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 字典表项实体类，对应数据库表 dict_item
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("dict_item")
public class IconDict {

    /**
     * 主键 ID，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 字典项的键（例如：pet_status、adopt_result 等）
     */
    private String itemKey;

    /**
     * 字典项的值（例如："待领养"、"审核通过" 等）
     */
    private String itemValue;

    /**
     * 字典项的描述信息，用于说明该键值对的用途或含义
     */
    private String description;

    /**
     * 创建时间，格式化为 yyyy-MM-dd
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;

    /**
     * 更新时间，格式化为 yyyy-MM-dd
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private LocalDateTime updateTime;
}