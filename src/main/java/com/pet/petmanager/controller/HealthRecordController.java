package com.pet.petmanager.controller;
import com.pet.petmanager.entity.HealthRecord;
import com.pet.petmanager.service.HealthRecordService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/health-record")
public class HealthRecordController {

    @Autowired
    private HealthRecordService healthRecordService;


    /**
     * 获取健康记录列表
     * @param petName
     * @param recordDate
     * @param currentPage
     * @param size
     * @return
     */
    @RequestMapping("/page")
    public Result getHealthRecordByPage(
            @RequestParam(defaultValue = "") String petName,
            @RequestParam(defaultValue = "") String recordDate,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size) {
        return healthRecordService.getHealthRecordByPage(petName, recordDate, currentPage, size);
    }

    @RequestMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam List<Long> ids) {
        return healthRecordService.deleteBatchByIds(ids);
    }

    @RequestMapping("/save")
    public Result save(@RequestBody HealthRecord healthRecord) {
        return healthRecordService.addHealthRecord(healthRecord);
    }

    @RequestMapping("/update")
    public Result updateById(@RequestBody HealthRecord healthRecord) {
        return healthRecordService.updateHealthRecord(healthRecord);
    }

    @RequestMapping("/selectByPetId/{id}")
    public Result selectByPetId(@PathVariable Long id) {
        // 只调用 Service，不写业务逻辑
        return healthRecordService.selectByPetId(id);
    }

}