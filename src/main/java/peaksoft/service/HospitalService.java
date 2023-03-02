package peaksoft.service;

import org.springframework.stereotype.Service;
import peaksoft.model.Hospital;

import java.util.List;
@Service
public interface HospitalService {
    void save(Hospital newHospital);
    List<Hospital> getAllHospitals(String word);
    Hospital getHospitalById(Long id);
    void deleteHospital(Long id);
    void update( Long id,Hospital newHospital);
    List<Hospital> search(String word);

}
