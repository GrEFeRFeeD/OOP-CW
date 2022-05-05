package oop.CourseWork.model.check;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.CourseWork.model.check_productBase.CheckProductBase;
import oop.CourseWork.model.employee.Employee;

import javax.persistence.*;
import java.util.Date;
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
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String status;

    @OneToMany(mappedBy = "check")
    @JsonIgnore
    private Set<CheckProductBase> checkBody;

    @ManyToOne
    @JoinColumn(name = "cashier")
    private Employee employee;

    public void addCheckBody(CheckProductBase checkProductBase) {
        checkBody.add(checkProductBase);
    }
}
