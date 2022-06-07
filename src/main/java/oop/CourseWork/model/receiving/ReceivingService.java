package oop.CourseWork.model.receiving;

import oop.CourseWork.model.order.OrderRepository;
import oop.CourseWork.model.order.OrderService;
import oop.CourseWork.model.order.OrderStatus;
import oop.CourseWork.model.order_product.OrderProductRepository;
import oop.CourseWork.model.productBase.ProductBaseRepository;
import oop.CourseWork.model.productLog.ProductLogRepository;
import oop.CourseWork.model.productLog.ProductLogService;
import oop.CourseWork.model.receiving_product.ReceivingProductRepository;
import oop.CourseWork.model.receiving_product.ReceivingProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceivingService {

    private ReceivingRepository receivingRepository;
    private ProductLogService productLogService;
    private OrderService orderService;

    @Autowired
    public ReceivingService(ReceivingRepository receivingRepository, ProductLogService productLogService, OrderService orderService) {
        this.receivingRepository = receivingRepository;
        this.productLogService = productLogService;
        this.orderService = orderService;
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

    public void closeReceiving(Long receivingId) {
        Receiving receiving = receivingRepository.getById(receivingId);
        productLogService.logReceiving(receiving);
        receiving.setStatus(ReceivingStatus.CLOSED);
        addReceiving(receiving);

        if (receiving.getOrder().getStatus() != OrderStatus.CLOSED) {
            orderService.closeOrder(receiving.getOrder().getId());
        }
    }
}
