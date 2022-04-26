package oop.CourseWork.model.employee;

import lombok.Data;
import oop.CourseWork.model.check.Check;
import oop.CourseWork.model.order.Order;

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

    @Column(name = "login")
    private String systemLogin;

    @Column(name = "password")
    private String systemPasswordHash;

    // 1:N (recursive) with employee
    @ManyToOne
    @JoinColumn
    private Employee supervisor;

    @OneToMany(mappedBy = "supervisor")
    private Set<Employee> subordinates;

    // 1:N with checks
    @OneToMany(mappedBy = "employee")
    private Set<Check> checks;

    // Todo: 1:N with orders
    @OneToMany(mappedBy = "employee")
    private Set<Order> orders;

}
