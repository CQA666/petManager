package com.pet.petmanager.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.PetMedicalService;
import com.pet.petmanager.utils.Result;

import java.util.List;

public interface PetMedicalServiceService extends IService<PetMedicalService> {
    Result selectPage(String petName, String username, Integer pageNum, Integer pageSize);

    Result deleteBatchStrict(List<Integer> ids);

    Result updatePetMedicalService(PetMedicalService petMedicalService);

    Result create(PetMedicalService petMedicalService);
}