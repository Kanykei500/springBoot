package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.model.Appointment;
import peaksoft.model.Hospital;
import peaksoft.repository.AppointmentRepository;
import peaksoft.service.AppointmentService;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    @Override
    public Appointment save(Long hospitalId, Appointment appointment) {
        return null;
    }

    @Override

    public List<Appointment> getAllAppointments(Long id) {
        return appointmentRepository.getAllAppointments(id);
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return null;
    }

    @Override
    public void deleteAppointment(Long hospitalId, Long id) {
        appointmentRepository.deleteById(id);

    }

    @Override
    public void update(Long id, Appointment newAppointment) {

    }
}
