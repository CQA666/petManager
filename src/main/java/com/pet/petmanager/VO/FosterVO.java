package com.pet.petmanager.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.pet.petmanager.entity.Foster;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FosterVO extends Foster {

    /** 用户名（非数据库字段，用于展示） */
    @TableField(exist = false)
    private String userName;

    /** 房间名称（非数据库字段，用于展示） */
    @TableField(exist = false)
    private String roomName;
}