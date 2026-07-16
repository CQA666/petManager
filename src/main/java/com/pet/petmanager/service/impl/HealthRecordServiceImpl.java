package com.pet.petmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.VO.HealthRecordVO;
import com.pet.petmanager.dao.HealthRecordDao;
import com.pet.petmanager.entity.HealthRecord;
import com.pet.petmanager.service.HealthRecordService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthRecordServiceImpl extends ServiceImpl<HealthRecordDao, HealthRecord> implements HealthRecordService {
    @Autowired
    private HealthRecordDao healthRecordDao;

    @Override
    public Result getHealthRecordByPage(String petName, String recordDate, Integer currentPage, Integer size) {
        // 创建一个分页对象，设置当前页码和每页大小
        Page<HealthRecordVO> page = new Page<>(currentPage, size);
        // 根据宠物名称和记录日期从数据库中查询健康记录，并将结果存储到healthRecordPage中
        Page<HealthRecordVO> healthRecordPage = healthRecordDao.selectByPage(petName, recordDate, page);
        // 返回一个成功的结果，包含分页后的健康记录数据
        return Result.success(healthRecordPage);
    }

    @Override
    public Result deleteBatchByIds(List<Long> ids) {
        // 1. 参数校验
        if (ids == null || ids.isEmpty()) {
            return Result.error("-1", "ID列表不能为空");
        }

        // 2. 执行批量删除（使用 MyBatis-Plus）
        int deletedCount = healthRecordDao.deleteBatchIds(ids);

        // 3. 判断结果并返回
        if (deletedCount > 0) {
            return Result.success();
        } else {
            return Result.error("-1", "删除失败：未找到匹配的记录");
        }
    }

    @Override
    public Result addHealthRecord(HealthRecord healthRecord) {
        // 参数校验
        if (healthRecord == null) {
            return Result.error("-1", "健康记录不能为空");
        }

        // 执行插入
        int inserted = healthRecordDao.insert(healthRecord);

        // 返回结果
        if (inserted > 0) {
            return Result.success();
        } else {
            return Result.error("-1", "添加失败");
        }
    }

    @Override
    public Result updateHealthRecord(HealthRecord healthRecord) {
        if (healthRecord == null || healthRecord.getId() == null) {
            return Result.error("-1", "健康记录或ID不能为空");
        }

        // 执行更新（MyBatis-Plus 根据 ID 更新非空字段）
        int updated = healthRecordDao.updateById(healthRecord);

        // 返回结果
        if (updated > 0) {
            return Result.success();
        } else {
            return Result.error("-1", "更新失败：未找到对应记录");
        }
    }

    @Override
    public Result selectByPetId(Long id) {
        QueryWrapper<HealthRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("pet_id", id);
        List<HealthRecord> healthRecords = healthRecordDao.selectList(wrapper);
        return Result.success(healthRecords);
    }


}


