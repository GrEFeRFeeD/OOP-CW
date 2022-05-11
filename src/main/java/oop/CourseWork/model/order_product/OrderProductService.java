package oop.CourseWork.model.order_product;

import oop.CourseWork.model.order.Order;
import oop.CourseWork.model.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderProductService {

    private OrderProductRepository orderProductRepository;
    private OrderRepository orderRepository;

    @Autowired
    public OrderProductService(OrderProductRepository orderProductRepository, OrderRepository orderRepository) {
        this.orderProductRepository = orderProductRepository;
        this.orderRepository = orderRepository;
    }

    public List<OrderProduct> getOrderProductsByOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.isEmpty()?null:orderProductRepository.findByOrderObj(order.get());
    }
    public List<OrderProduct> getAllOrderProducts() {
        return orderProductRepository.findAll();
    }
}
