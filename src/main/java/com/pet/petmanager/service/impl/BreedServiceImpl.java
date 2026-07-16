package com.pet.petmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.dao.BreedDao;
import com.pet.petmanager.entity.Breed;
import com.pet.petmanager.service.BreedService;
import com.pet.petmanager.utils.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BreedServiceImpl extends ServiceImpl<BreedDao, Breed> implements BreedService {

    @Autowired
    private BreedDao breedDao;

    @Override
    public Result selectPage(String name, Integer pageNum, Integer size) {
        LambdaQueryWrapper<Breed> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name),Breed::getBreedName,name);
        Page<Breed> breedPage = breedDao.selectPage(new Page<>(pageNum, size), queryWrapper);

        return Result.success(breedPage);
    }

    @Override
    public Result deleteBatch(List<Integer> ids) {
        if (ids==null||ids.isEmpty()){
            return Result.error("-1","请选择要删除的选项");
        }
        int res = breedDao.deleteBatchIds(ids);
        return res>0?Result.success():Result.error("-1","删除失败，请重试");
    }

    @Override
    public Result saveBreed(Breed breed) {
        if (breed==null){
            return Result.error("-1","品种信息为空，请重新输入");
        }
        int res = breedDao.insert(breed);

        return  res>0?Result.success():Result.error("-1","新增失败，请重试");
    }

    @Override
    public Result updateBreed(Breed breed) {

        if (breed==null){
            return Result.error("-1","品种信息为空，请重新输入");
        }
        LambdaQueryWrapper<Breed> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Breed::getBreedName,breed.getBreedName());
        Breed breed1 = breedDao.selectOne(queryWrapper);
        if (breed1!=null&&!breed1.getBreedId().equals(breed.getBreedId())){
            return Result.error("-1","品种重复，请重新输入");
        }
        int res = breedDao.updateById(breed);
        return res>0?Result.success():Result.error("-1","修改失败，请重试");
    }
}
