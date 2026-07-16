package com.pet.petmanager.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.MedicalServiceType;
import com.pet.petmanager.utils.Result;

public interface MedicalServiceTypeService extends IService<MedicalServiceType> {
    Result selectAll();
}