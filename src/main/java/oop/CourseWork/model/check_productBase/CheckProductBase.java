package oop.CourseWork.model.check_productBase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.CourseWork.model.check.Check;
import oop.CourseWork.model.productBase.ProductBase;

import javax.persistence.*;

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

}
