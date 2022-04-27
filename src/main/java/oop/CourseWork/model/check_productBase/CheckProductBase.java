package oop.CourseWork.model.check_productBase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import oop.CourseWork.model.check.Check;
import oop.CourseWork.model.productBase.ProductBase;

import javax.persistence.*;

@Entity
@Table(name = "check_product_base")
@Data
public class CheckProductBase {

    @EmbeddedId
    private CheckProductBaseKey id;

    @ManyToOne
    @MapsId("checkId")
    @JoinColumn(name = "check_id")
    @JsonIgnore
    private Check check;

    @ManyToOne
    @MapsId("productBaseId")
    @JoinColumn(name = "product_base_id")
    @JsonIgnore
    private ProductBase productBase;

    private int count;
    private double price;

}
