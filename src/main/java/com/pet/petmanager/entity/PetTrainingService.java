package com.pet.petmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 宠物训练服务实体类，对应数据库表 pet_training_services
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("pet_training_services")
public class PetTrainingService {

    /**
     * 服务的唯一标识符，主键，自动递增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 宠物名称
     */
    private String petName;

    /**
     * 训练服务的类型（如：基础服从、敏捷训练、行为矫正等）
     */
    private String serviceType;

    /**
     * 训练服务预计结束的日期，可以为空
     */
    private LocalDate endDate;

    /**
     * 训练的级别（如：初级、中级、高级）
     */
    private String trainingLevel;

    /**
     * 训练的目标或期望结果（例如：学会坐下、停止吠叫等）
     */
    private String trainingGoals;

    /**
     * 训练完成的状态，如“完成”、“进行中”、“未开始”等
     */
    private String completionStatus;

    /**
     * 用户姓名（关联的宠物主人姓名）
     */
    private String userName;
}
