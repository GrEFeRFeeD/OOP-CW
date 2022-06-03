package oop.CourseWork.model.check_productBase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.CourseWork.model.check.Check;
import oop.CourseWork.model.productBase.ProductBase;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "checks_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckProductBase {

    @EmbeddedId
    private CheckProductBaseKey id;

    @ManyToOne
    @MapsId("checkId")
    @JoinColumn(name = "check_id")
    private Check check;

    @ManyToOne
    @MapsId("productBaseId")
    @JoinColumn(name = "product_id")
    private ProductBase productBase;

    private int count;
    private double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckProductBase that = (CheckProductBase) o;
        return count == that.count && Double.compare(that.price, price) == 0 && id.equals(that.id) && Objects.equals(check, that.check) && Objects.equals(productBase, that.productBase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, check, productBase, count, price);
    }

    @Override
    public String toString() {
        return "CheckProductBase{" +
                "id=" + id +
                ", check=" + check.getId() +
                ", productBase=" + productBase.getId() +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
