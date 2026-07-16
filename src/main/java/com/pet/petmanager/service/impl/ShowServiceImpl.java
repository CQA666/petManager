package com.pet.petmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pet.petmanager.VO.StatisticsVO;
import com.pet.petmanager.dao.*;
import com.pet.petmanager.entity.*;
import com.pet.petmanager.enums.AdoptEnum;
import com.pet.petmanager.enums.FosterStatus;
import com.pet.petmanager.enums.OrderStatus;
import com.pet.petmanager.service.AdoptService;
import com.pet.petmanager.service.ShowService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShowServiceImpl implements ShowService {

    @Autowired
    private AdoptService adoptService;

    @Autowired
    private AnimalDao animalDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AdoptDao adoptDao;

    @Autowired
    private OrdersDao ordersDao;

    @Autowired
    private FosterDao fosterDao;

    @Autowired
    private GoodsDao goodsDao;

    @Autowired
    private BreedDao breedDao;

    /**
     * 获取领养数据趋势（委托给AdoptService处理）
     */
    @Override
    public Result getAdoptTrend() {
        return adoptService.getAdoptTrend();
    }

    /**
     * 获取系统综合统计数据
     * 计算逻辑：查询各表总数 + 条件筛选 + 金额聚合
     */
    @Override
    public Result getStatistics() {
        try {
            // 基础统计数据
            Long animalCount = animalDao.selectCount(null);
            Long userCount = userDao.selectCount(null) - 1;  // 减去管理员账号
            Long adoptCount = adoptDao.selectCount(null);
            Long orderCount = ordersDao.selectCount(null);
            Long fosterCount = fosterDao.selectCount(null);

            // 可领养宠物数量：状态为"可领养"
            Long availableCount = animalDao.selectCount(
                    new QueryWrapper<Animal>().eq("status", AdoptEnum.NO_ADOPT.getInfo())
            );

            // 本月新增用户数（暂设为0，因无create_time字段）
            Long newUserCount = 0L;

            // 本月领养数量
            LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
            Long monthAdoptCount = adoptDao.selectCount(
                    new QueryWrapper<Adopt>().ge("time", firstDayOfMonth.toString())
            );

            // 已完成订单总金额
            List<Orders> orders = ordersDao.selectList(
                    new LambdaQueryWrapper<Orders>()
                            .eq(Orders::getStatus, OrderStatus.Completed.name())
            );
            BigDecimal totalAmount = BigDecimal.ZERO;
            if (orders != null) {
                for (Orders order : orders) {
                    if (order != null && order.getTotalAmount() != null) {
                        Goods goods = goodsDao.selectById(order.getGoodsId());
                        totalAmount = totalAmount.add(
                                BigDecimal.valueOf(goods.getPrice()).multiply(BigDecimal.valueOf(order.getNum()))
                        );
                    }
                }
            }

            // 当前寄养中的数量
            Long currentFosterCount = fosterDao.selectCount(
                    new QueryWrapper<Foster>().eq("status", FosterStatus.Fostering.getInfo())
            );

            StatisticsVO statistics = new StatisticsVO(
                    animalCount, availableCount, userCount, newUserCount,
                    adoptCount, monthAdoptCount, orderCount, totalAmount,
                    fosterCount, currentFosterCount
            );

            return Result.success(statistics);
        } catch (Exception e) {
            return Result.error("-1", "获取统计数据失败：" + e.getMessage());
        }
    }

    /**
     * 获取宠物状态分布数据（如：可领养、已领养等各状态的数量）
     */
    @Override
    public Result getAnimalStatus() {
        try {
            List<Map<String, Object>> statusData = animalDao.selectStatusDistribution();
            return Result.success(statusData);
        } catch (Exception e) {
            return Result.error("-1", "获取宠物状态分布数据失败：" + e.getMessage());
        }
    }

    /**
     * 获取宠物品种分布数据（各品种的宠物数量统计）
     */
    @Override
    public Result getAnimalBreed() {
        try {
            List<Breed> breeds = breedDao.selectList(null);
            List<Map<String, Object>> breedData = new ArrayList<>();

            // 统计每个品种的宠物数量
            for (Breed breed : breeds) {
                Long count = animalDao.selectCount(
                        new QueryWrapper<Animal>().eq("breed", breed.getBreedName())
                );
                if (count > 0) {
                    Map<String, Object> item = new HashMap<>();
                    item.put("breed", breed.getBreedName());
                    item.put("count", count);
                    breedData.add(item);
                }
            }

            // 统计未分类的宠物
            Long otherCount = animalDao.selectCount(
                    new QueryWrapper<Animal>().isNull("breed").or().eq("breed", "")
            );
            if (otherCount > 0) {
                Map<String, Object> otherItem = new HashMap<>();
                otherItem.put("breed", "其他");
                otherItem.put("count", otherCount);
                breedData.add(otherItem);
            }

            return Result.success(breedData);
        } catch (Exception e) {
            return Result.error("-1", "获取宠物品种分布数据失败：" + e.getMessage());
        }
    }
}
