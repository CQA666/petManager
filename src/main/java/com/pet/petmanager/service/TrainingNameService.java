package com.pet.petmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.TrainingName;
import com.pet.petmanager.utils.Result;

import java.util.List;

public interface TrainingNameService extends IService<TrainingName> {
    Result selectPage(String name, Integer pageNum, Integer pageSize);

    Result deleteBatch(List<Integer> ids);

    Result saveTrainingName(TrainingName trainingName);

    Result updateTrainingName(TrainingName trainingName);

    Result selectAll();
}
