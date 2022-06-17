package oop.CourseWork.model.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class EmployeeValidator implements Validator {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeValidator(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Employee.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Employee employee = (Employee) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "", "This field is required.");

        if (employeeService.findEmployeeByPhoneNumber(employee.getPhoneNumber()) != null &&
                !employeeService.findEmployeeByPhoneNumber(employee.getPhoneNumber()).getId().equals(employee.getId())) {
            errors.rejectValue("phoneNumber", "", "Phone number is already exists.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "", "This field is required.");

        if (employee.getUsername().length() < 5 || employee.getUsername().length() > 32) {
            errors.rejectValue("username", "", "Username must be between 5 and 32 characters.");
        }

        if (employeeService.findEmployeeByUsername(employee.getUsername()) != null &&
                !employeeService.findEmployeeByUsername(employee.getUsername()).getId().equals(employee.getId())) {

            errors.rejectValue("username", "", "Username is already exists.");
        }

        if (employee.getPassword().length() > 0 ||
                (employeeService.findEmployeeByUsername(employee.getUsername()) != null &&
                        employeeService.findEmployeeByUsername(employee.getUsername()).getPassword().length() == 0)) {

            if (employee.getPassword().length() < 5 || employee.getPassword().length() > 32) {
                errors.rejectValue("password", "", "Password must be between 5 and 32 characters.");
            }

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmedPassword", "This field is required.");
            if (!employee.getPassword().equals(employee.getConfirmedPassword())) {
                errors.rejectValue("confirmedPassword", "", "Passwords doesn't match.");
            }
        }
    }
}
