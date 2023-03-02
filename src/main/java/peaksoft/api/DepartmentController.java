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
@RequestMapping("/departments")

public class DepartmentController {
    private final DepartmentService departmentService;
    private final HospitalService hospitalService;
    private final DoctorService doctorService;

    @Autowired

    public DepartmentController(DepartmentService departmentService, HospitalService hospitalService, DoctorService doctorService) {
        this.departmentService = departmentService;
        this.hospitalService = hospitalService;
        this.doctorService = doctorService;
    }

    @GetMapping("/{hospitalId}")
    public String getAllDepartments(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("departments", departmentService.getAllDepartments(hospitalId));
        model.addAttribute(hospitalId);
        return "departments/departments";
    }

    @GetMapping("/{hospitalId}/new")
    public String create(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("newDepartment", new Department());
        model.addAttribute(hospitalId);
        return "departments/departmentSavePage";
    }


    @PostMapping("/{hospitalId}/departmentSavePage")
    public String saveDepartment(@PathVariable Long hospitalId, @ModelAttribute("newDepartment")@Valid Department department) {
        departmentService.save(hospitalId,department);
        return "redirect:/departments/"+hospitalId;

    }
    @GetMapping("/{hospitalId}/{departmentId}/delete")
    public String deleteDepartment(@PathVariable("hospitalId")Long hospitalId, @PathVariable ("departmentId")Long id){
        departmentService.deleteDepartment(id);
        return "redirect:/departments/"+hospitalId;
    }
    @GetMapping("/{hospitalId}/{departmentId}/edit")
    public String editDepartment(Model model,@PathVariable("departmentId")Long departmentId,@PathVariable ("hospitalId")Long hospitalId){
        model.addAttribute(departmentService.getDepartmentById(departmentId));
        model.addAttribute("hospitalId",hospitalId);
        return "departments/updateDepartment";

    }
    @PostMapping("/{hospitalId}/{departmentId}/update")
    public String updateDepartment(@PathVariable("hospitalId")Long hospitalId,@PathVariable("departmentId")Long id, @ModelAttribute("department")@Valid Department department){
        departmentService.update(id, department);
        return "redirect:/departments/"+hospitalId;
    }




}
