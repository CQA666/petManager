package com.pet.petmanager.controller;
import com.pet.petmanager.entity.Menu;
import com.pet.petmanager.service.MenuService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 查询菜单树（支持按名称搜索）
     */
    @RequestMapping("/findAll")
    public Result findAll(@RequestParam(name = "name", defaultValue = "") String name) {
        return menuService.getMenuTree(name);
    }

    /**
     * 更新菜单
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Menu menu) {
        return menuService.updateMenu(menu);
    }

    /**
     * 批量删除菜单
     */
    @RequestMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam List<Integer> ids) {
        return menuService.deleteBatch(ids);
    }

    /**
     * 保存菜单（新增或更新，自动同步父子角色）
     */
    @RequestMapping("/save")
    public Result save(@RequestBody Menu menu) {
        return menuService.saveOrUpdateMenu(menu);
    }
}