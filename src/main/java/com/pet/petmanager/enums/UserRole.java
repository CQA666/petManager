package com.pet.petmanager.enums;



public enum UserRole {
    // 枚举常量：角色实例
    ADMIN(1, "ADMIN", "管理员"),        // 普通管理员
    USER(2, "USER", "用户"),            // 普通用户
    SUPER_ADMIN(3, "SUPER_ADMIN", "超级管理员");  // 超级管理员

    // 角色编码（数字标识）
    private final Integer id;
    // 角色英文标识
    private final String value;
    // 角色中文名称
    private final String name;

    /**
     * 枚举私有构造方法
     *
     * @param id    角色数字编码
     * @param value 角色英文标识
     * @param name  角色中文名称
     */
    UserRole(Integer id, String value, String name) {
        this.id = id;
        this.value = value;
        this.name = name;
    }

    // 可以添加getter方法来访问枚举的属性
    public Integer getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
