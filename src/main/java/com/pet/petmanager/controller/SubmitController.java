package com.pet.petmanager.controller;
import com.pet.petmanager.service.SubmitService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/submit")
public class SubmitController {

    @Autowired
    private SubmitService submitService;


    @RequestMapping("/page")
    public Result getSubmitsByPage(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size) {
        return submitService.getSubmitsByPage(name, currentPage, size);
    }
    /**
     * 根据id更新上报记录的状态
     * @param id
     * @return
     */
    @RequestMapping("/updateStatus/{id}")
    public Result updateStatus(@PathVariable Integer id) {
        return submitService.updateStatus(id);
    }
}

