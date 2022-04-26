package oop.CourseWork.model.check_productBase;

import lombok.Data;
import oop.CourseWork.model.check.Check;
import oop.CourseWork.model.productBase.ProductBase;

import javax.persistence.*;

@Entity
@Data
public class CheckProductBase {

    @EmbeddedId
    private CheckProductBaseKey id;

    @ManyToOne
    @MapsId("checkId")
    @JoinColumn(name = "check_id")
    private Check check;

    @ManyToOne
    @MapsId("productBaseId")
    @JoinColumn(name = "product_base_id")
    private ProductBase productBase;

    private int count;
    private double price;

}
