package oop.CourseWork.model.order_product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductService {

    @Autowired
    OrderProductRepository orderProductRepository;

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProductRepository.save(orderProduct);
    }

    public List<OrderProduct> getAllOrderProducts() {
        return orderProductRepository.findAll();
    }
}
