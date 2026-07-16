package com.pet.petmanager.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.dao.TrainingNameDao;
import com.pet.petmanager.entity.TrainingName;
import com.pet.petmanager.service.TrainingNameService;
import com.pet.petmanager.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingNameServiceImpl extends ServiceImpl<TrainingNameDao, TrainingName> implements TrainingNameService {

    @Autowired
    private TrainingNameDao trainingNameDao;

    @Override
    public Result selectPage(String name, Integer pageNum, Integer pageSize) {

        // 构建查询条件
        LambdaQueryWrapper<TrainingName> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like(TrainingName::getName, name);
        }

        // 执行分页查询
        Page<TrainingName> page = new Page<>(pageNum, pageSize);
        Page<TrainingName> resultPage = trainingNameDao.selectPage(page, queryWrapper);
        // 封装返回结果
        return Result.success(resultPage);
    }

    @Override
    public Result deleteBatch(List<Integer> ids) {
        // 参数校验
        if (ids == null || ids.isEmpty()) {
            return Result.error("-1", "ID列表不能为空");
        }

        // 执行批量删除（MyBatis-Plus 内置方法）
        // 等价于 mapper.deleteBatchIds()
        boolean deleted = this.removeByIds(ids);
        // 判断是否删除成功
        if (deleted) {
            // 返回成功结果
            return Result.success();
        } else {
            // 返回失败结果
            return Result.error("-1", "批量删除失败");
        }
    }

    @Override
    public Result saveTrainingName(TrainingName trainingName) {
        // 参数校验
        if (trainingName == null) {
            return Result.error("-1", "请求体不能为空");
        }
        if (StringUtils.isBlank(trainingName.getName())) {
            return Result.error("-1", "训练名称不能为空");
        }
        // 判断数据库中是否存在训练名称
        boolean b = trainingNameDao.selectCount(new LambdaQueryWrapper<TrainingName>().eq(TrainingName::getName, trainingName.getName().trim())) > 0;
        if (b){
            return Result.error("-1","训练名称已存在,请勿重复添加");

        }
        // 执行插入（MyBatis-Plus 自动填充 id）
        // 等价于 BaseMapper.insert()
        boolean saved = this.save(trainingName);

        if (saved) {
            return Result.success();
        } else {
            return Result.error("-1", "新增失败");
        }
    }

    @Override
    public Result updateTrainingName(TrainingName trainingName) {
        // 参数校验
        if (trainingName == null) {
            return Result.error("-1", "请求体不能为空");
        }
        if (trainingName.getId() == null || trainingName.getId() <= 0) {
            return Result.error("-1", "ID不能为空且必须大于0");
        }
        if (StringUtils.isBlank(trainingName.getName())) {
            return Result.error("-1", "训练名称不能为空");
        }
        // 执行更新（MyBatis-Plus 的 updateById 要求实体包含有效 ID）
        boolean updated = this.updateById(trainingName);

        // 返回结果
        if (updated) {
            return Result.success(trainingName); // 返回更新后的对象（含可能自动填充字段）
        } else {
            return Result.error("-1", "更新失败，可能记录不存在");
        }
    }

    @Override
    public Result selectAll() {
        // 执行全量查询
        List<TrainingName> list = trainingNameDao.selectList(null);
        // 判断是否有查询结果
        if (list != null && !list.isEmpty()) {
            return Result.success(list);
        } else {
            return Result.error("-1", "未找到训练名称");
        }
    }

}
