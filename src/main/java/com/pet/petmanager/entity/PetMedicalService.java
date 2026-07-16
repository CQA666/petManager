package com.pet.petmanager.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 宠物医疗服务实体类，对应数据库表 pet_medical_services
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("pet_medical_services")
public class PetMedicalService {

    /**
     * 服务的唯一标识符，主键，自动递增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 宠物的名称，不允许为空
     */
    private String petName;

    /**
     * 预约服务的用户名（宠物主人），不允许为空
     */
    private String username;

    /**
     * 医疗服务的类型（如：疫苗接种、绝育、体检等），不允许为空
     */
    private String type;

    /**
     * 服务预约或执行的日期，不允许为空
     */
    private LocalDate date;

    /**
     * 服务的详细描述信息（可选）
     */
    private String description;

    /**
     * 记录创建的日期（仅日期部分），插入后不可更新
     */
    private LocalDate createdAt;

    /**
     * 服务当前状态（如：已预约、已完成、已取消等）
     */
    private String status;
}