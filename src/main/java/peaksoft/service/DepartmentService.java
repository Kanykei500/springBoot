package peaksoft.service;

import org.springframework.stereotype.Service;
import peaksoft.model.Department;
import peaksoft.model.Doctor;

import javax.print.Doc;
import java.util.List;
@Service

public interface DepartmentService {
    Department save(Long hospitalId,Department newDepartment);
    List<Department> getAllDepartments(Long id);
    Department getDepartmentById(Long id);
    void deleteDepartment(Long id);
    void update( Long id,Department department);
    List<Department> getAllDepartmentsByDoctorId(Doctor doctor);


}
