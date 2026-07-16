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
@TableName("adopt")
public class Adopt {

    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 宠物id
     */
    private Integer animalId;

    /**
     * 领养时间
     */
    private String time;

    /**
     * 领养状态
     */
    private String status;

    /**
     * 审核意见
     */
    private String reviewComment;

    /**
     * 审核人ID
     */
    private Integer reviewerId;

    /**
     * 审核时间
     */
    private String reviewTime;
}
