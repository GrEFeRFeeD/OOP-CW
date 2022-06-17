package oop.CourseWork.security;

import oop.CourseWork.model.employee.Employee;
import oop.CourseWork.model.employee.EmployeeRepository;
import oop.CourseWork.model.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public UserDetailsServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee = employeeRepository.findEmployeeByUsername(username);

        if (employee == null) {
            throw new UsernameNotFoundException("User " + username + " not exists.");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();

        for (Role role : employee.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new User(username, employee.getPassword(), authorities);
    }
}
