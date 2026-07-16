package com.pet.petmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.Menu;
import com.pet.petmanager.utils.Result;

import java.util.List;

public interface MenuService extends IService<Menu> {
    Result getMenuTree(String name);

    Result updateMenu(Menu menu);

    Result deleteBatch(List<Integer> ids);

    Result saveOrUpdateMenu(Menu menu);
}