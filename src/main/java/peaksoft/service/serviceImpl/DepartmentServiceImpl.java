package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.exceptions.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Department;
import peaksoft.model.Doctor;
import peaksoft.model.Hospital;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.DepartmentRepository;
import peaksoft.repository.DoctorRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.DepartmentService;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final HospitalRepository hospitalRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;

    @Override
    public Department save(Long hospitalId, Department newDepartment) {
        Hospital hospital = hospitalRepository.findById(hospitalId).get();
        newDepartment.setHospital(hospital);
        return departmentRepository.save(newDepartment);
    }

    @Override
    public List<Department> getAllDepartments(Long id) {
        return departmentRepository.getAllDepartments(id);
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }

    @Override
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).get();
        List<Appointment> appointments = department.getHospital().getAppointments();
        List<Appointment> appointmentList = appointments.stream().filter(s -> s.getDepartment().getId().equals(id)).toList();
        appointmentList.forEach(s -> appointmentRepository.deleteById(s.getId()));
        List<Department> departments = department.getHospital().getDepartments();
        departments.removeIf(s -> s.getId().equals(id));
        departmentRepository.deleteById(id);

    }

    @Override
    public void update(Long id, Department department) {
        Department department1 = departmentRepository.findById(id).get();
        department1.setName(department.getName());
        departmentRepository.save(department1);


    }

    @Override
   public List<Department> getAllDepartmentsByDoctorId(Doctor doctor) {
        List<Department> departments=departmentRepository.getAllDepartments(doctor.getHospital().getId());
        for (Department department: doctor.getDepartments()){
            departments.remove(department);
        }
        return departments;

    }


}
