package com.consultorioapp.pacientes_api.controllers;

import com.consultorioapp.pacientes_api.configuration.ErrorResponse;
import com.consultorioapp.pacientes_api.model.Room;
import com.consultorioapp.pacientes_api.services.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@RestController
@RequestMapping(value = "/api/room")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class RoomController {

    @Autowired
    private IRoomService roomService;

    // POST ../api/room
    @PostMapping(value = "")
    public ResponseEntity<?> createRoom(@RequestBody Map<String, String> requestBody) {
        String roomName = requestBody.get("roomName");
        try {
            Room room = roomService.createRoom(roomName);
            return ResponseEntity.ok(room);
        } catch (IllegalArgumentException e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // GET ../api/room
    @GetMapping(value = "")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

    // DELETE ../api/room/{roomId}
    @DeleteMapping(value = "/{roomId}")
    public boolean deleteRoomById(@PathVariable Long roomId) {
        return roomService.deleteRoomById(roomId);
    }
}
