package com.pet.petmanager.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pet.petmanager.VO.AdoptVO;
import com.pet.petmanager.entity.Adopt;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AdoptDao extends BaseMapper<Adopt> {
    //@Param注解用于给参数取别名，在xml中使用时，需要使用别名来引用参数
    Page<AdoptVO> selectByPage(@Param("name") String name, Page<AdoptVO> page);

    Adopt selectByAnimalId(@Param("animal_id") Integer AnimalId);

    List<Map<String, Object>> selectAdoptTrend(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
