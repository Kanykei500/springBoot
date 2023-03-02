package peaksoft.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.exceptions.NotFoundException;
import peaksoft.model.Hospital;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.HospitalService;

import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository hospitalRepository;
    @Override
    public void save(Hospital newHospital) {
        hospitalRepository.save(newHospital);

    }



    @Override
    public List<Hospital> getAllHospitals(String word) {
        if (word != null && !word.trim().isEmpty()) {
            return hospitalRepository.searchByName(word);
        } else {
            return hospitalRepository.findAll();
        }
    }

    @Override
    public Hospital getHospitalById(Long id) {
        return hospitalRepository.findById(id).orElseThrow(()->new NotFoundException());
    }

    @Override
    public void deleteHospital(Long id) {
        hospitalRepository.deleteById(id);

    }

    @Override
    public void update(Long id, Hospital newHospital) {
        Hospital hospital = hospitalRepository.findById(id).get();
        hospital.setName(newHospital.getName());
        hospital.setAddress(newHospital.getAddress());
        hospital.setImage(newHospital.getImage());
        hospitalRepository.save(hospital);


    }

    @Override
    public List<Hospital> search(String word) {
        return hospitalRepository.searchByName(word);
    }
}
