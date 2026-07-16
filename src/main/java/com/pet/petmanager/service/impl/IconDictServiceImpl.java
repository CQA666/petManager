package com.pet.petmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.dao.IconDictDao;
import com.pet.petmanager.entity.IconDict;
import com.pet.petmanager.service.IconDictService;
import com.pet.petmanager.utils.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IconDictServiceImpl extends ServiceImpl<IconDictDao, IconDict> implements IconDictService {
    @Override
    public Result getAllDictItems() {
        // 查询全部字典项，不加任何条件
        List<IconDict> itemList = baseMapper.selectList(new QueryWrapper<>());
        if (itemList == null || itemList.isEmpty()) {
            return Result.error("-1", "查询失败，没有数据");
        }
        return Result.success(itemList);
    }
}