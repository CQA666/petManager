package com.pet.petmanager.controller;
import com.pet.petmanager.entity.TrainingName;
import com.pet.petmanager.service.TrainingNameService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trainingName")
public class TrainingNameController {

    @Autowired
    private TrainingNameService trainingNameService;

    /**
     * 查询所有培训名称
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/selectPage")
    public Result selectPage(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return trainingNameService.selectPage(name, pageNum, pageSize);
    }

    /**
     * 根据id批量删除培训名称
     * @param ids
     * @return
     */
    @RequestMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam List<Integer> ids) {
        return trainingNameService.deleteBatch(ids);
    }


    /**
     * 保存培训名称
     * @param trainingName
     * @return
     */
    @RequestMapping("/save")
    public Result save(@RequestBody TrainingName trainingName) {
        return trainingNameService.saveTrainingName(trainingName);
    }


    /**
     * 根据id修改培训名称
     * @param trainingName
     * @return
     */
    @RequestMapping("/update")
    public Result updateTrainingName(@RequestBody TrainingName trainingName) {
        return trainingNameService.updateTrainingName(trainingName);
    }

    /**
     * 查询所有培训名称
     * @return
     */
    @RequestMapping("/selectAll")
    public Result selectAll() {
        return trainingNameService.selectAll();
    }
}
