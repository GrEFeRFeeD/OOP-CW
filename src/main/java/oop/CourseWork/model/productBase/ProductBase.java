package oop.CourseWork.model.productBase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import oop.CourseWork.model.check_productBase.CheckProductBase;
import oop.CourseWork.model.product.Product;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product_base")
@Data
public class ProductBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_id")
    private Long id;

    private int count;

    @Column(name = "purchase_price")
    private double purchasePrice;

    @Column(name = "buying_price")
    private double buyingPrice;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "productBase")
    @JsonIgnore
    private Set<CheckProductBase> productBaseBody;

    public void addProductBaseBody(CheckProductBase checkProductBase) {
        productBaseBody.add(checkProductBase);
    }
}
