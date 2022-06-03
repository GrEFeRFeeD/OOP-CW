package oop.CourseWork.model.employee;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.CourseWork.model.check.Check;
import oop.CourseWork.model.order.Order;
import oop.CourseWork.model.productLog.ProductLog;
import oop.CourseWork.model.receiving.Receiving;
import oop.CourseWork.model.role.Role;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "employees")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    private String patronymic;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String position;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Transient
    private String confirmedPassword;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private Set<Check> checks;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private Set<Order> orders;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private Set<Receiving> receivings;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private Set<ProductLog> productLogs;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employees_roles",
            joinColumns = @JoinColumn(name = "employeeId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles;

    public void addCheck(Check check) {
        checks.add(check);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void addReceiving(Receiving receiving) { receivings.add(receiving); }

    public void addProductLog(ProductLog productLog) { productLogs.add(productLog); }

    public void addRole(Role role) { roles.add(role); }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, lastName, firstName, patronymic, phoneNumber, position, username, password);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", position='" + position + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", confirmedPassword='" + confirmedPassword + '\'' +
                '}';
    }
}
