package com.pet.petmanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.dao.RoomDao;
import com.pet.petmanager.entity.Room;
import com.pet.petmanager.enums.RoomStatus;
import com.pet.petmanager.service.RoomService;
import com.pet.petmanager.utils.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl extends ServiceImpl<RoomDao, Room> implements RoomService {
    @Autowired
    private RoomDao roomDao;
    @Override
    public Result selectPage(String name, Integer pageNum, Integer size) {

        LambdaQueryWrapper<Room> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name),Room::getName,name);
        Page<Room> roomPage = roomDao.selectPage(new Page<>(pageNum, size), queryWrapper);

        return Result.success(roomPage);
    }

    //修改房间信息
    @Override
    public Result updateRoom(Room room) {
        if (room==null){
            return Result.error("-1","房间信息不存在");
        }
        LambdaQueryWrapper<Room> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Room::getName,room.getName());
        Room room1 = roomDao.selectOne(queryWrapper);

        if (room1!=null&&!room1.getId().equals(room.getId())){
            return Result.error("-1","房间编号重复，请重新更改");
        }
        roomDao.updateById(room);
        return Result.success();
    }

    //保存房间
    @Override
    public Result saveRoom(Room room) {
        if (room==null){
            return Result.error("-1","房间信息不存在，请重新输入");
        }
        LambdaQueryWrapper<Room> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Room::getName,room.getName());
        Room room1 = roomDao.selectOne(queryWrapper);

        if (room1!=null){
            return Result.error("-1","房间编号重复，请重新输入");
        }
        room.setStatus(RoomStatus.EMPTY.getInfo());
        int rec = roomDao.insert(room);
        return rec>0? Result.success(): Result.error("-1","添加失败，请重试");
    }
}
