package com.pet.petmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.dao.PetNotificationDao;
import com.pet.petmanager.dao.UserDao;
import com.pet.petmanager.entity.PetNotification;
import com.pet.petmanager.entity.User;
import com.pet.petmanager.service.PetNotificationService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetNotificationServiceImpl extends ServiceImpl<PetNotificationDao, PetNotification> implements PetNotificationService {

    @Autowired
    private PetNotificationDao petNotificationDao;
    @Autowired
    private UserDao userDao;

    @Override
    public Result getNotificationsByPage(String username, String status, Integer currentPage, Integer size) {
        // 初始化用户ID列表为null
        List<Integer> userIds = null;
        // 如果用户名不为空，先根据用户名查用户ID
        if (StringUtils.isNotBlank(username)) {
            LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
            userWrapper.like(User::getUsername, username);
            userIds = userDao.selectList(userWrapper)
                    .stream()
                    .map(User::getId)
                    .collect(Collectors.toList());
            if (userIds.isEmpty()) {
                return Result.success(new Page<>(currentPage, size));
            }
        }
        // 构建查询条件
        LambdaQueryWrapper<PetNotification> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(userIds != null, PetNotification::getUserId, userIds)
                .eq(StringUtils.isNotBlank(status), PetNotification::getStatus, status)
                .orderByDesc(PetNotification::getTimestamp);
        // 执行分页查询
        Page<PetNotification> page = petNotificationDao.selectPage(
                new Page<>(currentPage, size), wrapper);
        // 遍历填充用户名
        page.getRecords().forEach(notification -> {
            User user = userDao.selectById(notification.getUserId());
            if (user != null) {
                notification.setUsername(user.getUsername());
            }
        });
        return Result.success(page);
    }

    @Override
    public Result createNotificationsForRole(String role, PetNotification notification) {
        if (StringUtils.isBlank(role)) {
            return Result.error("-1", "角色不能为空");
        }
        if (notification == null) {
            return Result.error("-1", "通知内容不能为空");
        }
        // 查询该角色的所有用户
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getRole, role);
        List<User> users = userDao.selectList(userWrapper);
        if (users.isEmpty()) {
            return Result.error("-1", "未找到该角色的用户");
        }
        // 为每个用户创建独立通知
        LocalDateTime now = LocalDateTime.now();
        for (User user : users) {
            PetNotification record = new PetNotification();
            record.setMessage(notification.getMessage());
            record.setUserId(user.getId());
            record.setTimestamp(now);
            record.setStatus("unread");
            petNotificationDao.insert(record);
        }
        return Result.success();
    }

    @Override
    public Result createNotificationForUser(Integer userId, PetNotification notification) {
        if (userId == null || userId <= 0) {
            return Result.error("-1", "用户ID无效");
        }
        if (notification == null || StringUtils.isBlank(notification.getMessage())) {
            return Result.error("-1", "通知消息内容不能为空");
        }
        PetNotification record = new PetNotification();
        record.setUserId(userId);
        record.setMessage(notification.getMessage().trim());
        record.setTimestamp(LocalDateTime.now());
        record.setStatus("unread");
        int inserted = petNotificationDao.insert(record);
        return inserted > 0 ? Result.success() : Result.error("-1", "添加失败，请稍后重试");
    }

    @Override
    public Result deleteNotificationsBatch(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Result.error("-1", "ID列表不能为空");
        }
        int deletedCount = petNotificationDao.deleteBatchIds(ids);
        if (deletedCount > 0) {
            return Result.success("成功删除 " + deletedCount + " 条通知");
        } else {
            return Result.success("未找到可删除的通知");
        }
    }

    @Override
    public Result markNotificationAsRead(Integer id) {
        if (id == null || id <= 0) {
            return Result.error("-1", "通知ID无效");
        }
        PetNotification update = new PetNotification();
        update.setId(id);
        update.setStatus("read");
        int updated = petNotificationDao.updateById(update);
        if (updated > 0) {
            return Result.success();
        } else {
            return Result.error("-1", "通知不存在或已是已读状态");
        }
    }

    @Override
    public Result getPetNotificationByUserId(Integer userId, String status, Integer currentPage, Integer size) {
        LambdaQueryWrapper<PetNotification> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PetNotification::getUserId, userId)
                .eq(StringUtils.isNotBlank(status), PetNotification::getStatus, status)
                .orderByDesc(PetNotification::getTimestamp);
        Page<PetNotification> page = petNotificationDao.selectPage(
                new Page<>(currentPage, size), wrapper);
        // 填充用户名
        page.getRecords().forEach(notification -> {
            User user = userDao.selectById(notification.getUserId());
            if (user != null) {
                notification.setUsername(user.getUsername());
            }
        });
        return Result.success(page);
    }
}