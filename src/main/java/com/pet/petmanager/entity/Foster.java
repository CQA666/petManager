package com.pet.petmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 寄养记录实体类，对应数据库表 foster
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("foster")
public class Foster {

    /** 主键 ID，自增 */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 用户 ID，关联用户表 */
    private Integer userId;

    /** 宠物昵称 */
    private String name;

    /** 寄养时间（格式如：2025-11-26） */
    private String time;

    /** 寄养天数 */
    private Integer days;

    /** 寄养状态（如：pending, confirmed, completed, cancelled 等） */
    private String status;

    /** 寄养房间 ID，关联房间表 */
    private Integer roomId;


}
