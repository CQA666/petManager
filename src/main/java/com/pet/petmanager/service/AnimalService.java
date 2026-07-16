package com.pet.petmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.Animal;
import com.pet.petmanager.utils.Result;

import java.util.List;

public interface AnimalService extends IService<Animal> {

    //分页查询宠物
    Result selectPage(String name, Integer pageNum, Integer size);

    //批量删除宠物
    Result deleteAnimals(List<Integer> ids);

    //新增宠物
    Result saveAnimal(Animal animal);

    //更新宠物信息
    Result updateAnimal(Integer id, Animal animal);

    Result selectAll();
}
