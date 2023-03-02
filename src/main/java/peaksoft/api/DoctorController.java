package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Department;
import peaksoft.model.Doctor;
import peaksoft.service.DepartmentService;
import peaksoft.service.DoctorService;
import peaksoft.service.HospitalService;

import java.util.List;

@Controller
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;
    private final HospitalService hospitalService;
    private final DepartmentService departmentService;
    @Autowired
    public DoctorController(DoctorService doctorService, HospitalService hospitalService, DepartmentService departmentService) {
        this.doctorService = doctorService;
        this.hospitalService = hospitalService;
        this.departmentService = departmentService;
    }
    @GetMapping("/{hospitalId}")
    public String getAllDoctors(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors(hospitalId));
        model.addAttribute(hospitalId);
        return "doctors/doctors";
    }
    @GetMapping("/{hospitalId}/new")
    public String create(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("newDoctors", new Doctor());
      // model.addAttribute("departments",departmentService.getAllDepartments(hospitalId));
       model.addAttribute(hospitalId);
        return "doctors/doctorSavePage";
    }


    @PostMapping("/{hospitalId}/doctorSavePage")
    public String saveDoctor(@PathVariable Long hospitalId,@ModelAttribute("newDoctor")@Valid Doctor doctor) {
        doctorService.save(hospitalId,doctor);
        return "redirect:/doctors/"+ hospitalId;

    }
    @GetMapping("/{hospitalId}/{doctorId}/delete")
    public String deleteDoctor(@PathVariable("hospitalId")Long hospitalId,@PathVariable ("doctorId")Long doctorId){
        doctorService.deleteDoctor(doctorId);
        return "redirect:/doctors/"+hospitalId;
    }
    @GetMapping("/{hospitalId}/{doctorId}/edit")
    public String editDoctor(Model model,@PathVariable("hospitalId")Long hospitalId,@PathVariable ("doctorId")Long doctorId){
        model.addAttribute("doctor",doctorService.getDoctorById(doctorId));
        model.addAttribute("hospitalId",hospitalId);
        return "doctors/updateDoctor";

    }
    @PostMapping("/{hospitalId}/{doctorId}/update")
    public String updateDoctor(@PathVariable("hospitalId")Long hospitalId,@PathVariable("doctorId")Long doctorId, @ModelAttribute("doctor")@Valid Doctor doctor){
        doctorService.update(doctorId, doctor);
        return "redirect:/doctors/"+hospitalId;
    }
    @GetMapping("/{doctorId}/assign")
    public String assign(Model model,@PathVariable ("doctorId")Long doctorId){
        Doctor doctor = doctorService.getDoctorById(doctorId);
        List<Department>departments=departmentService.getAllDepartmentsByDoctorId(doctor);
        model.addAttribute("doctor",doctor);
        model.addAttribute("departments",departments);
        return "doctors/assign";
    }
    @PostMapping("/{doctorId}/save")
    public String assignDepartmentToDoctor(@PathVariable ("doctorId")Long doctorId,
                                           @RequestParam ("departmentIds")List<Long>departmentIds){
        doctorService.assignDepartmentsToDoctor(departmentIds, doctorId);
        return "redirect:/doctors/"+doctorService.getDoctorById(doctorId).getHospital().getId();
    }

}

