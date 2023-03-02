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
import peaksoft.service.DoctorService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;
    private final AppointmentRepository appointmentRepository;
    @Override
    public Doctor save(Long hospitalId, Doctor newDoctor) {
        Hospital hospital = hospitalRepository.findById(hospitalId).orElseThrow(()->new NotFoundException());
        newDoctor.setHospital(hospital);
        return doctorRepository.save(newDoctor);




    }

    @Override
    public List<Doctor> getAllDoctors(Long id) {
        return doctorRepository.getAllDoctors(id);
    }

    @Override
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() -> new NotFoundException());
    }


    @Override
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(()->new NotFoundException());
        List<Appointment> appointments = doctor.getAppointments();
        List<Appointment> appointmentList = appointments.stream().filter(s -> s.getDoctor().getId().equals(id)).toList();
        appointmentList.forEach(s -> appointmentRepository.deleteById(s.getId()));
        List<Doctor> doctors = doctor.getHospital().getDoctors();
        doctors.removeIf(d->d.getId().equals(id));
        doctorRepository.deleteById(id);


    }

    @Override
    public void update(Long id, Doctor newDoctor) {
        Doctor doctor = doctorRepository.findById(id).get();
        doctor.setEmail(newDoctor.getEmail());
        doctor.setFirstName(newDoctor.getFirstName());
        doctor.setLastName(newDoctor.getLastName());
        doctor.setPosition(newDoctor.getPosition());

    }

    @Override
    public void assignDepartmentsToDoctor(List<Long> departmentIds, Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new IllegalArgumentException
                                ("Invalid department id" + doctorId));
        List<Department> departments = departmentRepository.findAllById(departmentIds);
        for (Department department : departments) {
            doctor.addDepartment(department);
            department.addDoctor(doctor);
        }
        doctorRepository.save(doctor);
    }


}
