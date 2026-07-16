package com.pet.petmanager.controller;


import com.pet.petmanager.service.HomeSliderService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/slider")
public class HomeSliderController {

    @Autowired
    private HomeSliderService homeSliderService;

    /**
     * 分页查询轮播图列表
     */
    @RequestMapping("/findAll")
    public Result selectPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer limit) {
        return homeSliderService.getHomeSliderPage(page, limit);
    }

    /**
     * 保存轮播图（含文件上传）
     */
    @RequestMapping("/save")
    public Result save(@RequestParam("files") MultipartFile file,
                       @RequestParam String homeSlider) {
        return homeSliderService.saveHomeSlider(file, homeSlider);
    }

    /**
     * 修改轮播图（含文件上传，支持替换旧图片）
     */
    @RequestMapping("/update")
    public Result updateById(
            @RequestParam("files") MultipartFile file,
            @RequestParam(value = "deletedImg", required = false) String deletedImg,
            @RequestParam String homeSlider) {
        return homeSliderService.updateHomeSlider(file, deletedImg, homeSlider);
    }

    /**
     * 根据id删除轮播图（同时删除图片文件）
     */
    @RequestMapping("delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        return homeSliderService.deleteHomeSliderById(id);
    }

    /**
     * 获取所有轮播图（前台展示用）
     */
    @RequestMapping("/selectAll")
    public Result selectAll() {
        return homeSliderService.selectAllSliders();
    }
}