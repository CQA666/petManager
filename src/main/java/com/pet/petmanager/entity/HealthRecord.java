package com.pet.petmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthRecord {

    // 主键ID
    @TableId(type = IdType.AUTO)
    private Long id;

    // 宠物ID
    private Long petId;

    // 记录日期
    private LocalDate recordDate;

    // 体温（单位：摄氏度）
    private Double temperature;

    // 体重（单位：千克）
    private Double weight;

    // 身高（单位：厘米）
    private Double height;

    // 疫苗接种日期
    private LocalDate vaccinationDate;

    // 健康状态（如：良好、一般、异常等）
    private String healthStatus;
}
