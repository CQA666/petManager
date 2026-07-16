package com.pet.petmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.dao.PetGroomingServiceDao;
import com.pet.petmanager.entity.PetGroomingService;
import com.pet.petmanager.service.PetGroomingServiceService;
import com.pet.petmanager.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PetGroomingServiceServiceImpl extends ServiceImpl<PetGroomingServiceDao, PetGroomingService> implements PetGroomingServiceService {

    @Autowired
    private PetGroomingServiceDao petGroomingServiceDao;

    @Override
    public Result selectPage(String petName, String username, Integer pageNum, Integer pageSize) {

        LambdaQueryWrapper<PetGroomingService> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(petName)) {
            queryWrapper.like(PetGroomingService::getPetName, petName);
        }
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like(PetGroomingService::getUsername, username);
        }

        Page<PetGroomingService> page = new Page<>(pageNum, pageSize);
        Page<PetGroomingService> resultPage = this.page(page, queryWrapper);

        return Result.success(resultPage);
    }

    @Override
    public Result deleteBatch(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Result.error("-1", "ID 列表不能为空");
        }

        int i = petGroomingServiceDao.deleteBatchIds(ids);
        if (i != ids.size()) {
            return Result.error("-1", "删除失败");
        }
        return Result.success();
    }

    @Override
    public Result updatePetGroomingService(PetGroomingService petGroomingService) {
        if (petGroomingService == null) {
            return Result.error("-1", "请求体不能为空");
        }
        if (petGroomingService.getId() == null || petGroomingService.getId() <= 0) {
            return Result.error("-1", "ID 不能为空且必须大于 0");
        }
        // 更新宠物美容服务
        boolean updated = this.updateById(petGroomingService);

        // 判断更新是否成功
        if (updated) {
            // 更新成功
            return Result.success();
        } else {
            // 更新失败
            return Result.error("-1", "更新失败，记录可能不存在");
        }
    }

    @Override
    public Result savePetGroomingService(PetGroomingService petGroomingService) {
        // 设置默认状态
        petGroomingService.setStatus("未开始");

        petGroomingService.setCreatedAt(LocalDate.from(LocalDateTime.now()));

        // 执行插入
        int res = petGroomingServiceDao.insert(petGroomingService);
        return res>0?Result.success():Result.error("-1","添加失败");
    }
}

