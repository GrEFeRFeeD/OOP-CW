package oop.CourseWork.model.check;

import lombok.Data;
import oop.CourseWork.model.check_productBase.CheckProductBase;
import oop.CourseWork.model.employee.Employee;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "checks")
@Data
public class Check {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "check_id")

    private int id;

    private Date date;

    // N:M with productBase
    @OneToMany(mappedBy = "check")
    private Set<CheckProductBase> checkBody;
}
