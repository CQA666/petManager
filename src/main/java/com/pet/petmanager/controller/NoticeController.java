package com.pet.petmanager.controller;

import com.pet.petmanager.entity.Notice;
import com.pet.petmanager.service.NoticeService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 分页查询通知列表
     * @param title 通知标题（可选，模糊查询）
     * @param currentPage 当前页码
     * @param size 每页条数
     */
    @RequestMapping("/page")
    public Result getNoticesByPage(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "1") Integer currentPage,
            @RequestParam(defaultValue = "10") Integer size) {
        return noticeService.getNoticesByPage(title, currentPage, size);
    }

    /**
     * 批量删除通知
     * @param ids 要删除的通知ID列表
     */
    @RequestMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam List<Integer> ids) {
        return noticeService.deleteBatch(ids);
    }

    /**
     * 保存通知
     * @param notice 通知实体（JSON格式请求体）
     */
    @RequestMapping("/save")
    public Result save(@RequestBody Notice notice) {
        return noticeService.saveNotice(notice);
    }

    /**
     * 修改通知
     * @param notice 通知实体（JSON格式请求体，必须包含id）
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Notice notice) {
        return noticeService.updateNotice(notice);
    }

    /**
     * 获取最新通知列表（前台展示用）
     * @param count 获取数量，默认10条
     */
    @RequestMapping("/limit")
    public Result getLatestNotices(@RequestParam(defaultValue = "10") Integer count) {
        return noticeService.getLatestNotices(count);
    }
}