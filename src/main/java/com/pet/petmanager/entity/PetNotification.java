package com.pet.petmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户通知实体类，对应数据库表 pet_notification
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("pet_notification")
public class PetNotification {

    /** 通知的唯一标识，自增主键 */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 关联的用户ID */
    private Integer userId;

    /** 推送给用户的消息内容 */
    private String message;

    /** 消息创建的时间戳 */
    private LocalDateTime timestamp;

    /** 通知状态（例如：unread / read） */
    private String status;

    /** 用户名（非数据库字段，用于关联查询展示） */
    @TableField(exist = false)
    private String username;
}