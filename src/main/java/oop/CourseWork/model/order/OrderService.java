package oop.CourseWork.model.order;

import oop.CourseWork.model.employee.EmployeeRepository;
import oop.CourseWork.model.order_product.OrderProduct;
import oop.CourseWork.model.order_product.OrderProductKey;
import oop.CourseWork.model.order_product.OrderProductRepository;
import oop.CourseWork.model.product.Product;
import oop.CourseWork.model.product.ProductRepository;
import oop.CourseWork.model.provider.Provider;
import oop.CourseWork.model.provider.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private ProviderRepository providerRepository;
    private EmployeeRepository employeeRepository;
    private ProductRepository productRepository;
    private OrderProductRepository orderProductRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProviderRepository providerRepository, EmployeeRepository employeeRepository, ProductRepository productRepository, OrderProductRepository orderProductRepository) {
        this.orderRepository = orderRepository;
        this.providerRepository = providerRepository;
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
    }

    private Order addToRepository(Order order, Long providerId, Long employeeId) {
        order.setDate(new Date(System.currentTimeMillis()));
        order.setProvider(providerRepository.getById(providerId));
        //TODO: employee_id connection order.setEmployee(employeeRepository.getById(employeeId));
        return orderRepository.save(order);
    }

    public Order addOrder(Order order, Long providerId, Long employeeId) {
        Order savedOrder = addToRepository(order, providerId, employeeId);

        for(Product p : productRepository.findAll()) {
            OrderProduct orderProduct = new OrderProduct(new OrderProductKey(), savedOrder, p, 0, 0, 0);
            orderProductRepository.save(orderProduct);
        }

        return savedOrder;
    }

    public Map<Long, Map<String, Double>> getAllOrderSums() {
        List<Order> orders = orderRepository.findAll();
        Map<Long, Map<String, Double>> allOrderSums = new HashMap<>();
        for (Order o : orders) {
            List<OrderProduct> orderProducts = orderProductRepository.findByOrderObj(o);
            Map<String, Double> currentOrderSums = new HashMap<>();
            currentOrderSums.put("products", orderProducts.stream().mapToDouble(value -> value.getOrder() * value.getProductObj().getPrice()).sum());
            currentOrderSums.put("returns", orderProducts.stream().mapToDouble(value -> value.getRetrn() * value.getProductObj().getPrice()).sum());
            allOrderSums.put(o.getId(), currentOrderSums);
        }
        return allOrderSums;
    }

    public Order getOrderById(Long id) {
        return orderRepository.getById(id);
    }

    public List<Order> getOrdersByProvider(Long providerId) {
        Provider provider = providerRepository.getById(providerId);
        return orderRepository.findByProvider(provider);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
