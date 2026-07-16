package com.pet.petmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.petmanager.dao.AnimalDao;
import com.pet.petmanager.dao.PetAiChatDao;
import com.pet.petmanager.entity.Animal;
import com.pet.petmanager.entity.PetAiChat;
import com.pet.petmanager.service.PetAiChatService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class PetAiChatServiceImpl extends ServiceImpl<PetAiChatDao, PetAiChat> implements PetAiChatService {

    // JDK 自带 HTTP 客户端
    private final HttpClient httpClient = HttpClient.newHttpClient();

    // Java 对象与 JSON 互相转换
    private final ObjectMapper objectMapper = new ObjectMapper();

    // 操作 pet_ai_chat 表
    @Autowired
    private PetAiChatDao petAiChatDao;

    // 操作 animal 表
    @Autowired
    private AnimalDao animalDao;

    // DeepSeek API 请求地址
    @Value("${deepseek.api-url}")
    private String apiUrl;

    // DeepSeek 模型名称
    @Value("${deepseek.model}")
    private String model;

    // DeepSeek API 密钥
    @Value("${deepseek.api-key}")
    private String apiKey;

    @Override
    public Result chat(Integer userId, Integer petId, String message) {

        // 根据 petId 查询宠物信息
        Animal pet = animalDao.selectById(petId);

        // 宠物不存在则返回错误
        if (pet == null) {
            return Result.error("-1", "宠物不存在");
        }

        // 获取当前时间，格式化为中文日期字符串
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm"));

        // 拼装 system 提示词，告诉 AI 它是一只宠物
        // Objects.toString(v, "") 用于防止字段为 null 时出现 "null" 字样
        String systemPrompt = String.format(
                "当前时间是：%s。你现在是一只名叫%s的%s岁%s%s。性格特征是：%s。" +
                        "你必须以这只宠物的身份和口吻回复用户的所有消息，不能以助手或人类的身份回复。" +
                        "如果用户询问时间相关问题，请基于当前时间回答。",
                now,
                Objects.toString(pet.getName(), ""),
                Objects.toString(pet.getAge(), ""),
                Objects.toString(pet.getSex(), ""),
                Objects.toString(pet.getBreed(), ""),
                Objects.toString(pet.getBehaviorTraits(), "")
        );

        try {
            // 构建请求体，序列化为 JSON 字符串
            // Map.of 一行创建不可变 Map，比 new HashMap + put 更简洁
            String jsonBody = objectMapper.writeValueAsString(Map.of(
                    "model", model,
                    "user", "user_" + userId,
                    // messages 包含两条消息：system 设身份，user 放输入
                    "messages", List.of(
                            Map.of("role", "system", "content", systemPrompt),
                            Map.of("role", "user", "content", message)
                    ),
                    // 控制生成文字的随机性，0.7 是常用平衡值
                    "temperature", 0.7,
                    // 只在概率最高的 4 个词中采样，让回复更聚焦
                    "top_k", 4,
                    // 关闭流式输出，一次性返回完整结果
                    "stream", false,
                    // 限制最大生成 1024 个 token
                    "max_tokens", 1024
            ));

            // 构建 HTTP POST 请求
            HttpRequest request = HttpRequest.newBuilder()
                    // 设置请求地址
                    .uri(URI.create(apiUrl))
                    // 声明请求体为 JSON 格式
                    .header("Content-Type", "application/json")
                    // Bearer Token 鉴权
                    .header("Authorization", "Bearer " + apiKey)
                    // 设置请求方法为 POST，BodyPublishers.ofString 将字符串转为请求体
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // 同步发送请求，BodyHandlers.ofString() 将响应体转为字符串
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // 解析响应 JSON，提取 AI 回复文本
            // path("choices") 进入 choices 字段（不存在时不会 NPE）
            // .get(0) 取数组第一个元素
            // .path("message").path("content") 进入 content 拿到回复内容
            // .asText() 转为字符串
            String aiResponse = objectMapper.readTree(response.body())
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            // 创建聊天记录对象
            PetAiChat record = new PetAiChat();
            // 设置用户 ID
            record.setUserId(userId);
            // 设置宠物 ID
            record.setPetId(petId);
            // 设置用户原始消息
            record.setMessage(message);
            // 设置 AI 回复内容
            record.setResponse(aiResponse);
            // 设置创建时间为当前时间
            record.setCreatedAt(LocalDateTime.now());
            // 写入数据库，自动回填主键 ID
            petAiChatDao.insert(record);

            // 返回成功结果：{"code":"0","msg":"成功","data":record}
            return Result.success(record);

        } catch (Exception e) {
            // 统一捕获异常，返回错误格式
            return Result.error("-1", "调用DeepSeek API失败：" + e.getMessage());
        }
    }
}
