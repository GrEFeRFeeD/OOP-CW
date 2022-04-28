package oop.CourseWork.model.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.CourseWork.model.employee.Employee;
import oop.CourseWork.model.order_product.OrderProduct;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_id")
    private int id;

    private Date date;
    private String provider;

    // N:M with products
    @OneToMany(mappedBy = "order")
    private Set<OrderProduct> orderBody;

    // 1:N with employee
    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Employee employee;

    public void addOrderBody(OrderProduct orderProduct) {
        orderBody.add(orderProduct);
    }
}
