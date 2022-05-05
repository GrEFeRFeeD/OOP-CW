package oop.CourseWork.model.receiving_product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import oop.CourseWork.model.product.Product;
import oop.CourseWork.model.receiving.Receiving;

import javax.persistence.*;

@Entity
@Table(name = "receivings_products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceivingProduct {

    @EmbeddedId
    private ReceivingProductKey id;

    @ManyToOne
    @MapsId("receivingId")
    @JoinColumn(name = "receiving_id")
    private Receiving receiving;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    private int count;
    private double price;
}
