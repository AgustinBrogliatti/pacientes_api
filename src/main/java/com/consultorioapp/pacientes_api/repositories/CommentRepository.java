package com.consultorioapp.pacientes_api.repositories;

import com.consultorioapp.pacientes_api.model.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByMedicalRecordId(Long recordId);
}
