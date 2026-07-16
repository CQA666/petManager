package com.pet.petmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.dao.MenuDao;
import com.pet.petmanager.entity.Menu;
import com.pet.petmanager.service.MenuService;
import com.pet.petmanager.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public Result getMenuTree(String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort_num");
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like("name", name);
        }
        List<Menu> allList = menuDao.selectList(queryWrapper);

        // 构建一级菜单（pid == null 或 pid == 0）
        List<Menu> parentList = allList.stream()
                .filter(menu -> menu.getPid() == null || menu.getPid() == 0)
                .collect(Collectors.toList());

        // 为每个一级菜单设置子菜单
        for (Menu parent : parentList) {
            List<Menu> children = allList.stream()
                    .filter(menu -> parent.getId().equals(menu.getPid()))
                    .collect(Collectors.toList());
            parent.setChildren(children);
        }

        return Result.success(parentList);
    }

    @Override
    public Result updateMenu(Menu menu) {
        boolean updated = this.updateById(menu);
        if (updated) {
            return Result.success();
        } else {
            return Result.error("-1", "修改失败");
        }
    }

    @Override
    public Result deleteBatch(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Result.error("-1", "ID列表不能为空");
        }
        boolean deleted = this.removeByIds(ids);
        if (deleted) {
            return Result.success();
        } else {
            return Result.error("-1", "删除失败");
        }
    }

    @Override
    public Result saveOrUpdateMenu(Menu menu) {
        // 判断是新增还是更新
        Menu res = menuDao.selectOne(Wrappers.<Menu>lambdaQuery().eq(Menu::getId, menu.getId()));
        if (res != null) {
            // 更新
            int i = menuDao.updateById(menu);
            if (i > 0) {
                this.updateMenuRole(menu);  // 同步父子角色
                return Result.success("更新成功");
            } else {
                return Result.error("-1", "更新失败");
            }
        } else {
            // 新增
            int insert = menuDao.insert(menu);
            if (insert > 0) {
                this.updateMenuRole(menu);  // 同步父子角色
                return Result.success("插入成功");
            } else {
                return Result.error("-1", "插入失败");
            }
        }
    }

    /**
     * 根据当前菜单，更新其父级或子级的角色字段（role）
     * 规则：父菜单的角色由所有子菜单的角色综合决定
     */
    private void updateMenuRole(Menu menu) {
        // 当前menu是父级菜单，获取其所有子菜单并统一角色
        if (menu.getPid() == null) {
            List<Menu> submenus = menuDao.selectList(Wrappers.<Menu>lambdaQuery().eq(Menu::getPid, menu.getId()));
            Integer parentRole = menu.getRole();
            for (Menu submenu : submenus) {
                submenu.setRole(parentRole);
                menuDao.updateById(submenu);
            }
            return;
        }

        // 当前menu是子菜单，向上更新父级角色
        Menu parentMenu = menuDao.selectOne(Wrappers.<Menu>lambdaQuery().eq(Menu::getId, menu.getPid()));
        if (parentMenu == null) {
            return;
        }

        Integer parentId = parentMenu.getId();
        Integer parentRole = parentMenu.getRole();
        List<Menu> childrenMenus = menuDao.selectList(Wrappers.<Menu>lambdaQuery().eq(Menu::getPid, parentId));

        // 统计子菜单的角色分布，决定父菜单角色
        int countRole0 = 0, countRole1 = 0, countRole2 = 0, countRole3 = 0;
        for (Menu childMenu : childrenMenus) {
            Integer childRole = childMenu.getRole();
            if (childRole == 0) countRole0++;
            else if (childRole == 1) countRole1++;
            else if (childRole == 2) countRole2++;
            else if (childRole == 3) countRole3++;
        }

        // 根据子菜单角色分布确定父菜单角色
        if (countRole0 == childrenMenus.size()) {
            parentRole = 0;
        } else if (countRole1 == childrenMenus.size() || (countRole1 > 0 && countRole2 == 0 && countRole3 == 0)) {
            parentRole = 1;
        } else if (countRole2 == childrenMenus.size() || (countRole2 > 0 && countRole1 == 0 && countRole3 == 0)) {
            parentRole = 2;
        } else if (countRole1 > 0 && countRole2 > 0 || countRole3 > 0) {
            parentRole = 3;
        }

        // 更新父级菜单角色
        if (!parentRole.equals(parentMenu.getRole())) {
            parentMenu.setRole(parentRole);
            menuDao.updateById(parentMenu);
        }
    }
}