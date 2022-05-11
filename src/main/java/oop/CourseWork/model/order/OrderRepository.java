package oop.CourseWork.model.order;

import oop.CourseWork.model.provider.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByProvider(Provider provider);
}
