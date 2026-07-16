package com.pet.petmanager.controller;
import com.pet.petmanager.service.IconDictService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/icon-dict")
public class IconDictController {

    @Autowired
    private IconDictService iconDictService;

    /**
     * 查询所有字典项
     */
    @RequestMapping("/all")
    public Result getAll() {
        return iconDictService.getAllDictItems();
    }
}