package com.pet.petmanager.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.dao.MenuDao;
import com.pet.petmanager.dao.UserDao;
import com.pet.petmanager.entity.Menu;
import com.pet.petmanager.entity.User;
import com.pet.petmanager.entity.UserPasswordUpdate;
import com.pet.petmanager.enums.UserRole;
import com.pet.petmanager.service.UserService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MenuDao menuDao;

    @Value("${user.defaultPassword}")
    private String DEFAULT_PWD;

    //用户登录
    @Override
    public Result login(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User loginUser = userDao.selectOne(queryWrapper);
// 如果查询结果为空，返回错误信息，提示用户不存在
        if (loginUser == null) {
            return Result.error("-1", "登录失败，用户不存在！");
        }
        // 初始化UserRole对象，用于存储查询到的用户角色
        UserRole loginRole = null;
        // 获取用户的角色信息
        String role = loginUser.getRole();
        // 根据角色信息设置对应的UserRole对象
        if (role.equals(UserRole.USER.getValue())) {
            loginRole = UserRole.USER;
        } else if (role.equals(UserRole.ADMIN.getValue())) {
            loginRole = UserRole.ADMIN;
        } else if (role.equals(UserRole.SUPER_ADMIN.getValue())) {
            loginRole = UserRole.SUPER_ADMIN;
        } else {
            // 如果角色信息不匹配任何已知角色，返回错误信息，提示角色信息异常
            return Result.error("-1", "登陆失败，角色信息异常");
        }
        // 如果用户角色ID不等于3（3为SUPER_ADMIN）
        if (loginRole.getId() != 3) {
            // 根据角色ID和3查询菜单列表
            // 查询角色为 roleId 或3 的菜单列表
            List<Menu> roleMenuList = menuDao.selectList(
                    new LambdaQueryWrapper<Menu>()
                            //in 方法用于查询 Menu 表中 role 字段的值等于 loginRole.getId() 或者 3 的所有记录
                            .in(Menu::getRole, Arrays.asList(loginRole.getId(), 3))
            );
            // 获取一级菜单列表（父菜单ID为空的菜单）
            // 一级菜单
            // 作用:筛选集合中父菜单 ID（pid）为空的菜单作为一级父菜单,并转换为集合
            //stream() 方法用于将集合转换为流，filter 方法用于过滤集合，toList() 方法用于将流转换为集合
            List<Menu> parentList = roleMenuList.stream().filter(menu -> menu.getPid() == null).toList();
            // 遍历一级菜单列表，为每个一级菜单设置其对应的子菜单列表
            for (Menu parentMenu : parentList) {
                // 根据父菜单ID查询子菜单列表
                //collect是收集器
                //Collectors.toList()是转换器
                List<Menu> childrenList = roleMenuList.stream().filter(menu -> parentMenu.getId().equals(menu.getPid())).collect(Collectors.toList());
                // 将子菜单列表设置为一级菜单的子菜单
                parentMenu.setChildren(childrenList);
            }
            // 将菜单列表设置到用户对象中
            loginUser.setMenuList(roleMenuList);
        } else {
            // 如果用户角色ID等于3，查询所有菜单列表
            // 查询所有菜单列表
            List<Menu> roleMenuList = menuDao.selectList(null);
            // 获取一级菜单列表（父菜单ID为空的菜单）
            // 一级菜单
            List<Menu> parentList = roleMenuList.stream().filter(menu -> menu.getPid() == null).toList();
            // 遍历一级菜单列表，为每个一级菜单设置其对应的子菜单列表
            for (Menu parentMenu : parentList) {
                // 根据父菜单ID查询子菜单列表
                List<Menu> childrenList = roleMenuList.stream().filter(menu -> parentMenu.getId().equals(menu.getPid())).collect(Collectors.toList());
                // 将子菜单列表设置为一级菜单的子菜单
                parentMenu.setChildren(childrenList);
            }
            // 将菜单列表设置到用户对象中
            loginUser.setMenuList(roleMenuList);
        }

        // 验证用户密码是否匹配
        // 如果传入的用户密码与数据库中查询到的用户密码匹配
        if (!user.getPassword().equals(loginUser.getPassword())) {
            // 返回错误信息，提示用户名或密码错误
            return Result.error("-1", "登录失败，用户名或密码错误！");

        }
        // 返回成功信息，包含用户对象
        return Result.success(loginUser);
    }


    //用户注册
    @Override
    public Result register(User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //判断名称是否重复
        queryWrapper.eq(User::getUsername, user.getUsername());
        Long userCount1 = userDao.selectCount(queryWrapper);
        if (userCount1 > 0) {
            return Result.error("-1", "用户名已经存在");
        }
        //判断Email是否重复
        queryWrapper.eq(User::getEmail, user.getEmail());
        Long userCount2 = userDao.selectCount(queryWrapper);
        if (userCount2 > 0) {
            return Result.error("-1", "邮箱已经存在");
        }
        //判断密码是否为非空或者仅由空白字符组成的，如果为空则设置默认密码
        if (!StringUtils.isNotBlank(user.getPassword())) {
            user.setPassword(DEFAULT_PWD);
        }
        //判断用户角色是否为空或者仅由空白字符组成的，如果为空则设置默认密码
        if (!StringUtils.isNotBlank(user.getRole())) {
            user.setRole(UserRole.USER.getValue());
        } else {

            //如果不为空判断是否正常
            if (!Arrays.asList(UserRole.ADMIN.getValue(), UserRole.USER.getValue(), UserRole.SUPER_ADMIN.getValue()).contains(user.getRole())) {
                // 如果角色无效，返回错误信息
                return Result.error("-1", "无效的角色");

            }
        }
            // 保存用户信息到数据库
            // insert方法用于插入一条记录，返回受影响的行数
            int res = userDao.insert(user);
            // 如果受影响的行数大于0，返回成功信息，包含用户对象
            if (res > 0) {
                return Result.success(user);
            } else {
                // 如果受影响的行数等于0，返回错误信息
                return Result.error("-1", "注册失败");
            }

    }

    @Override
    public Result updateUser(Integer id, User user) {
        User user1 = userDao.selectOne(new QueryWrapper<User>().eq("email", user.getEmail()));
        //判断邮箱是否存在
        if (user1!=null&&!user1.getId().equals(user.getId())){
            return Result.error("-1","邮箱已经存在");
        }
        user.setId(id);
        int count = userDao.updateById(user);

        if (count<=0){
            return Result.error("-1","未找到相关用户");
        }
        return Result.success(user);

    }

    @Override
    public Result forgetPassword(String email, String newPassword) {
        User user1 = userDao.selectOne(new QueryWrapper<User>().eq("email", email));
        //判断user1是否为空
        if (user1 == null) {
            return Result.error("-1", "该邮箱未注册");
        }
        //判断新密码是否为空或者不为null
        if(!StringUtils.isNotBlank(newPassword)){
            return Result.error("-1","密码不能为空");
        }
        //判断新密码长度是否符合要求（6-20位）
        if (newPassword.length() < 6 || newPassword.length() > 20) {
            return Result.error("-1", "密码长度必须在6到20个字符之间");
        }
        //判断新密码是否与原密码相同
        if (newPassword.equals(user1.getPassword())) {
            return Result.error("-1", "新密码不能与原密码相同");
        }

        user1.setPassword(newPassword);
        int res = userDao.updateById(user1);
        if (res==0){
            return Result.error("-1","更新失败，请重试");
        }
        return Result.success("密码修改成功");
    }

    @Override
    public Result changePassword(Integer id, UserPasswordUpdate userPasswordUpdate) {
        //判断参数是否为空
        if (userPasswordUpdate == null || id == null) {
            return Result.error("-1", "密码不能为空");
        }
        //判断新旧密码是否一样
        if (userPasswordUpdate.getNewPassword().equals(userPasswordUpdate.getOldPassword())){
            return Result.error("-1","新旧密码不能一样");
        }
        //查询用户
        User user = userDao.selectOne(new QueryWrapper<User>().eq("id", id));
        //判断用户是否存在
        if (user == null) {
            return Result.error("-1", "用户不存在");
        }
        //验证旧密码是否正确
        if (!user.getPassword().equals(userPasswordUpdate.getOldPassword())) {
            return Result.error("-1", "旧密码不正确");
        }
        //修改密码
        user.setPassword(userPasswordUpdate.getNewPassword());
        int rec = userDao.updateById(user);
        if (rec==0) {
            return Result.error("-1", "修改密码失败，请重试");
        }
        return Result.success("密码修改成功");
    }
    @Override
    public Result getUsersByPage(String username, String sex, String name, String role, String currentRole, Integer currentPage, Integer size) {

        // 权限控制：根据当前角色决定可查询的用户范围
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        // 检查当前用户角色是否为管理员
        if (UserRole.ADMIN.getValue().equals(currentRole)) {
            // 如果是管理员，则查询角色为普通用户的数据
            queryWrapper.eq(User::getRole, UserRole.USER.getValue());
            // 检查当前用户角色是否为超级管理员
        } else if (UserRole.SUPER_ADMIN.getValue().equals(currentRole)) {
            // 如果是超级管理员，则查询角色为普通用户或管理员的数据
            queryWrapper.in(User::getRole, Arrays.asList(UserRole.USER.getValue(), UserRole.ADMIN.getValue()));
            // 如果当前用户角色既不是管理员也不是超级管理员
        } else {
            // 返回身份认证失败的结果
            return Result.error("-1", "身份认证失败");
        }

        // 动态添加查询条件
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like(User::getUsername, username);
        }
        if (StringUtils.isNotBlank(sex)) {
            queryWrapper.eq(User::getSex, sex);
        }
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like(User::getName, name);
        }
        if (StringUtils.isNotBlank(role)) {
            queryWrapper.eq(User::getRole, role);
        }

        // 设置分页条件
        Page<User> page = new Page<>(currentPage, size);
        // 查询用户列表
        Page<User> resultPage = userDao.selectPage(page, queryWrapper);
        // 返回成功信息，包含用户分页对象
        return Result.success(resultPage);
    }

    @Override
    public Result deleteBatch(List<Integer> ids) {
        // 判断用户ID列表是否为空
        if (ids == null || ids.isEmpty()) {
            return Result.error("-1", "用户ID列表不能为空");
        }
        // 根据用户ID列表删除用户信息
        int deletedCount = userDao.deleteBatchIds(ids);
        // 判断删除的用户数量是否大于0
        if (deletedCount > 0) {
            // 返回成功信息
            return Result.success();
        } else {
            // 返回错误信息，提示未找到对应的用户记录
            return Result.error("-1", "删除失败：未找到对应的用户记录");
        }
    }

    @Override
    public Result searchUsersByUsernameOrName(String username, Integer limit) {
        // 构建查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        // 指定查询字段
        // 返回除密码外的字段
        wrapper.select(User::getId,
                        User::getUsername,
                        User::getName,
                        User::getRole,
                        User::getAvatar,
                        User::getSex,
                        User::getPhone,
                        User::getEmail,
                        User::getAddress,
                        User::getAccount)
                // 模糊查询用户名
                .like(StringUtils.isNotBlank(username), User::getUsername, username)
                // or模糊查询姓名
                .or()
                .like(StringUtils.isNotBlank(username), User::getName, username)
                .last("LIMIT " + limit); // 限制返回数量

        // 查询用户列表
        List<User> users = userDao.selectList(wrapper);
        // 返回成功信息，包含用户列表
        return Result.success(users);
    }

    @Override
    public Result rechargeBalance(Map<String, Object> params) {
        // 参数提取与校验
        if (params == null) {
            return Result.error("-1", "请求参数不能为空");
        }

        Object userIdObj = params.get("userId");
        Object amountObj = params.get("amount");

        if (userIdObj == null || amountObj == null) {
            return Result.error("-1", "缺少必要参数: userId 或 amount");
        }
        Integer userId;
        Double amount;
        try {
            userId = Integer.valueOf(userIdObj.toString());
            amount = Double.valueOf(amountObj.toString());
        } catch (NumberFormatException e) {
            return Result.error("-1", "参数格式错误");
        }

        if (userId <= 0 || amount <= 0) {
            return Result.error("-1", "参数错误或充值金额不正确");
        }

        // 查询用户
        User user = userDao.selectById(userId);
        if (user == null) {
            return Result.error("-1", "用户不存在");
        }

        // 4. 更新余额
        double oldBalance = user.getAccount() != null ? user.getAccount() : 0.0;
        double newBalance = oldBalance + amount;
        user.setAccount(newBalance);

        // 5. 执行更新
        int rows = userDao.updateById(user);
        if (rows > 0) {
            // 6. 构造返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("userId", userId);
            result.put("newBalance", newBalance);
            result.put("addedAmount", amount);
            return Result.success(result);
        } else {
            return Result.error("-1", "充值失败，请稍后再试");
        }
    }
}
