package com.consultorioapp.pacientes_api.repository;
import com.consultorioapp.pacientes_api.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
