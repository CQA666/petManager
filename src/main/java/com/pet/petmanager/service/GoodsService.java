package com.pet.petmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.Goods;
import com.pet.petmanager.utils.Result;

import java.util.List;

public interface GoodsService extends IService<Goods> {
    Result getGoodsByPage(String name, Integer currentPage, Integer size);

    Result saveGoods(Goods goods);

    Result deleteBatchGoods(List<Integer> ids);

    Result updateGoods(Goods goods);

    Result selectAllGoods();
}
