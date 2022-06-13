package oop.CourseWork.model.role;

import oop.CourseWork.model.employee.Employee;
import oop.CourseWork.model.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.util.List;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public void nullifyEmployee(Employee employee) {
        List<Role> roles = roleRepository.findAll();
        for (Role r : roles) {
            r.getEmployees().remove(employee);
            roleRepository.save(r);
        }

    }

    public Role getRoleByName(String name) { return roleRepository.findRoleByName(name); }
}
