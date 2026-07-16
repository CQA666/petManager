package com.pet.petmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.dao.NoticeDao;
import com.pet.petmanager.entity.Notice;
import com.pet.petmanager.service.NoticeService;
import com.pet.petmanager.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeDao, Notice> implements NoticeService {

    @Autowired
    private NoticeDao noticeDao;

    @Override
    public Result getNoticesByPage(String title, Integer currentPage, Integer size) {
        // 创建LambdaQueryWrapper条件构造器
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        // 判断是否有标题条件
        if (StringUtils.isNotBlank(title)) {
            // 标题模糊查询
            wrapper.like(Notice::getTitle, title);
        }
        // 设置分页条件
        Page<Notice> page = new Page<>(currentPage, size);
        // 执行分页查询
        Page<Notice> resultPage = noticeDao.selectPage(page, wrapper);
        // 返回分页结果
        return Result.success(resultPage);
    }

    @Override
    public Result deleteBatch(List<Integer> ids) {
        // 判断是否有ID列表
        if (ids == null || ids.isEmpty()) {
            return Result.error("-1", "ID列表不能为空");
        }
        // 调用Mybatis-Plus批量删除方法
        int deletedCount = noticeDao.deleteBatchIds(ids);
        // 判断是否删除成功
        if (deletedCount > 0) {
            return Result.success();
        } else {
            return Result.error("-1", "删除失败：未找到对应的通知记录");
        }
    }

    @Override
    public Result saveNotice(Notice notice) {
        // 判断通知内容是否为空
        if (notice == null) {
            return Result.error("-1", "通知内容不能为空");
        }
        // 调用Mybatis-Plus保存方法
        int inserted = noticeDao.insert(notice);
        // 判断是否保存成功
        if (inserted > 0) {
            return Result.success();
        } else {
            return Result.error("-1", "新增失败");
        }
    }

    @Override
    public Result updateNotice(Notice notice) {
        // 判断通知ID是否为空
        if (notice == null || notice.getId() == null) {
            return Result.error("-1", "通知ID不能为空");
        }
        // 调用Mybatis-Plus根据ID更新方法
        int updated = noticeDao.updateById(notice);
        // 判断是否更新成功
        if (updated > 0) {
            return Result.success();
        } else {
            return Result.error("-1", "修改失败：通知可能不存在或未修改任何字段");
        }
    }

    @Override
    public Result getLatestNotices(Integer count) {
        // 构造分页对象：第1页，每页 count 条
        Page<Notice> page = new Page<>(1, count);
        // 按 time 字段倒序（由近到远）
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("time");
        // 执行分页查询
        Page<Notice> resultPage = this.page(page, queryWrapper);
        List<Notice> notices = resultPage.getRecords();
        return Result.success(notices);
    }
}
