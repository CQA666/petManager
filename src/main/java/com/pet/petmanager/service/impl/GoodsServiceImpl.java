package com.pet.petmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.dao.GoodsDao;
import com.pet.petmanager.entity.Goods;
import com.pet.petmanager.service.GoodsService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsDao, Goods> implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public Result getGoodsByPage(String name, Integer currentPage, Integer size) {

        // 构建分页对象
        Page<Goods> page = new Page<>(currentPage, size);
        // 构建动态查询条件
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            queryWrapper.like(Goods::getName, name);
        }
        // 执行分页查询
        Page<Goods> goodsPage = goodsDao.selectPage(page, queryWrapper);
        // 返回成功结果
        return Result.success(goodsPage);
    }

    @Override
    public Result saveGoods(Goods goods) {
        if (goods==null)
            return Result.error("-1","商品信息为空，请重新输入");
        int res = goodsDao.insert(goods);

        return res>0? Result.success():Result.error("-1","新增商品失败，请重试");
    }

    @Override
    public Result deleteBatchGoods(List<Integer> ids) {
        // 参数校验
        if (ids == null || ids.isEmpty()) {
            return Result.error("-1", "商品ID列表不能为空");
        }

        // 调用BaseMapper的deleteBatchIds方法批量删除商品
        int deletedCount = goodsDao.deleteBatchIds(ids);

        // 判断结果与参数列表长度是否一致
        if (deletedCount == ids.size()) {
            // 全部删除成功
            return Result.success();
            // 部分成功
        } else if (deletedCount > 0) {
            // 部分成功（有些 ID 不存在）
            return Result.error("-1", "部分商品不存在，仅删除 " + deletedCount + " 条");
        } else {
            // 全部失败（所有 ID 都不存在）
            return Result.error("-1", "未找到要删除的商品");
        }
    }

    @Override
    public Result updateGoods(Goods goods) {
        // 参数校验
        if (goods == null || goods.getId() == null) {
            return Result.error("-1", "商品ID不能为空");
        }
        //调用BaseMapper的updateById方法更新商品
        int updated = goodsDao.updateById(goods);
        //判断结果
        if (updated > 0) {
            //更新成功
            return Result.success();
        } else {
            //更新失败
            return Result.error("-1", "更新失败：商品可能不存在或未修改任何字段");
        }
    }
    @Override
    public Result selectAllGoods() {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        List<Goods> goodsList = goodsDao.selectList(queryWrapper);
        if (goodsList == null) {
            return Result.error("-1", "商品数据加载异常");
        }
        return Result.success(goodsList);
    }

}
