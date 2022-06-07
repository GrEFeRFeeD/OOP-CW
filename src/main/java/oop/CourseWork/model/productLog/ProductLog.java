package oop.CourseWork.model.productLog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.CourseWork.model.employee.Employee;
import oop.CourseWork.model.product.Product;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "product_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "log_id")
    private Long id;

    private ProductLogType type;
    private int count;
    private double price;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
