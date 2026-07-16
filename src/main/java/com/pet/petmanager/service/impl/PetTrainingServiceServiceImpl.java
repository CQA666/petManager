package com.pet.petmanager.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.dao.PetTrainingServiceDao;
import com.pet.petmanager.entity.PetTrainingService;
import com.pet.petmanager.service.PetTrainingServiceService;
import com.pet.petmanager.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetTrainingServiceServiceImpl extends ServiceImpl<PetTrainingServiceDao, PetTrainingService> implements PetTrainingServiceService {
    private final PetTrainingServiceDao petTrainingServiceDao;

    public PetTrainingServiceServiceImpl(PetTrainingServiceDao petTrainingServiceDao) {
        this.petTrainingServiceDao = petTrainingServiceDao;
    }

    @Override
    public Result selectPage(String serviceType, String username, Integer pageNum, Integer pageSize) {

        // 构建查询条件
        LambdaQueryWrapper<PetTrainingService> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(serviceType)) {
            queryWrapper.eq(PetTrainingService::getServiceType, serviceType);
        }
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.eq(PetTrainingService::getUserName, username);
        }

        // 执行分页查询
        Page<PetTrainingService> page = new Page<>(pageNum, pageSize);
        // 调用IService的page方法查询分页数据
        Page<PetTrainingService> resultPage = this.page(page, queryWrapper);
        // 将分页数据转换为Result对象
        return Result.success(resultPage);
    }

    @Override
    public Result updatePetTrainingService(PetTrainingService petTrainingService) {
        // 1. 参数校验
        if (petTrainingService == null) {
            return Result.error("-1", "请求体不能为空");
        }
        if (petTrainingService.getId() == null || petTrainingService.getId() <= 0) {
            return Result.error("-1", "ID 不能为空且必须大于 0");
        }

        // 2. 执行更新（MyBatis-Plus 根据 ID 更新非空字段）
        boolean updated = this.updateById(petTrainingService);

        // 3. 返回结果
        if (updated) {
            return Result.success(petTrainingService);
        } else {
            return Result.error("-1", "更新失败，可能记录不存在");
        }
    }

    @Override
    public Result deleteBatch(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Result.error("-1", "ID 列表不能为空");
        }
        // 调用IService的removeByIds方法批量删除数据
        boolean deleted = this.removeByIds(ids);
        // 判断是否全部删除成功
        if (deleted) {
            return Result.success();
        } else {
            return Result.error("-1", "批量删除失败，可能记录不存在或已被删除");
        }
    }

    @Override
    public Result savePetTrainingService(PetTrainingService petTrainingService) {
        // 1. 设置默认完成状态
        petTrainingService.setCompletionStatus("未开始");

        // 2. 执行数据库插入
        int rows = petTrainingServiceDao.insert(petTrainingService);

        // 3. 根据插入结果返回成功/失败
        if (rows > 0) {
            return Result.success();
        } else {
            return Result.error("-1", "训练服务添加失败");
        }
    }
}
