package com.pet.petmanager.controller;

import com.pet.petmanager.entity.User;
import com.pet.petmanager.service.EmailService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
//CrossOrigin注解表示，允许跨域请求
@CrossOrigin
public class EmailController {

    @Autowired
    private EmailService emailService;
    @PostMapping("/sendEmail")
    public Result emailRegister(@RequestBody User user){
        return emailService.emailRegister(user);
    }

    @GetMapping("/findByEmail")
    public Result findByEmail(@RequestParam String email){
        return emailService.findByEmail(email);
    }


}
