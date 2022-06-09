package oop.CourseWork.model.productLog;

import oop.CourseWork.model.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductLogRepository extends JpaRepository<ProductLog, Long> {
    List<ProductLog> getProductLogsByEmployee(Employee employee);
}
