package com.pet.petmanager.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 医疗服务类型实体类，用于表示宠物可选的医疗服务种类（如疫苗接种、驱虫、绝育等）。
 * 对应数据库表：medical_service_types
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("medical_service_types")
public class MedicalServiceType {

    /**
     * 服务类型的唯一标识符，主键，自动递增。
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 医疗服务类型的名称（如"疫苗接种"、"体检"），不能为空。
     */
    private String name;

    /**
     * 对该医疗服务类型的详细描述（可选），用于向用户说明服务内容。
     */
    private String description;
}
