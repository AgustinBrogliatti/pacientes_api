package com.consultorioapp.pacientes_api.services;

import com.consultorioapp.pacientes_api.Dto.CommentDto;
import com.consultorioapp.pacientes_api.model.Comment;
import com.consultorioapp.pacientes_api.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    @Transactional
    public boolean createComment(Comment comment) {
        commentRepository.save(comment);
        return true;
    }

    @Override
    @Transactional
    public boolean updateComment(Comment comment) {
        Optional<Comment> existingComment = commentRepository.findById(comment.getId());
        if (existingComment.isPresent()) {
            commentRepository.save(comment);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteComment(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByRecordId(Long recordId) {
        List<Comment> comments = commentRepository.findByMedicalRecordId(recordId);
        return comments.stream()
                .map(comment -> new CommentDto(
                        comment.getId(),
                        comment.getBody(),
                        comment.getDate(),
                        comment.getMedicalRecord().getDoctor().getFullName(),
                        comment.getMedicalRecord().getDoctor().getId()
                ))
                .collect(Collectors.toList());
    }
}
