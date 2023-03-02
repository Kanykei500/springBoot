package peaksoft.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
public class Department {
    @Id
    @SequenceGenerator(name = "department_id_gen",sequenceName = "department_id_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "department_id_gen")

    private Long id;
    @NotEmpty(message = "Name should not be null")
    @Column(name = "name",unique = true)
    private String name;
    @ManyToMany(mappedBy = "departments",cascade = {REFRESH,PERSIST,DETACH,MERGE})
    private List<Doctor> doctors;
    public void addDoctor(Doctor doctor){
        if (doctors==null){
            doctors=new ArrayList<>();
        }
        doctors.add(doctor);
    }
    @ManyToOne(cascade ={REFRESH,PERSIST,DETACH,MERGE} )
    private Hospital hospital;
    @Transient
    private Long hospitalId;

}
