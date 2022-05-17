package oop.CourseWork.model.receiving;

import oop.CourseWork.model.order.OrderRepository;
import oop.CourseWork.model.order_product.OrderProductRepository;
import oop.CourseWork.model.receiving_product.ReceivingProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceivingService {

    private ReceivingRepository receivingRepository;
    private ReceivingProductRepository receivingProductRepository;
    private OrderRepository orderRepository;
    private OrderProductRepository orderProductRepository;

    @Autowired
    public ReceivingService(ReceivingRepository receivingRepository, ReceivingProductRepository receivingProductRepository, OrderRepository orderRepository, OrderProductRepository orderProductRepository) {
        this.receivingRepository = receivingRepository;
        this.receivingProductRepository = receivingProductRepository;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
    }

    public Receiving addReceiving(Receiving receiving) {
        return receivingRepository.save(receiving);
    }

    public List<Receiving> getAllReceivings() {
        return receivingRepository.findAll();
    }

    public Receiving getReceivingById(Long receivingId) {
        return receivingRepository.getById(receivingId);
    }
}
