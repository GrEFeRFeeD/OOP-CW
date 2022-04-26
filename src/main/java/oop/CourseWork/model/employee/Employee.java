package oop.CourseWork.model.employee;

import lombok.Data;
import oop.CourseWork.model.check.Check;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "employee_id")
    private int id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    private String patronymic;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String position;
    private String department;

    private String systemLogin;
    private String systemPasswordHash;

    // TODO: 1:N with employee
    // TODO: 1:N with checks
    // Todo: 1:N with orders
}
