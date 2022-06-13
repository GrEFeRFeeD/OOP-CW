package oop.CourseWork.controllers;

import oop.CourseWork.model.employee.Employee;
import oop.CourseWork.model.employee.EmployeeService;
import oop.CourseWork.model.role.Role;
import oop.CourseWork.model.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class PostLoginRedirectController {

    private EmployeeService employeeService;
    private RoleService roleService;

    @Autowired
    public PostLoginRedirectController(EmployeeService employeeService, RoleService roleService) {
        this.employeeService = employeeService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String redirectToHomePage() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeService.findEmployeeByUsername(authentication.getName());
        if (employee == null) {
            return "index";
        }
        Set<Role> roles = employee.getRoles();
        if (roles.contains(roleService.getRoleByName("ROLE_ADMIN"))) {
            return "adminpage";
        }
        if (roles.contains(roleService.getRoleByName("ROLE_MANAGER"))) {
            return "managerpage";
        }
        if (roles.contains(roleService.getRoleByName("ROLE_RECEIVER"))) {
            return "receiving";
        }
        if (roles.contains(roleService.getRoleByName("ROLE_CASHIER"))) {
            return "check";
        }
        return "index";
    }
}
