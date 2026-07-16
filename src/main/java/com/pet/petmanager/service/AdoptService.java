package com.pet.petmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.VO.AdoptVO;
import com.pet.petmanager.entity.Adopt;
import com.pet.petmanager.utils.Result;

public interface AdoptService extends IService<Adopt> {

    //领养分页查询
    Result selectPage(String name, Integer currentPage, Integer size);


    //修改领养信息
    Result updateAdopt(AdoptVO adoptVO);

    Result getAdoptTrend();
}
