package com.pet.petmanager.controller;
import com.pet.petmanager.entity.PetTrainingService;
import com.pet.petmanager.service.PetTrainingServiceService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/petTrainingService")
public class PetTrainingServiceController {

    @Autowired
    private PetTrainingServiceService petTrainingServiceService;

    /**
     * 根据条件查询分页数据
     * @param serviceType
     * @param username
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/selectPage")
    public Result selectPage(
            @RequestParam(defaultValue = "") String serviceType,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return petTrainingServiceService.selectPage(serviceType, username, pageNum, pageSize);
    }

    /**
     * 根据id修改训练服务
     * @param petTrainingService
     * @return
     */
    @RequestMapping("/update")
    public Result updatePetTrainingService(@RequestBody PetTrainingService petTrainingService) {
        return petTrainingServiceService.updatePetTrainingService(petTrainingService);
    }

    /**
     * 根据id删除训练服务
     * @param ids
     * @return
     */
    @RequestMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam List<Integer> ids) {
        return petTrainingServiceService.deleteBatch(ids);
    }

    @RequestMapping("/save")
    public Result save(@RequestBody PetTrainingService petTrainingService) {
        return petTrainingServiceService.savePetTrainingService(petTrainingService);
    }
}