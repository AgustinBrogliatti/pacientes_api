package com.consultorioapp.pacientes_api.controllers;

import com.consultorioapp.pacientes_api.Dto.CommentDto;
import com.consultorioapp.pacientes_api.configuration.ErrorResponse;
import com.consultorioapp.pacientes_api.model.Comment;
import com.consultorioapp.pacientes_api.services.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/medical-record/comments")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class CommentController {
    @Autowired
    private ICommentService commentService;

    // POST /api/medical-record/comments
    @PostMapping(value = "")
    public ResponseEntity<?> createComment(@RequestBody Comment comment) {
        try {
            boolean isCreated = commentService.createComment(comment);
            if (isCreated) {
                return ResponseEntity.status(HttpStatus.CREATED).body(comment);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Creation failed", "Unable to create comment");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // PUT /api/medical-record/comments
    @PutMapping(value = "")
    public ResponseEntity<?> updateComment(@RequestBody Comment comment) {
        try {
            boolean isUpdated = commentService.updateComment(comment);
            if (isUpdated) {
                return ResponseEntity.ok(comment);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Update failed", "Comment not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // DELETE /api/medical-record/comments/{commentId}
    @DeleteMapping(value = "/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        try {
            boolean isDeleted = commentService.deleteComment(commentId);
            if (isDeleted) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Deletion failed", "Comment not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // GET /api/medical-record/comments/{recordId}
    @GetMapping(value = "/{recordId}")
    public ResponseEntity<?> getCommentsByRecordId(@PathVariable Long recordId) {
        try {
            List<CommentDto> comments = commentService.getCommentsByRecordId(recordId);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
