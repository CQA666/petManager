package com.pet.petmanager.controller;
import com.pet.petmanager.entity.Foster;
import com.pet.petmanager.service.FosterService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/foster")
public class FosterController {

    @Autowired
    private FosterService fosterService;

    /**
     * 分页查询寄养记录
     * @param animalName
     * @param userId
     * @param status
     * @param currentPage
     * @param size
     * @return
     */
    @RequestMapping("/selectPage")
    public Result selectPage(
            @RequestParam(defaultValue = "") String animalName,
            @RequestParam(required = false) Integer userId,
            @RequestParam(defaultValue = "") String status,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size) {
        // 调用service层方法
        return fosterService.selectFosterPage(animalName, userId, status, currentPage, size);
    }
    /**
     * 根据id修改寄养记录
     * @param foster
     * @return
     */
    @RequestMapping("/update")
    public Result updateById(@RequestBody Foster foster) {
        return fosterService.updateFosterAndRoom(foster);
    }

    @RequestMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        boolean b = fosterService.removeById(id);
        if (b){
            return Result.success();
        }
        return Result.error("-1","删除失败");
    }
    @RequestMapping("/save")
    public Result save(@RequestBody Foster foster) {
        boolean save = fosterService.save(foster);
        if (save){
            return Result.success();
        }
        return Result.error("-1","添加失败");
    }


}