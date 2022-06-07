package oop.CourseWork.model.productBase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.CourseWork.model.check_productBase.CheckProductBase;
import oop.CourseWork.model.product.Product;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product_base")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_id")
    private Long id;

    private int count;

    @Column(name = "purchase_price")
    private double purchasePrice;

    @Column(name = "selling_price")
    private double sellingPrice;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductBase that = (ProductBase) o;
        return count == that.count && Double.compare(that.purchasePrice, purchasePrice) == 0 && Double.compare(that.sellingPrice, sellingPrice) == 0 && id.equals(that.id) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, count, purchasePrice, sellingPrice, product);
    }

    @Override
    public String toString() {
        return "ProductBase{" +
                "id=" + id +
                ", count=" + count +
                ", purchasePrice=" + purchasePrice +
                ", sellingPrice=" + sellingPrice +
                ", product=" + product.getId() +
                '}';
    }
}
