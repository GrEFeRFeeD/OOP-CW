package oop.CourseWork.model.check_productBase;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class CheckProductBaseKey implements Serializable {

    @Column(name = "check_id")
    private int checkId;

    @Column(name = "product_base_id")
    private int productBaseId;

}
