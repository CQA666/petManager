package com.pet.petmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.dao.MedicalServiceTypeDao;
import com.pet.petmanager.entity.MedicalServiceType;
import com.pet.petmanager.service.MedicalServiceTypeService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalServiceTypeServiceImpl extends ServiceImpl<MedicalServiceTypeDao, MedicalServiceType> implements MedicalServiceTypeService {

    @Autowired
    private MedicalServiceTypeDao medicalServiceTypeDao;

    @Override
    public Result selectAll() {
        LambdaQueryWrapper<MedicalServiceType> queryWrapper = new LambdaQueryWrapper<>();
        List<MedicalServiceType> list = this.list(queryWrapper);
        if (list.isEmpty()) {
            return Result.error("-1", "未找到服务类型");
        }
        return Result.success(list);
    }
}