package oop.CourseWork.model.employee;

import oop.CourseWork.model.productLog.ProductLog;
import oop.CourseWork.model.productLog.ProductLogService;
import oop.CourseWork.model.role.Role;
import oop.CourseWork.model.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.List;

@Controller
public class EmployeeController {

    private EmployeeService employeeService;
    private EmployeeValidator employeeValidator;
    private RoleService roleService;
    private ProductLogService productLogService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeValidator employeeValidator, RoleService roleService, ProductLogService productLogService) {
        this.employeeService = employeeService;
        this.employeeValidator = employeeValidator;
        this.roleService = roleService;
        this.productLogService = productLogService;
    }

    @GetMapping("/employees")
    public String redirectToEmployeesAll() {
        return "redirect:/employees/all";
    }

    @GetMapping("/employees/{id}")
    public String getEmployee(@PathVariable(name = "id") Long employeeId, Model model) {

        Employee employee = employeeService.getEmployeeById(employeeId);
        model.addAttribute("employee", employee);

        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);

        List<ProductLog> productLogs = productLogService.getProductLogsByEmployee(employee);
        model.addAttribute("productLogs", productLogs);

        return "employee";
    }

    @PostMapping("/employees/{id}")
    public String saveChanges(@PathVariable(name = "id") Long employeeId,
                              @ModelAttribute(name = "employee") Employee employee,
                              BindingResult bindingResult,
                              Model model) {

        employeeValidator.validate(employee, bindingResult);

        if (bindingResult.hasErrors()) {
            return "employee";
        }

        if (employee.getPassword().equals("")) {
            Employee oldEmployee = employeeService.getEmployeeById(employee.getId());
            employee.setPassword(oldEmployee.getPassword());
            employeeService.addEmployeeWithoutHash(employee);
        } else {
            employeeService.addEmployee(employee);
        }

        return "redirect:/employees/" + employeeId;
    }

    @GetMapping("/employees/{id}/delete")
    public String deleteEmployee(@PathVariable(name = "id") Long employeeId, Model model) {

        employeeService.deleteEmployee(employeeId);
        return "redirect:/employees/all";
    }

    @GetMapping("/employees/all")
    public String getAllEmployees(Model model) {

        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);

        return "employeelist";
    }

    @GetMapping("/employees/new_employee")
    public String createEmployee() {

        Employee employee = new Employee(null, " ", " ", " ", "", "", "", "", "", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        employee = employeeService.addEmployee(employee);

        return "redirect:/employees/" + employee.getId();
    }
}
