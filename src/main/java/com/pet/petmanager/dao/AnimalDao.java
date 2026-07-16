package com.pet.petmanager.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pet.petmanager.entity.Animal;

import java.util.List;
import java.util.Map;

public interface AnimalDao extends BaseMapper<Animal> {
    List<Map<String, Object>> selectStatusDistribution();
}
