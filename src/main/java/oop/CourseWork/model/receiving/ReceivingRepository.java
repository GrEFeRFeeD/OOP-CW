package oop.CourseWork.model.receiving;

import oop.CourseWork.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceivingRepository extends JpaRepository<Receiving, Long> {
    List<Receiving> findByOrder(Order order);
}
