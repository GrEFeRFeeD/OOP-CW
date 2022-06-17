package oop.CourseWork.model.order_product;

import oop.CourseWork.model.order.Order;
import oop.CourseWork.model.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderProductService {

    private final OrderProductRepository orderProductRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderProductService(OrderProductRepository orderProductRepository, OrderRepository orderRepository) {
        this.orderProductRepository = orderProductRepository;
        this.orderRepository = orderRepository;
    }

    public OrderProduct getOrderProductById(Long orderId, Long productId) {
        return orderProductRepository.getById(new OrderProductKey(orderId, productId));
    }

    public List<OrderProduct> getOrderProductsByOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(orderProductRepository::findByOrderObj).orElse(null);
    }
    public List<OrderProduct> getAllOrderProducts() {
        return orderProductRepository.findAll();
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProductRepository.save(orderProduct);
    }

}
