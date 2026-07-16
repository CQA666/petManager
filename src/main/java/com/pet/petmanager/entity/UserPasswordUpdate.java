package com.pet.petmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordUpdate {
    // 旧密码
    private String oldPassword;
    // 新密码
    private String newPassword;
}
