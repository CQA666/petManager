package com.pet.petmanager.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("pet_ai_chat")
public class PetAiChat {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer petId;
    private String message;
    private String response;
    private LocalDateTime createdAt;
}
