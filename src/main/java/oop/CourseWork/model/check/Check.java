package oop.CourseWork.model.check;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.CourseWork.model.check_productBase.CheckProductBase;
import oop.CourseWork.model.employee.Employee;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "checks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Check {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "check_id")
    private int id;

    private Date date;

    // N:M with productBase
    @OneToMany(mappedBy = "check")
    private Set<CheckProductBase> checkBody;

    // 1:N with employee
    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Employee employee;

    public void addCheckBody(CheckProductBase checkProductBase) {
        checkBody.add(checkProductBase);
    }
}
