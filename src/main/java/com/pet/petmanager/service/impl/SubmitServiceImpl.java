package com.pet.petmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.dao.SubmitDao;
import com.pet.petmanager.entity.Submit;
import com.pet.petmanager.enums.SubmitEnum;
import com.pet.petmanager.service.SubmitService;
import com.pet.petmanager.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmitServiceImpl extends ServiceImpl<SubmitDao, Submit> implements SubmitService {

    @Autowired
    private SubmitDao submitDao;


    @Override
    public Result getSubmitsByPage(String name, Integer currentPage, Integer size) {

        // 构建查询条件
        LambdaQueryWrapper<Submit> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            // 如果 name 不为空，则模糊查询
            queryWrapper.like(Submit::getName, name);
        }
        // 构建分页对象
        Page<Submit> page = new Page<>(currentPage, size);
        // 执行分页查询
        Page<Submit> resultPage = submitDao.selectPage(page, queryWrapper);
        // 直接返回分页对象（MyBatis-Plus 的 Page 支持 JSON 序列化）
        return Result.success(resultPage);
    }

    @Override
    public Result updateStatus(Integer id) {
        // 1. 参数校验
        if (id == null || id <= 0) {
            return Result.error("-1", "无效的上报ID");
        }

        // 2. 查询是否存在
        Submit submit = submitDao.selectById(id);
        if (submit == null) {
            return Result.error("-1", "上报记录不存在");
        }

        // 3. 更新状态为“已处理”
        submit.setStatus(SubmitEnum.YES.getInfo());
        boolean updated = submitDao.updateById(submit) > 0;

        // 4. 返回结果
        if (updated) {
            return Result.success("更新成功");
        } else {
            return Result.error("-1", "更新失败");
        }
    }
}
