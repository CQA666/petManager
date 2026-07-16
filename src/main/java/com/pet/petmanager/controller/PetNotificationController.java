package com.pet.petmanager.controller;


import com.pet.petmanager.entity.PetNotification;
import com.pet.petmanager.service.PetNotificationService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet-notification")
public class PetNotificationController {

    @Autowired
    private PetNotificationService petNotificationService;

    /**
     * 分页查询（管理员查看所有，支持按用户名和状态筛选）
     */
    @RequestMapping("/page")
    public Result selectPage(
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size) {
        return petNotificationService.getNotificationsByPage(username, status, currentPage, size);
    }

    /**
     * 向指定角色的所有用户发送通知
     */
    @RequestMapping("/role/{role}")
    public Result addForRole(@PathVariable String role,
                             @RequestBody PetNotification notification) {
        return petNotificationService.createNotificationsForRole(role, notification);
    }

    /**
     * 向指定用户发送通知
     */
    @RequestMapping("/user/{userId}")
    public Result addForUser(@PathVariable Integer userId,
                             @RequestBody PetNotification notification) {
        return petNotificationService.createNotificationForUser(userId, notification);
    }

    /**
     * 批量删除通知
     */
    @RequestMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam List<Integer> ids) {
        return petNotificationService.deleteNotificationsBatch(ids);
    }

    /**
     * 标记通知为已读
     */
    @RequestMapping("/markAsRead/{id}")
    public Result markAsRead(@PathVariable Integer id) {
        return petNotificationService.markNotificationAsRead(id);
    }

    /**
     * 根据用户ID获取通知（用户端使用）
     */
    @RequestMapping("/getPage")
    public Result getNotificationsByUserId(
            @RequestParam Integer userId,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size) {
        return petNotificationService.getPetNotificationByUserId(userId, status, currentPage, size);
    }
}
