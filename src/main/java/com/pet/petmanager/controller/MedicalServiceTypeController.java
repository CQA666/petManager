package com.pet.petmanager.controller;

import com.pet.petmanager.service.MedicalServiceTypeService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicalServiceType")
public class MedicalServiceTypeController {

    @Autowired
    private MedicalServiceTypeService medicalServiceTypeService;

    /**
     * 查询所有医疗服务类型
     * @return
     */
    @RequestMapping("/selectAll")
    public Result selectAll() {
        return medicalServiceTypeService.selectAll();
    }
}