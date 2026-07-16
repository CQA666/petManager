package com.pet.petmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.PetGroomingService;
import com.pet.petmanager.utils.Result;

import java.util.List;

public interface PetGroomingServiceService extends IService<PetGroomingService> {
    Result selectPage(String petName, String username, Integer pageNum, Integer pageSize);

    Result deleteBatch(List<Integer> ids);

    Result updatePetGroomingService(PetGroomingService petGroomingService);

    Result savePetGroomingService(PetGroomingService petGroomingService);
}
