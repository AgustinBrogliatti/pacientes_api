package com.consultorioapp.pacientes_api.repositories;
import com.consultorioapp.pacientes_api.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

}
