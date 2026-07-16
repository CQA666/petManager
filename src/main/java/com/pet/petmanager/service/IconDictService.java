package com.pet.petmanager.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.IconDict;
import com.pet.petmanager.utils.Result;

public interface IconDictService extends IService<IconDict> {
    Result getAllDictItems();
}