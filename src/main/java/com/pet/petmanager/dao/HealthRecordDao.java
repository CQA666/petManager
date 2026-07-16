package com.pet.petmanager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pet.petmanager.VO.HealthRecordVO;
import com.pet.petmanager.entity.HealthRecord;
import org.apache.ibatis.annotations.Param;

public interface HealthRecordDao extends BaseMapper<HealthRecord> {
    Page<HealthRecordVO> selectByPage(@Param("petName")String petName , @Param("recordDate") String recordDate, Page<HealthRecordVO> page);
}
