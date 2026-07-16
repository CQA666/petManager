package com.pet.petmanager.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.Submit;
import com.pet.petmanager.utils.Result;

public interface SubmitService extends IService<Submit> {
    Result getSubmitsByPage(String name, Integer currentPage, Integer size);

    Result updateStatus(Integer id);
}
