package com.pet.petmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.PetNotification;
import com.pet.petmanager.utils.Result;

import java.util.List;

public interface PetNotificationService extends IService<PetNotification> {
    Result getNotificationsByPage(String username, String status, Integer currentPage, Integer size);

    Result createNotificationsForRole(String role, PetNotification notification);

    Result createNotificationForUser(Integer userId, PetNotification notification);

    Result deleteNotificationsBatch(List<Integer> ids);

    Result markNotificationAsRead(Integer id);

    Result getPetNotificationByUserId(Integer userId, String status, Integer currentPage, Integer size);
}
