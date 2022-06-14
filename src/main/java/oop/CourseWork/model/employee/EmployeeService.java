package oop.CourseWork.model.employee;

import oop.CourseWork.model.check.CheckService;
import oop.CourseWork.model.order.OrderService;
import oop.CourseWork.model.productLog.ProductLogService;
import oop.CourseWork.model.receiving.ReceivingService;
import oop.CourseWork.model.role.RoleRepository;
import oop.CourseWork.model.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService{

    private EmployeeRepository employeeRepository;
	private PasswordEncoder passwordEncoder;
    private OrderService orderService;
    private ReceivingService receivingService;
    private RoleService roleService;
    private ProductLogService productLogService;
    private CheckService checkService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, OrderService orderService, ReceivingService receivingService, RoleService roleService, ProductLogService productLogService, CheckService checkService) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.orderService = orderService;
        this.receivingService = receivingService;
        this.roleService = roleService;
        this.productLogService = productLogService;
        this.checkService = checkService;
    }

    public Employee addEmployee(Employee employee) {
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		return employeeRepository.save(employee);
	}

    public Employee addEmployeeWithoutHash(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findEmployeeByUsername(String username) {
        return employeeRepository.findEmployeeByUsername(username);
    }

    public Employee findEmployeeByPhoneNumber(String phoneNumber) {
        return employeeRepository.findEmployeeByPhoneNumber(phoneNumber);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long employeeId) { return employeeRepository.getById(employeeId); }

    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.getById(employeeId);
        orderService.nullifyEmployee(employee);
        receivingService.nullifyEmployee(employee);
        roleService.nullifyEmployee(employee);
        productLogService.nullifyEmployee(employee);
        checkService.nullifyEmployee(employee);
        employeeRepository.delete(employee);
    }

    public boolean canCurrentEmployeeDoManage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = findEmployeeByUsername(authentication.getName());
        return employee.getRoles().contains(roleService.getRoleByName("ROLE_MANAGER")) ||
                employee.getRoles().contains(roleService.getRoleByName("ROLE_ADMIN"));
    }

    public boolean isCurrentEmployeeAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = findEmployeeByUsername(authentication.getName());
        return employee.getRoles().contains(roleService.getRoleByName("ROLE_ADMIN"));
    }
}
