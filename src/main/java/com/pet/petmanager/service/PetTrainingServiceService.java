package com.pet.petmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.PetTrainingService;
import com.pet.petmanager.utils.Result;

import java.util.List;

public interface PetTrainingServiceService extends IService<PetTrainingService> {
    Result selectPage(String serviceType, String username, Integer pageNum, Integer pageSize);

    Result updatePetTrainingService(PetTrainingService petTrainingService);

    Result deleteBatch(List<Integer> ids);

    Result savePetTrainingService(PetTrainingService petTrainingService);
}