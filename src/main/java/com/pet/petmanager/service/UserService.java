package com.pet.petmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.User;
import com.pet.petmanager.entity.UserPasswordUpdate;
import com.pet.petmanager.utils.Result;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    //用户登录
    Result login(User user);

    //用户注册
    Result register(User user);

    //提交用户信息
    Result updateUser(Integer id,User user);

    //忘记密码
    Result forgetPassword(String email, String newPassword);

    //修改密码
    Result changePassword(Integer id, UserPasswordUpdate userPasswordUpdate);

    Result getUsersByPage(String username, String sex, String name, String role, String currentRole, Integer currentPage, Integer size);

    Result deleteBatch(List<Integer> ids);

    Result searchUsersByUsernameOrName(String username, Integer limit);

    Result rechargeBalance(Map<String, Object> params);
}
