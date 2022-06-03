package oop.CourseWork.model.check_productBase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckProductBaseKey implements Serializable {

    @Column(name = "check_id")
    private Long checkId;

    @Column(name = "product_base_id")
    private Long productBaseId;

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public void setProductBaseId(Long productBaseId) {
        this.productBaseId = productBaseId;
    }
}
