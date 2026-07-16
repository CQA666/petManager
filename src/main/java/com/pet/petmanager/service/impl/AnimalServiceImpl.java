package com.pet.petmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.VO.AdoptVO;
import com.pet.petmanager.dao.AdoptDao;
import com.pet.petmanager.dao.AnimalDao;
import com.pet.petmanager.entity.Adopt;
import com.pet.petmanager.entity.Animal;
import com.pet.petmanager.enums.AnimalEnum;
import com.pet.petmanager.service.AdoptService;
import com.pet.petmanager.service.AnimalService;
import com.pet.petmanager.utils.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalServiceImpl extends ServiceImpl<AnimalDao,Animal> implements AnimalService {

    @Autowired
    private AnimalDao animalDao;


    //分页查询宠物
    @Override
    public Result selectPage(String name, Integer pageNum, Integer size) {

        LambdaQueryWrapper<Animal> queryWrapper = new LambdaQueryWrapper<>();
        //如果名字不为空，就模糊查询
        queryWrapper.like(StringUtils.isNotBlank(name),Animal::getName,name)
        //根据id倒叙查询
                    .orderByDesc(Animal::getId);

        //分页查询
        Page<Animal> animalPage = animalDao.selectPage(new Page<>(pageNum, size), queryWrapper);

        //返回查询信息
        return Result.success(animalPage);
    }

    //批量删除宠物
    @Override
    public Result deleteAnimals(List<Integer> ids) {
        if (ids==null||ids.isEmpty()){
            return Result.error("-1","请勾选删除对象");
        }
        int i = animalDao.deleteBatchIds(ids);
        if (i==0){
            return  Result.error("-1","删除失败，请重试");
        }
        return Result.success("删除成功");
    }

    //新增宠物
    @Override
    public Result saveAnimal(Animal animal) {

        if (animal==null){
            return Result.error("-1","宠物信息不能为空");
        }
        animalDao.insert(animal);
        return Result.success();
    }

    @Override
    public Result updateAnimal(Integer id, Animal animal) {
        if (animal==null){
            return Result.error("-1","宠物信息不能为空");
        }

        animal.setId(id);
        int rec = animalDao.updateById(animal);
        if (rec==0){
            return Result.error("-1","信息更新失败，请重试");
        }
        return Result.success();
    }
    @Override
    public Result selectAll() {
        //select * from animal
        //查询所有动物数据
        List<Animal> animals = animalDao.selectList(null);
        if (animals != null && !animals.isEmpty()) {
            return Result.success(animals);
        } else {
            return Result.error("-1", "未找到动物数据");
        }
    }
}
