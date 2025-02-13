package com.camp.reservation.reservation.room.controller;

import com.camp.reservation.reservation.room.dto.request.RoomAvailabilityRequestDTO;
import com.camp.reservation.reservation.room.dto.response.RoomResponseDTO;
import com.camp.reservation.reservation.room.entity.Room;
import com.camp.reservation.reservation.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @PostMapping("/available")
    public ResponseEntity<List<Room>> findAvailableRooms(@RequestBody RoomAvailabilityRequestDTO requestDTO) {
        List<Room> availableRooms = roomService.findAvailableRooms(requestDTO);
        return ResponseEntity.ok(availableRooms);
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomResponseDTO> getRoomById(@PathVariable("roomId") Long roomId) {
        Optional<Room> roomOptional = roomService.findRoomById(roomId);
        return roomOptional.map(room -> ResponseEntity.ok(convertToDTO(room)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private RoomResponseDTO convertToDTO(Room room) {
        RoomResponseDTO dto = new RoomResponseDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setCapacity(room.getCapacity());
        dto.setImage(room.getImage());
        dto.setExtraInfo(room.getExtraInfo());
        return dto;
    }
}
