package com.pet.petmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.HomeSlider;
import com.pet.petmanager.utils.Result;
import org.springframework.web.multipart.MultipartFile;

public interface HomeSliderService extends IService<HomeSlider> {
    Result getHomeSliderPage(Integer page, Integer limit);

    Result saveHomeSlider(MultipartFile file, String homeSlider);

    Result updateHomeSlider(MultipartFile file, String deletedImg, String homeSlider);

    Result deleteHomeSliderById(Integer id);

    Result selectAllSliders();
}