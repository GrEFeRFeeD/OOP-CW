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
import java.util.*;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_id")
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<ProductGroup> groups;

    private String name;
    private double price;


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

    public Product() {
        this.productBody = new HashSet<>();
        this.productLogs = new HashSet<>();
        this.receivingProducts = new HashSet<>();
    }

    public void addProductBody(OrderProduct orderProduct) {
        productBody.add(orderProduct);
    }
    public void addProductLog(ProductLog productLog) { productLogs.add(productLog); }
    public void addReceivingProduct(ReceivingProduct receivingProduct) { receivingProducts.add(receivingProduct); }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", groups=" + groups +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", productBase=" + productBase.getId()+
                '}';
    }
}
