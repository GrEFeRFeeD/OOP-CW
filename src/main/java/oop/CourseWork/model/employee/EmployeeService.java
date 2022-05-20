package oop.CourseWork.model.employee;

import oop.CourseWork.model.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
    }
	
	public void addEmployee(Employee employee) {
		//TODO: add employee
	}

    public Employee findEmployeeByUsername(String username) {
        return employeeRepository.findEmployeeByUsername(username);
    }
}
