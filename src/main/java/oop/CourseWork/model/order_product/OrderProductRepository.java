package oop.CourseWork.model.order_product;

import oop.CourseWork.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductKey> {
    List<OrderProduct> findByOrderObj(Order order);
}
