package com.pet.petmanager.controller;


import com.pet.petmanager.dao.AnimalDao;
import com.pet.petmanager.entity.Animal;
import com.pet.petmanager.service.AnimalService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    @Autowired
    private AnimalService animalService;


    //分页查询
    @GetMapping("/page")
    public Result selectPage(@RequestParam(defaultValue = "") String name,
                             @RequestParam(defaultValue = "1") Integer PageNum,
                             @RequestParam(defaultValue = "10") Integer size){
        return animalService.selectPage(name,PageNum,size);

    }
    //批量删除
    @DeleteMapping("/deleteBatch")
    public Result deleteAnimals(@RequestParam(defaultValue = "")List<Integer> ids){
        return animalService.deleteAnimals(ids);
    }

    //新增宠物
    @PostMapping("/save")
    public Result saveAnimal(@RequestBody Animal animal){
        return animalService.saveAnimal(animal);
    }

    //修改宠物
    @PutMapping("/update/{id}")
    public Result updateAnimal(@PathVariable Integer id,
                               @RequestBody Animal animal){
        return animalService.updateAnimal(id,animal);
    }


    /**
     * 查询全部动物功能
     * @return
     */
    @RequestMapping("/selectAll")
    public Result selectAll() {
        return animalService.selectAll();
    }
}
