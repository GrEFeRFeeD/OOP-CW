package oop.CourseWork.model.receiving_product;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class ReceivingProductKey implements Serializable {

    @Column(name = "receiving_id")
    private Long receivingId;

    @Column(name = "product_id")
    private Long productId;
}
