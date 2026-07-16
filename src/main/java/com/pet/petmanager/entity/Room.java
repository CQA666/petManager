package com.pet.petmanager.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("room")
public class Room {
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 房间名
     */
    private String name;
    /**
     * 房间状态
     */
    private String status;

    /**
     * 宠物昵称
     */
    //fill= FieldFill.UPDATE 表示该字段在更新时自动填充
    @TableField(fill= FieldFill.UPDATE)
    private String animal;
}
