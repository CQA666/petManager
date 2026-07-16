package com.pet.petmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 宠物上报表实体类，对应数据库表 submit
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("submit")
public class Submit {

    /**
     * 主键 ID，自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 上报说明
     */
    private String name;

    /**
     * 上报时间（建议格式：yyyy-MM-dd HH:mm:ss）
     */
    private String time;

    /**
     * 处理状态（例如：待处理、已处理、已忽略 等）
     */
    private String status;
}
