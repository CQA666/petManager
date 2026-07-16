package com.pet.petmanager.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.VO.AdoptVO;
import com.pet.petmanager.dao.AdoptDao;
import com.pet.petmanager.dao.AnimalDao;
import com.pet.petmanager.entity.Adopt;
import com.pet.petmanager.entity.Animal;
import com.pet.petmanager.enums.AnimalEnum;
import com.pet.petmanager.service.AdoptService;
import com.pet.petmanager.service.AnimalService;
import com.pet.petmanager.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class AdoptServiceImpl extends ServiceImpl<AdoptDao, Adopt> implements AdoptService {


    @Autowired
    private AdoptDao adoptDao;

    @Autowired
    private AnimalService animalService;
    @Autowired
    private AnimalDao animalDao;

    //领养分页查询
    @Override
    public Result selectPage(String name, Integer currentPage, Integer size) {
        // 创建Page对象,设置当前页和每页显示的数量
        Page<AdoptVO> page = new Page<>(currentPage, size);
        // 调用AdoptDao的selectByPage方法,传入name和Page对象,返回Page对象
        Page<AdoptVO> adoptPage = adoptDao.selectByPage(name, page);
        // 返回封装了Page对象和数据的Result对象
        return Result.success(adoptPage);

    }

    @Override
    public Result updateAdopt(AdoptVO adoptVO) {
        //判断参数是否为空
        if (adoptVO == null) {
            return Result.error("-1", "参数不能为空");
        }
        //判断是否更改为待领养
        if (AnimalEnum.ADOPT_CANCEL.getInfo().equals(adoptVO.getStatus())){
            //更改animal表中的status为待领养
            Animal animal = animalService.getById(adoptVO.getAnimalId());
            //判断宠物是否存在
            if (animal == null) {
                return Result.error("-1", "宠物不存在");
            }
            animal.setStatus(adoptVO.getStatus());
            animalDao.updateById(animal);
        }
        //判断名字是否为空
        if (!StringUtils.isNoneBlank(adoptVO.getAnimalName())){
           return Result.error("-1","名字不能为空");
        }
        Adopt adopt = adoptDao.selectById(adoptVO.getId());
        Animal animal1 = animalDao.selectById(adopt.getAnimalId());
        //如果修改了名字要修改animal表
        if (!adoptVO.getAnimalName().equals(animal1.getName())){
            animal1.setName(adoptVO.getAnimalName());
            animalDao.updateById(animal1);
        }
        //不是放弃的话就修改adopt表
        int i = adoptDao.updateById(adoptVO);
        if (i==0){
            return Result.error("-1","保存错误，请重试");
        }
        return Result.success("操作成功");
    }

    @Override
    public Result getAdoptTrend() {
        //
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusYears(1);
        List<Map<String, Object>> trendData = adoptDao.selectAdoptTrend(startDate.toString(), endDate.toString());
        return Result.success(trendData);
    }
}
