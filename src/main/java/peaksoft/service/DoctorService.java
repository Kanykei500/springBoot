package peaksoft.service;

import org.springframework.stereotype.Service;
import peaksoft.model.Doctor;

import java.util.List;
@Service

public interface DoctorService {
    Doctor save(Long hospitalId,Doctor newDoctor);
    List<Doctor> getAllDoctors(Long id);
    Doctor getDoctorById(Long id);
    void deleteDoctor(Long id);
    void update( Long id,Doctor newDoctor);
    void assignDepartmentsToDoctor(List<Long>departmentIds, Long doctorId);


}
