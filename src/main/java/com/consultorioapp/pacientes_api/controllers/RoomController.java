package com.consultorioapp.pacientes_api.controllers;

import com.consultorioapp.pacientes_api.configuration.ErrorResponse;
import com.consultorioapp.pacientes_api.model.Room;
import com.consultorioapp.pacientes_api.services.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping(value = "/api/room")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
public class RoomController {

    @Autowired
    private IRoomService roomService;

    // POST ../api/room
    @PostMapping(value = "")
    public ResponseEntity<?> createRoom(@RequestBody Room room) {
        try {
            Room roomCreated = roomService.createRoom(room);
            return ResponseEntity.ok(roomCreated);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // GET ../api/room
    @GetMapping(value = "")
    public ResponseEntity<?> getAllRooms() {
        try {
            List<Room> rooms = roomService.getAllRooms();
            return ResponseEntity.ok(rooms);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // DELETE ../api/room/{roomId}
    @DeleteMapping(value = "/{roomId}")
    public ResponseEntity<?> deleteRoomById(@PathVariable Long roomId) {
        try {
            boolean deleted = roomService.deleteRoomById(roomId);
            Map<String, Object> response = new HashMap<>();
            response.put("roomId", roomId);
            response.put("message", "Deleted successfully");
            response.put("deleted", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
}
