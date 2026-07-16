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
@TableName("submit_img")
public class SubmitImg {
    /**
     * 图片id
     */
    @TableId(type= IdType.AUTO)
    Integer id;
    /**
     * 上报id
     */
    Integer submitId;
    /**
     * 图片路径
     */
    String url;
}

