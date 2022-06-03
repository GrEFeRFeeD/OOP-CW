package oop.CourseWork.model.check;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.CourseWork.model.check_productBase.CheckProductBase;
import oop.CourseWork.model.employee.Employee;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
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

    private CheckStatus status;

    @ManyToOne
    @JoinColumn(name = "cashier")
    private Employee employee;

    @OneToMany(mappedBy = "check")
    @JsonIgnore
    private Set<CheckProductBase> checkBody;

    public void addCheckBody(CheckProductBase checkProductBase) {
        checkBody.add(checkProductBase);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Check check = (Check) o;
        return Objects.equals(id, check.id) && Objects.equals(date, check.date) && status == check.status && Objects.equals(employee, check.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, status, employee);
    }

    @Override
    public String toString() {
        return "Check{" +
                "id=" + id +
                ", date=" + date +
                ", status=" + status +
                ", employee=" + employee +
                '}';
    }
}
