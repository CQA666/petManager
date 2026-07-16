package com.pet.petmanager.controller;
import com.pet.petmanager.service.ShowService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/show")
public class ShowController {

    @Autowired
    private ShowService showService;

    /**
     * 获取领养数据趋势
     */
    @RequestMapping("/getAdoptTrend")
    public Result getAdoptTrend() {
        return showService.getAdoptTrend();
    }

    /**
     * 获取系统综合统计数据
     */
    @GetMapping("/getStatistics")
    public Result getStatistics() {
        return showService.getStatistics();
    }

    /**
     * 获取宠物状态分布数据
     */
    @GetMapping("/getAnimalStatus")
    public Result getAnimalStatus() {
        return showService.getAnimalStatus();
    }

    /**
     * 获取宠物品种分布数据
     */
    @GetMapping("/getAnimalBreed")
    public Result getAnimalBreed() {
        return showService.getAnimalBreed();
    }
}
