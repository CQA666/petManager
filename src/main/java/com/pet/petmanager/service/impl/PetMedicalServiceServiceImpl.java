package com.pet.petmanager.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.dao.PetMedicalServiceDao;
import com.pet.petmanager.entity.PetMedicalService;
import com.pet.petmanager.service.PetMedicalServiceService;
import com.pet.petmanager.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PetMedicalServiceServiceImpl extends ServiceImpl<PetMedicalServiceDao, PetMedicalService> implements PetMedicalServiceService {
    private final PetMedicalServiceDao petMedicalServiceDao;

    public PetMedicalServiceServiceImpl(PetMedicalServiceDao petMedicalServiceDao) {
        this.petMedicalServiceDao = petMedicalServiceDao;
    }

    @Override
    public Result selectPage(String petName, String username, Integer pageNum, Integer pageSize) {

        LambdaQueryWrapper<PetMedicalService> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(petName)) {
            queryWrapper.like(PetMedicalService::getPetName, petName);
        }
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like(PetMedicalService::getUsername, username);
        }
        Page<PetMedicalService> page = new Page<>(pageNum, pageSize);
        Page<PetMedicalService> resultPage = this.page(page, queryWrapper);

        return Result.success(resultPage);
    }

    @Override
    public Result deleteBatchStrict(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Result.error("-1", "ID 列表不能为空");
        }
        boolean success = this.removeByIds(ids); // 批量删除
        return success ? Result.success() : Result.error("-1", "未删除任何记录，所有 ID 可能均无效");
    }

    @Override
    public Result updatePetMedicalService(PetMedicalService petMedicalService) {
        if (petMedicalService == null) {
            return Result.error("-1", "请求体不能为空");
        }

        Integer id = petMedicalService.getId();
        if (id == null || id <= 0) {
            return Result.error("-1", "ID 不能为空且必须大于 0");
        }

        boolean updated = this.updateById(petMedicalService);

        if (updated) {
            return Result.success();
        } else {
            return Result.error("-1", "更新失败，记录可能不存在");
        }
    }

    @Override
    public Result create(PetMedicalService petMedicalService) {
        // 1. 设置默认字段
        petMedicalService.setStatus("未开始");
        petMedicalService.setCreatedAt(LocalDate.now());

        // 2. 执行插入
        int rows = petMedicalServiceDao.insert(petMedicalService);

        // 3. 返回统一结果
        if (rows > 0) {
            return Result.success();
        } else {
            return Result.error("-1", "添加失败");
        }
    }
}