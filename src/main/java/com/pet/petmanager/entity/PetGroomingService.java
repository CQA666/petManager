package com.pet.petmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 宠物美容服务实体类，对应数据库表 pet_grooming_services
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("pet_grooming_services")
public class PetGroomingService {

    /**
     * 服务的唯一标识符，主键，自动递增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 宠物的名称
     */
    private String petName;

    /**
     * 预约服务的用户名（宠物主人）
     */
    private String username;

    /**
     * 美容服务进行的日期（不包含时间）
     */
    private LocalDate serviceDate;

    /**
     * 美容服务的类型（如：洗澡、修剪、造型等）
     */
    private String serviceType;

    /**
     * 记录创建的日期（仅日期部分，默认由业务逻辑或数据库设置）
     */
    private LocalDate createdAt;

    /**
     * 服务当前状态（如：未开始、服务中、完成）
     */
    private String status;
}
