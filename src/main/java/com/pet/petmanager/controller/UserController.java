package com.pet.petmanager.controller;

import com.pet.petmanager.entity.User;
import com.pet.petmanager.entity.UserPasswordUpdate;
import com.pet.petmanager.service.UserService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //登录功能
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        return userService.login(user);
    }

    //注册功能
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        return userService.register(user);
    }

    //提交用户信息
    @PutMapping("/{id}")
    public Result updateUser(@PathVariable Integer id,@RequestBody User user){
        return userService.updateUser(id,user);
    }

    //忘记密码
    @GetMapping("/forget")
    public Result forgetPassword(@RequestParam(defaultValue = "") String email,
                                 @RequestParam(defaultValue = "") String newPassword){
        return userService.forgetPassword(email,newPassword);

    }

    //修改密码
    @PutMapping("password/{id}")
    public Result changePassword(@PathVariable Integer id,
                                 @RequestBody UserPasswordUpdate userPasswordUpdate){
        return userService.changePassword(id,userPasswordUpdate);
    }
    @RequestMapping("/page")
    public Result getUsersByPage(
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String sex,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String role,
            @RequestParam(defaultValue = "") String currentRole,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size) {
        return userService.getUsersByPage(username, sex, name, role, currentRole, currentPage, size);
    }

    /**
     * 批量删除用户（管理员）
     */
    @RequestMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam List<Integer> ids) {
        return userService.deleteBatch(ids);
    }

    /**
     * 搜索用户（根据用户名或姓名）
     */
    @GetMapping("/search")
    public Result searchUsers(@RequestParam(required = false) String username,
                              @RequestParam(defaultValue = "10") Integer limit) {
        return userService.searchUsersByUsernameOrName(username, limit);
    }

    /**
     * 为用户充值余额
     */
    @RequestMapping("/recharge")
    public Result rechargeUserBalance(@RequestBody Map<String, Object> params) {
        return userService.rechargeBalance(params);
    }

    @PostMapping("")
    public Result saveUser(@RequestBody User user){
       return userService.register(user);

    }


}
