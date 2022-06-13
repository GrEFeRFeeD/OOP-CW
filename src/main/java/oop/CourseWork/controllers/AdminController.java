package oop.CourseWork.controllers;

import oop.CourseWork.model.employee.Employee;
import oop.CourseWork.model.employee.EmployeeService;
import oop.CourseWork.model.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    private EmployeeService employeeService;
    private RoleService roleService;

    @Autowired
    public AdminController(EmployeeService employeeService, RoleService roleService) {
        this.employeeService = employeeService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getAdminPage() {
        return "adminpage";
    }

    @GetMapping("/manage_page")
    public String redirectToManagePage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeService.findEmployeeByUsername(authentication.getName());
        if (employee.getRoles().contains(roleService.getRoleByName("ROLE_ADMIN"))) {
            return "redirect:/admin";
        }

        return "redirect:/manager";
    }
}
