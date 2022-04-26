package oop.CourseWork.model.productBase;

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
    @Column(name = "product_base_id")
    private int id;

    private int count;

    @Column(name = "purchase_price")
    private double purchasePrice;

    @Column(name = "buying_price")
    private double buyingPrice;

    // 1:1 with products
    @OneToOne(mappedBy = "productBase")
    private Product product;

    // M:N with checks
    @OneToMany(mappedBy = "productBase")
    private Set<CheckProductBase> productBaseBody;
}
