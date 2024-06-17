package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.Dto.CommentDto;
import com.consultorioapp.pacientes_api.model.Comment;

import java.util.List;

public interface ICommentService {
    boolean createComment(Comment comment);
    boolean updateComment(Comment comment);
    boolean deleteComment(Long commentId);
    List<CommentDto> getCommentsByRecordId(Long recordId);
}
