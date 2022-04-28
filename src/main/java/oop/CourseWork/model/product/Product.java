package oop.CourseWork.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.CourseWork.model.order.Order;
import oop.CourseWork.model.order_product.OrderProduct;
import oop.CourseWork.model.productBase.ProductBase;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_id")
    private int id;

    private String name;
    private double price;

    // 1:1 with product_base
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_base_id_fk", referencedColumnName = "product_base_id")
    @JsonIgnore
    private ProductBase productBase;

    // M:N with orders
    @OneToMany(mappedBy = "product")
    private Set<OrderProduct> productBody;

    public void addProductBody(OrderProduct orderProduct) {
        productBody.add(orderProduct);
    }
}
