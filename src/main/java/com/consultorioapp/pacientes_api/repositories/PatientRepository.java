package com.consultorioapp.pacientes_api.repositories;

import com.consultorioapp.pacientes_api.model.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {

}
