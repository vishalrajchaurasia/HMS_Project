package com.hms.service;

import com.hms.entity.Room;
import java.util.List;

public interface RoomService {
    Room createRoom(Room room);
    Room getRoomById(Long id);
    List<Room> getAllRooms();
    Room updateRoom(Long id, Room room);
    void deleteRoom(Long id);
}
