package com.pet.petmanager.controller;
import com.pet.petmanager.entity.PetGroomingService;
import com.pet.petmanager.service.PetGroomingServiceService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/petGroomingService")
public class PetGroomingServiceController {

    @Autowired
    private PetGroomingServiceService petGroomingServiceService;

    /**
     * 查询宠物理疗服务列表
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
        return petGroomingServiceService.selectPage(petName, username, pageNum, pageSize);
    }

    /**
     * 根据id批量删除宠物理疗服务
     * @param ids
     * @return
     */
    @RequestMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam List<Integer> ids) {
        return petGroomingServiceService.deleteBatch(ids);
    }


    @RequestMapping("/update")
    public Result updateById(@RequestBody PetGroomingService petGroomingService) {
        return petGroomingServiceService.updatePetGroomingService(petGroomingService);
    }

    @RequestMapping("/save")
    public Result save(@RequestBody PetGroomingService petGroomingService) {
        return petGroomingServiceService.savePetGroomingService(petGroomingService);
    }

}
