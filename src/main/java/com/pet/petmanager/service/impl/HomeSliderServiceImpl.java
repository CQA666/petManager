package com.pet.petmanager.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.dao.HomeSliderDao;
import com.pet.petmanager.entity.HomeSlider;
import com.pet.petmanager.service.HomeSliderService;
import com.pet.petmanager.utils.FileUtil;
import com.pet.petmanager.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class HomeSliderServiceImpl extends ServiceImpl<HomeSliderDao, HomeSlider> implements HomeSliderService {
    private final HomeSliderDao homeSliderDao;

    public HomeSliderServiceImpl(HomeSliderDao homeSliderDao) {
        this.homeSliderDao = homeSliderDao;
    }

    @Override
    public Result getHomeSliderPage(Integer page, Integer limit) {
        // 构建分页条件
        Page<HomeSlider> sliderPage = new Page<>(page, limit);
        // 执行分页查询
        Page<HomeSlider> resultPage = homeSliderDao.selectPage(sliderPage, null);
        // 返回结果
        return Result.success(resultPage);
    }

    @Override
    public Result saveHomeSlider(MultipartFile file, String homeSlider) {
        // 检查homeSlider是否为空
        if (homeSlider == null || homeSlider.trim().isEmpty()) {
            return Result.error("-1", "轮播图信息不能为空");
        }
        HomeSlider entity;
        try {
            // 将JSON字符串解析为HomeSlider对象
            entity = JSON.parseObject(homeSlider, HomeSlider.class);
        } catch (Exception e) {
            return Result.error("-1", "轮播图数据格式错误");
        }
        // 保存图片文件并获取路径
        String imagePath = updateImages(file, null);
        entity.setImg(imagePath);
        // 执行数据库插入
        int inserted = homeSliderDao.insert(entity);
        return inserted > 0 ? Result.success() : Result.error("-1", "添加失败");
    }

    @Override
    public Result updateHomeSlider(MultipartFile file, String deletedImg, String homeSlider) {
        // 检查轮播图信息是否为空
        if (homeSlider == null || homeSlider.trim().isEmpty()) {
            return Result.error("-1", "轮播图信息不能为空");
        }
        HomeSlider entity;
        try {
            entity = JSON.parseObject(homeSlider, HomeSlider.class);
        } catch (Exception e) {
            return Result.error("-1", "轮播图数据格式错误");
        }
        if (entity.getId() == null) {
            return Result.error("-1", "轮播图ID不能为空");
        }
        // 如果有新文件上传，替换旧图片
        if (file != null && !file.isEmpty() && file.getSize() > 0) {
            String newImagePath = updateImages(file, deletedImg);
            if (newImagePath != null) {
                entity.setImg(newImagePath);
            }
        }
        // 执行数据库更新
        int updated = homeSliderDao.updateById(entity);
        if (updated > 0) {
            return Result.success();
        } else {
            return Result.error("-1", "更新失败：记录可能不存在");
        }
    }

    @Override
    public Result deleteHomeSliderById(Integer id) {
        if (id == null) {
            return Result.error("-1", "ID不能为空");
        }
        // 先查询原记录，获取旧图片路径
        HomeSlider existing = homeSliderDao.selectById(id);
        if (existing == null) {
            return Result.error("-1", "轮播图不存在，无法删除");
        }
        // 执行数据库删除
        int deleted = homeSliderDao.deleteById(id);
        if (deleted > 0) {
            // 删除本地图片文件
            updateImages(null, existing.getImg());
            return Result.success();
        } else {
            return Result.error("-1", "删除失败");
        }
    }

    @Override
    public Result selectAllSliders() {
        List<HomeSlider> sliders = homeSliderDao.selectList(null);
        return Result.success(sliders);
    }

    /**
     * 图片处理逻辑：
     * - 若 oldImg 非空，则删除旧图；
     * - 若 newFile 非空，则保存新图并返回路径。
     */
    public String updateImages(MultipartFile newFile, String oldImg) {
        if (oldImg != null) {
            String localPath = FileUtil.removePartFromUrlUsingJSON(oldImg, "/api/img/");
            FileUtil.deleteFile(localPath);
        }
        if (newFile != null && !newFile.isEmpty()) {
            return FileUtil.saveImage(newFile, "homeSlider");
        }
        return null;
    }
}