package oop.CourseWork.model.check;

import oop.CourseWork.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckRepository extends JpaRepository<Check, Long> {
    List<Check> findByEmployeeAndStatus(Employee employee, CheckStatus checkStatus);
}
