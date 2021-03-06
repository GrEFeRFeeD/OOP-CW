package oop.CourseWork.model.employee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeByUsername(String username);
    Employee findEmployeeByPhoneNumber(String phoneNumber);
}
