package com.pet.petmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("breed")
public class Breed {

    /**
     * 品种id
     */
    @TableId(type = IdType.AUTO)
    private  Integer breedId;
    /**
     * 品种名称
     */
    private String breedName;
}
