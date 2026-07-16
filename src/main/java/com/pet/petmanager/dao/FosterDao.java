package com.pet.petmanager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pet.petmanager.VO.FosterVO;
import com.pet.petmanager.entity.Foster;
import org.apache.ibatis.annotations.Param;

public interface FosterDao extends BaseMapper<Foster> {
    Page<FosterVO> selectByPage(@Param("status") String status,
                                @Param("name") String name,
                                @Param("userId") Integer userId,
                                Page<FosterVO> page);
}
