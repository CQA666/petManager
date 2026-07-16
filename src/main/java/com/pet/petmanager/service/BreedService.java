package com.pet.petmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.Breed;
import com.pet.petmanager.utils.Result;

import java.util.List;

public interface BreedService extends IService<Breed> {

    Result selectPage(String name, Integer pageNum, Integer size);

    Result deleteBatch(List<Integer> ids);

    Result saveBreed(Breed breed);

    Result updateBreed(Breed breed);
}
