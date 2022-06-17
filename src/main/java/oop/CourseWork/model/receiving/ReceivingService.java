package oop.CourseWork.model.receiving;

import oop.CourseWork.model.employee.Employee;
import oop.CourseWork.model.order.OrderService;
import oop.CourseWork.model.order.OrderStatus;
import oop.CourseWork.model.productLog.ProductLogService;
import oop.CourseWork.model.receiving_product.ReceivingProduct;
import oop.CourseWork.model.receiving_product.ReceivingProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReceivingService {

    private final ReceivingRepository receivingRepository;
    private final ProductLogService productLogService;
    private final OrderService orderService;
    private final ReceivingProductService receivingProductService;

    @Autowired
    public ReceivingService(ReceivingRepository receivingRepository, ProductLogService productLogService, OrderService orderService, ReceivingProductService receivingProductService) {
        this.receivingRepository = receivingRepository;
        this.productLogService = productLogService;
        this.orderService = orderService;
        this.receivingProductService = receivingProductService;
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

    public void nullifyEmployee(Employee employee) {
        List<Receiving> receivings = receivingRepository.findAll();
        for (Receiving r : receivings) {
            if (r.getEmployee() != null && r.getEmployee().getId().equals(employee.getId())) {
                r.setEmployee(null);
                receivingRepository.save(r);
            }
        }
    }

    public Map<Long, Double> getAllReceivingSums() {
        List<Receiving> receivings = receivingRepository.findAll();
        Map<Long, Double> receivingSums = new HashMap<>();
        for (Receiving r : receivings) {
            List<ReceivingProduct> receivingProducts = receivingProductService.getReceivingProductsByReceiving(r);
            receivingSums.put(r.getId(), receivingProducts.stream().mapToDouble(value -> value.getCount() * value.getPrice()).sum());
        }

        return receivingSums;
    }
}
