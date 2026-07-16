package com.pet.petmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.Notice;
import com.pet.petmanager.utils.Result;

import java.util.List;

public interface NoticeService extends IService<Notice> {
    Result getNoticesByPage(String title, Integer currentPage, Integer size);

    Result deleteBatch(List<Integer> ids);

    Result saveNotice(Notice notice);

    Result updateNotice(Notice notice);

    Result getLatestNotices(Integer count);
}