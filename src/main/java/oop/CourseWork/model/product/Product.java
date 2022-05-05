package oop.CourseWork.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.CourseWork.model.order.Order;
import oop.CourseWork.model.order_product.OrderProduct;
import oop.CourseWork.model.productBase.ProductBase;
import oop.CourseWork.model.productLog.ProductLog;
import oop.CourseWork.model.receiving_product.ReceivingProduct;

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
    private Long id;

    private String name;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private ProductBase productBase;

    @OneToMany(mappedBy = "productObj")
    @JsonIgnore
    private Set<OrderProduct> productBody;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private Set<ProductLog> productLogs;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private Set<ReceivingProduct> receivingProducts;

    public void addProductBody(OrderProduct orderProduct) {
        productBody.add(orderProduct);
    }
    public void addProductLog(ProductLog productLog) { productLogs.add(productLog); }
    public void addReceivingProduct(ReceivingProduct receivingProduct) { receivingProducts.add(receivingProduct); }
}
