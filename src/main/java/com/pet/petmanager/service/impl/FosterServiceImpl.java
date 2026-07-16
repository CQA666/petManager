package com.pet.petmanager.service.impl;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pet.petmanager.VO.FosterVO;
import com.pet.petmanager.dao.FosterDao;
import com.pet.petmanager.dao.RoomDao;
import com.pet.petmanager.entity.Foster;
import com.pet.petmanager.entity.Room;
import com.pet.petmanager.enums.FosterStatus;
import com.pet.petmanager.enums.RoomStatus;
import com.pet.petmanager.service.FosterService;
import com.pet.petmanager.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FosterServiceImpl extends ServiceImpl<FosterDao, Foster> implements FosterService {

    @Autowired
    private FosterDao fosterDao;
    @Autowired
    private RoomDao roomDao;

    @Override
    public Result selectFosterPage(String animalName, Integer userId, String status, Integer currentPage, Integer size) {
        // 前端约定 userId = -1 表示查询所有用户，这里转为 null 跳过该过滤条件
        if (userId != null && userId == -1) {
            userId = null;
        }
        // 使用 MybatisPlus 的分页插件 Page 构建分页对象
        Page<FosterVO> page = new Page<>(currentPage, size);
        // 调用fosterDao 的 selectByPage 方法
        Page<FosterVO> fosterPage = fosterDao.selectByPage(status, animalName, userId, page);
        // 将分页对象转换为 Result 对象
        return Result.success(fosterPage);
    }

    @Override
    public Result updateFosterAndRoom(Foster foster) {
        if (foster == null || foster.getId() == null) {
            return Result.error("-1", "寄养记录ID不能为空");
        }

        Foster old = fosterDao.selectById(foster.getId());
        if (old == null) {
            return Result.error("-1", "寄养记录不存在");
        }

        // 使用 UpdateWrapper 精准控制更新
        UpdateWrapper<Foster> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", foster.getId());
        updateWrapper.set("status", foster.getStatus());
        updateWrapper.set("name", foster.getName());
        updateWrapper.set("time", foster.getTime());
        updateWrapper.set("days", foster.getDays());
        updateWrapper.set("user_id", foster.getUserId());

        if (FosterStatus.END.getInfo().equals(foster.getStatus())) {
            // 寄养结束：room_id 置空，释放原房间
            updateWrapper.set("room_id", null);
            fosterDao.update(null, updateWrapper);

            // 释放房间：设置为闲置，清空宠物名
            if (old.getRoomId() != null) {
                Room room = roomDao.selectById(old.getRoomId());
                if (room != null) {
                    room.setStatus(RoomStatus.EMPTY.getInfo());
                    room.setAnimal(null);
                    if (roomDao.updateById(room) <= 0) {
                        return Result.error("-1", "房间释放失败");
                    }
                }
            }
            return Result.success();
        }

        // 非结束状态：更新 room_id
        updateWrapper.set("room_id", foster.getRoomId());
        fosterDao.update(null, updateWrapper);

        // ---- 处理房间变更 ----
        Integer newRoomId = foster.getRoomId();
        Integer oldRoomId = old.getRoomId();

        // 房间未变更，无需处理
        if (Objects.equals(oldRoomId, newRoomId)) {
            return Result.success();
        }

        // 释放原房间：设置为闲置，清空宠物名
        if (oldRoomId != null) {
            Room oldRoom = roomDao.selectById(oldRoomId);
            if (oldRoom != null) {
                oldRoom.setStatus(RoomStatus.EMPTY.getInfo());
                oldRoom.setAnimal(null);
                if (roomDao.updateById(oldRoom) <= 0) {
                    return Result.error("-1", "原房间释放失败");
                }
            }
        }

        // 分配新房间
        if (newRoomId == null) {
            return Result.success();
        }

        Room newRoom = roomDao.selectById(newRoomId);
        if (newRoom == null) {
            return Result.error("-1", "指定的新房间不存在");
        }
        if (!RoomStatus.EMPTY.getInfo().equals(newRoom.getStatus())) {
            return Result.error("-1", "新房间已被占用！");
        }

        newRoom.setStatus(RoomStatus.USING.getInfo());
        newRoom.setAnimal(foster.getName());
        if (roomDao.updateById(newRoom) <= 0) {
            return Result.error("-1", "新房间分配失败");
        }

        return Result.success();
    }
}
