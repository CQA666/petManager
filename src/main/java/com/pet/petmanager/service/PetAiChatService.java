package com.pet.petmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.PetAiChat;
import com.pet.petmanager.utils.Result;

public interface PetAiChatService extends IService<PetAiChat> {
    Result chat(Integer userId, Integer petId, String message);
}
