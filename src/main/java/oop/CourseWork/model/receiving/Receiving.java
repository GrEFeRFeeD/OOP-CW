package oop.CourseWork.model.receiving;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.CourseWork.model.employee.Employee;
import oop.CourseWork.model.order.Order;
import oop.CourseWork.model.receiving_product.ReceivingProduct;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "receivings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Receiving {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "receiving_id")
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private ReceivingStatus status;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToMany(mappedBy = "receiving")
    @JsonIgnore
    private Set<ReceivingProduct> receivingProducts;

    public void addReceivingProduct(ReceivingProduct receivingProduct) { receivingProducts.add(receivingProduct); }
}
