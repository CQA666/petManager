package com.pet.petmanager.enums;

public enum RoomStatus {
    USING("使用中"),
    EMPTY("闲置");
    private String info;

    RoomStatus(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
