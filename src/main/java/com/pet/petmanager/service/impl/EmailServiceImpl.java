package com.pet.petmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pet.petmanager.dao.UserDao;
import com.pet.petmanager.entity.User;
import com.pet.petmanager.service.EmailService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {

    //注入JavaMailSender处理邮件发送的接口
    @Autowired
    private JavaMailSender javaMailSender;

    //@Value注解用于注入配置文件中的属性值
    @Value("${user.fromEmail}")
    private String FROM_EMAIL;

    //注入userDao
    @Autowired
    private UserDao userDao;

    @Override
    public Result emailRegister(User user) {
        // 查询数据库中是否存在相同邮箱的用户
        Long email = userDao.selectCount(new QueryWrapper<User>().eq("email", user.getEmail()));
        // 如果存在相同邮箱的用户，则返回错误信息，提示邮箱已存在
        if(email>0){
            Result.error("-1","邮箱已存在，请勿重复注册");
        }
        // 创建一个随机数生成器
        Random random = new Random();
        // 生成一个6位数的随机验证码，范围在100000到999999之间
        int code= random.nextInt(899999)+100000;
        // 创建一个SimpleMailMessage对象，用于发送邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 设置发件人邮箱地址
        simpleMailMessage.setFrom(FROM_EMAIL);
        // 设置收件人邮箱地址，即用户提供的邮箱地址
        simpleMailMessage.setTo(user.getEmail());
        // 设置邮件主题
        simpleMailMessage.setSubject("管理系统验证码");
        // 设置邮件正文，包含生成的验证码，并提醒用户不要转发
        simpleMailMessage.setText("邮箱验证码为："+code+",请勿转发给他人");
        // 尝试发送邮件
        try{
            // 使用javaMailSender发送邮件
            javaMailSender.send(simpleMailMessage);
            // 如果邮件发送成功，则返回成功的结果，包含验证码
            return Result.success(code);
            // 捕获并处理邮件发送过程中可能出现的异常
        }catch (Exception e){
            // 如果邮件发送失败，则返回错误信息，提示验证码发送异常，并建议联系管理员
            return Result.error("-1","验证码发送异常，请联系管理员。");
        }
    }

    @Override
    public Result findByEmail(String email) {
        Long email1 = userDao.selectCount(new QueryWrapper<User>().eq("email", email));
        // 如果存在相同邮箱的用户，则返回错误信息，提示邮箱已存在
        if(email1>0){
            Result.error("-1","邮箱已存在，请勿重复注册");
        }
        // 创建一个随机数生成器
        Random random = new Random();
        // 生成一个6位数的随机验证码，范围在100000到999999之间
        int code= random.nextInt(899999)+100000;
        // 创建一个SimpleMailMessage对象，用于发送邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 设置发件人邮箱地址
        simpleMailMessage.setFrom(FROM_EMAIL);
        // 设置收件人邮箱地址，即用户提供的邮箱地址
        simpleMailMessage.setTo(email);
        // 设置邮件主题
        simpleMailMessage.setSubject("管理系统验证码");
        // 设置邮件正文，包含生成的验证码，并提醒用户不要转发
        simpleMailMessage.setText("邮箱验证码为："+code+",请勿转发给他人");
        // 尝试发送邮件
        try{
            // 使用javaMailSender发送邮件
            javaMailSender.send(simpleMailMessage);
            // 如果邮件发送成功，则返回成功的结果，包含验证码
            return Result.success(code);
            // 捕获并处理邮件发送过程中可能出现的异常
        }catch (Exception e){
            // 如果邮件发送失败，则返回错误信息，提示验证码发送异常，并建议联系管理员
            return Result.error("-1","验证码发送异常，请联系管理员。");
        }

    }
}
