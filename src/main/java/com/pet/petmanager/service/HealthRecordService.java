package com.pet.petmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.HealthRecord;
import com.pet.petmanager.utils.Result;

import java.util.List;

public interface HealthRecordService extends IService<HealthRecord> {
    Result getHealthRecordByPage(String petName, String recordDate, Integer currentPage, Integer size);

    Result deleteBatchByIds(List<Long> ids);

    Result addHealthRecord(HealthRecord healthRecord);

    Result updateHealthRecord(HealthRecord healthRecord);

    Result selectByPetId(Long id);
}
