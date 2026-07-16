package com.pet.petmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pet.petmanager.entity.Room;
import com.pet.petmanager.utils.Result;

public interface RoomService extends IService<Room> {
    //分页查询宠物房间信息
    Result selectPage(String name, Integer pageNum, Integer size);

    //修改房价信息
    Result updateRoom(Room room);

    //保存房间
    Result saveRoom(Room room);
}
