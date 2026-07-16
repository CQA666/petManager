package com.pet.petmanager.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//lombok注解，生成构造方法、getter、setter方法、toString方法
@Data
//无参构造方法
@NoArgsConstructor
//有参构造方法
@AllArgsConstructor
//表名
@TableName("user")
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 50, message = "用户名长度必须在3到50个字符之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 20, message = "密码长度必须在6到20个字符之间")
    private String password;

    private String name;

    private String avatar;

    private String role;

    private String sex;

    private String phone;

    // 该注解用于验证字符串是否符合指定的邮箱格式正则表达式
    @Pattern(regexp = "^[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = "邮箱格式不正确")
    private String email;

    private String address;

    private Double account;

    // @TableField(exist = false) 是 MyBatis-Plus 框架中的一个注解，用于标识该字段在数据库表中不存在。这个字段通常是用于在对象中存储一些额外的信息，这些信息并不直接映射到数据库中的某个字段。
    @TableField(exist = false)
    // 声明一个私有的List类型变量menuList，用于保存Menu对象
    private List<Menu> menuList;
}

