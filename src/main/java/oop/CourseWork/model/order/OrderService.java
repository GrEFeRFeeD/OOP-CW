package oop.CourseWork.model.order;

import oop.CourseWork.model.employee.Employee;
import oop.CourseWork.model.employee.EmployeeRepository;
import oop.CourseWork.model.order_product.OrderProduct;
import oop.CourseWork.model.order_product.OrderProductKey;
import oop.CourseWork.model.order_product.OrderProductRepository;
import oop.CourseWork.model.product.Product;
import oop.CourseWork.model.product.ProductRepository;
import oop.CourseWork.model.productLog.ProductLogService;
import oop.CourseWork.model.provider.Provider;
import oop.CourseWork.model.provider.ProviderRepository;
import oop.CourseWork.model.receiving.Receiving;
import oop.CourseWork.model.receiving.ReceivingRepository;
import oop.CourseWork.model.receiving_product.ReceivingProduct;
import oop.CourseWork.model.receiving_product.ReceivingProductRepository;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private ProviderRepository providerRepository;
    private EmployeeRepository employeeRepository;
    private ProductRepository productRepository;
    private OrderProductRepository orderProductRepository;
    private ReceivingRepository receivingRepository;
    private ReceivingProductRepository receivingProductRepository;
    private ProductLogService productLogService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProviderRepository providerRepository, EmployeeRepository employeeRepository, ProductRepository productRepository, OrderProductRepository orderProductRepository, ReceivingRepository receivingRepository, ReceivingProductRepository receivingProductRepository, ProductLogService productLogService) {
        this.orderRepository = orderRepository;
        this.providerRepository = providerRepository;
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
        this.orderProductRepository = orderProductRepository;
        this.receivingRepository = receivingRepository;
        this.receivingProductRepository = receivingProductRepository;
        this.productLogService = productLogService;
    }

    private Order addToRepository(Order order, Long providerId) {
        order.setDate(new Date(System.currentTimeMillis()));
        order.setProvider(providerRepository.getById(providerId));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        order.setEmployee(employeeRepository.findEmployeeByUsername(authentication.getName()));
        return orderRepository.save(order);
    }

    public Order addOrder(Order order, Long providerId) {
        Order savedOrder = addToRepository(order, providerId);

        for(Product p : productRepository.findAll()) {
            OrderProduct orderProduct = new OrderProduct(new OrderProductKey(), savedOrder, p, 0, 0, 0);
            orderProductRepository.save(orderProduct);
        }

        return savedOrder;
    }

    public void addOrder(Order order) {
        orderRepository.save(order);
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

    public void deleteOrder(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent()) return;
        Order order = orderOptional.get();
        System.out.println("ORDER_BODY = " + order.getOrderBody());
        List<OrderProduct> orderProducts = orderProductRepository.findByOrderObj(order);
        orderProductRepository.deleteAll(orderProducts);
        List<Receiving> receivings = receivingRepository.findByOrder(order);
        for (Receiving receiving : receivings) {
            List<ReceivingProduct> receivingProducts = receivingProductRepository.getReceivingProductsByReceiving(receiving);
            receivingProductRepository.deleteAll(receivingProducts);
        }
        receivingRepository.deleteAll(receivings);
        orderRepository.delete(order);
    }

    public void closeOrder(Long orderId) {
        Order order = orderRepository.getById(orderId);
        productLogService.logOrder(order);
        order.setStatus(OrderStatus.CLOSED);
        addOrder(order);
    }

    public void nullifyEmployee(Employee employee) {
        List<Order> orders = orderRepository.findAll();
        for (Order o : orders) {
            if (o.getEmployee() != null && o.getEmployee().getId().equals(employee.getId())) {
                o.setEmployee(null);
                orderRepository.save(o);
            }
        }
    }

    public void adjustOrderStatus(Long orderId) {
        Order order = orderRepository.getById(orderId);
        if (order.getStatus() != OrderStatus.OPEN) {
            return;
        }

        long diffInMills = Math.abs(System.currentTimeMillis() - order.getDate().getTime());
        long diff = TimeUnit.DAYS.convert(diffInMills, TimeUnit.MILLISECONDS);
        if (diff > 1) {
            order.setStatus(OrderStatus.PARTLY_EDITABLE);
            orderRepository.save(order);
        }
    }

    public void nullifyProvider(Provider provider) {
        List<Order> orders = orderRepository.findByProvider(provider);
        for (Order o : orders) {
            if (o.getProvider() != null && o.getProvider().getId().equals(provider.getId())) {
                o.setProvider(null);
                orderRepository.save(o);
            }
        }
    }

}
