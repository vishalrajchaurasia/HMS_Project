package com.hms.service.impl;

import com.hms.entity.Room;
import com.hms.repository.RoomRepository;
import com.hms.service.RoomService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room createRoom(Room room) {
        if (room.getCount() < 1) {
            throw new IllegalArgumentException("Room count must be at least 1");
        }
        return roomRepository.save(room);
    }

    @Override
    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room updateRoom(Long id, Room room) {
        Room existingRoom = getRoomById(id);
        if (room.getCount() < 1) {
            throw new IllegalArgumentException("Room count must be at least 1");
        }
        existingRoom.setType(room.getType());
        existingRoom.setCount(room.getCount());
        existingRoom.setProperty(room.getProperty());
        return roomRepository.save(existingRoom);
    }

    @Override
    public void deleteRoom(Long id) {
        Room room = getRoomById(id);
        roomRepository.delete(room);
    }
}
