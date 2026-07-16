package com.pet.petmanager.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 训练名称实体类，对应数据库表 training_names
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("training_names")
public class TrainingName {

    /**
     * 训练ID，主键，自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 训练名称
     */
    private String name;

    /**
     * 训练描述
     */
    private String description;
}
