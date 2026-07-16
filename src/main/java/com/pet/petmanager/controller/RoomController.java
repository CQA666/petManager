package com.pet.petmanager.controller;

import com.pet.petmanager.dao.RoomDao;
import com.pet.petmanager.entity.Room;
import com.pet.petmanager.service.RoomService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomDao roomDao;

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "") String name,
                             @RequestParam(defaultValue = "1")Integer pageNum,
                             @RequestParam(defaultValue = "10")Integer size){
        return roomService.selectPage(name,pageNum,size);
    }

    //批量删除
    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestParam List<Integer> ids){
        int rec = roomDao.deleteBatchIds(ids);
        if (rec==0){
            return Result.error("-1","删除失败请重试");
        }
        return Result.success();
    }

    //更新房间信息
    @PutMapping("/update")
    public Result updateRoom(@RequestBody Room room){
        return roomService.updateRoom(room);
    }

    //添加房间
    @PostMapping("/save")
    public Result saveRoom(@RequestBody Room room){
        return  roomService.saveRoom(room);
    }

    @GetMapping("/selectAll")
    public Result selectAll() {

        List<Room> list = roomService.list();
        return Result.success(list);
    }

}
