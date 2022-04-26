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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckProductBaseKey that = (CheckProductBaseKey) o;
        return checkId == that.checkId && productBaseId == that.productBaseId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkId, productBaseId);
    }
}
