package com.pet.petmanager.controller;


import com.pet.petmanager.entity.Breed;
import com.pet.petmanager.service.BreedService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/breed")
public class BreedController {
    @Autowired
    private BreedService breedService;

    //查询所有
    @GetMapping("/selectAll")
    public Result selectAll(){

        return Result.success(breedService.list());
    }
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "")String name,
                             @RequestParam(defaultValue = "1")Integer pageNum,
                             @RequestParam(defaultValue = "10")Integer size){
        return breedService.selectPage(name,pageNum,size);
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam List<Integer> ids){
        return breedService.deleteBatch(ids);
    }

    @PostMapping("/save")
    public Result saveBreed(@RequestBody Breed breed){
        return breedService.saveBreed(breed);
    }

    @PutMapping("/update")
    public Result updateBreed(@RequestBody Breed breed){
        return breedService.updateBreed(breed);
    }
}
