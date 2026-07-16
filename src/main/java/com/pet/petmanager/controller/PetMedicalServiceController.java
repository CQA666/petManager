package com.pet.petmanager.controller;
import com.pet.petmanager.entity.PetMedicalService;
import com.pet.petmanager.service.PetMedicalServiceService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/petMedicalService")
public class PetMedicalServiceController {

    @Autowired
    private PetMedicalServiceService petMedicalServiceService;

    /**
     * 查询宠物医疗服务列表
     * @param petName
     * @param username
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/selectPage")
    public Result selectPage(
            @RequestParam(defaultValue = "") String petName,
            @RequestParam(defaultValue = "") String username,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return petMedicalServiceService.selectPage(petName, username, pageNum, pageSize);
    }

    /**
     * 根据id批量删除宠物医疗服务
     * @param ids
     * @return
     */
    @RequestMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam List<Integer> ids) {
        return petMedicalServiceService.deleteBatchStrict(ids);
    }

    /**
     * 根据id更改宠物医疗服务
     * @param petMedicalService
     * @return
     */
    @RequestMapping("/update")
    public Result updateById(@RequestBody PetMedicalService petMedicalService) {
        return petMedicalServiceService.updatePetMedicalService(petMedicalService);
    }

    @RequestMapping("/save")
    public Result save(@RequestBody PetMedicalService petMedicalService) {
        return petMedicalServiceService.create(petMedicalService);
    }
}