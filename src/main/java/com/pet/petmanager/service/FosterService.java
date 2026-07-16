package com.pet.petmanager.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.Foster;
import com.pet.petmanager.utils.Result;

public interface FosterService extends IService<Foster> {
    Result selectFosterPage(String animalName, Integer userId, String status, Integer currentPage, Integer size);

    Result updateFosterAndRoom(Foster foster);
}