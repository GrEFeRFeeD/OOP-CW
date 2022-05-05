package oop.CourseWork.model.provider;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.CourseWork.model.order.Order;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "providers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "provider_id")
    private Long id;

    private String name;
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    // 1:N with orders
    @OneToMany(mappedBy = "provider")
    @JsonIgnore
    private Set<Order> orders;

    public void addOrder(Order order) {
        orders.add(order);
    }
}
