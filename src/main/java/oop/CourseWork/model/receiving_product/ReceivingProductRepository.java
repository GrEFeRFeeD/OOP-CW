package oop.CourseWork.model.receiving_product;

import oop.CourseWork.model.receiving.Receiving;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceivingProductRepository extends JpaRepository<ReceivingProduct, ReceivingProductKey> {
    List<ReceivingProduct> getReceivingProductsByReceiving(Receiving receiving);
}
