package com.consultorioapp.pacientes_api.controllers;

import com.consultorioapp.pacientes_api.Dto.MedicalRecordDetailsDto;
import com.consultorioapp.pacientes_api.Dto.MedicalRecordPreviewDto;
import com.consultorioapp.pacientes_api.Dto.MedicalRecordHealthDto;
import com.consultorioapp.pacientes_api.configuration.ErrorResponse;
import com.consultorioapp.pacientes_api.model.MedicalRecord;
import com.consultorioapp.pacientes_api.model.Patient;
import com.consultorioapp.pacientes_api.services.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/medical-record")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class RecordController {

    @Autowired
    private IRecordService recordService;

    // POST ../api/medical-record
    @PostMapping(value = "")
    public ResponseEntity<?> createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        try {
            MedicalRecordDetailsDto createdRecord = recordService.createMedicalRecord(medicalRecord);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRecord);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getRecords() {
        try {
            List<MedicalRecordPreviewDto> records = recordService.getRecords();
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // GET ../api/medical-record/dni?dni
    @GetMapping("/dni")
    public ResponseEntity<?> getRecordsByDni(@RequestParam String dni) {
        try {
            List<MedicalRecordPreviewDto> records = recordService.getRecordsByDni(dni);
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/lastname")
    public ResponseEntity<?> getRecordsByLastName(@RequestParam String lastname) {
        try {
            List<MedicalRecordPreviewDto> records = recordService.getRecordsByLastName(lastname);
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/fullname")
    public ResponseEntity<?> getRecordsByFullName(@RequestParam String fullName) {
        try {
            List<MedicalRecordPreviewDto> records = recordService.getRecordsByFullName(fullName);
            return ResponseEntity.ok(records);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecordDetails(@PathVariable Long id) {
        try {
            MedicalRecordDetailsDto recordDetails = recordService.getRecordDetails(id);
            return ResponseEntity.ok(recordDetails);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/{id}/patient")
    public ResponseEntity<?> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        try {
            Patient patientUpdated = recordService.updatePatient(id, patient);
            return ResponseEntity.ok(patientUpdated);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> updateInfo(@RequestBody MedicalRecordHealthDto newRecordData) {
        try {
            MedicalRecordDetailsDto recordUpdated = recordService.updateRecordInfo(newRecordData);
            return ResponseEntity.ok(recordUpdated);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecord(@PathVariable Long id) {
        try {
            recordService.deleteRecordById(id);
            Map<String, Object> response = new HashMap<>();
            response.put("id", id);
            response.put("message", "Deleted successfully");
            response.put("deleted", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
