package oop.CourseWork.model.productLog;

import oop.CourseWork.model.check.Check;
import oop.CourseWork.model.check_productBase.CheckProductBase;
import oop.CourseWork.model.check_productBase.CheckProductBaseRepository;
import oop.CourseWork.model.employee.Employee;
import oop.CourseWork.model.order.Order;
import oop.CourseWork.model.order_product.OrderProduct;
import oop.CourseWork.model.order_product.OrderProductRepository;
import oop.CourseWork.model.product.Product;
import oop.CourseWork.model.product.ProductRepository;
import oop.CourseWork.model.productBase.ProductBase;
import oop.CourseWork.model.productBase.ProductBaseRepository;
import oop.CourseWork.model.productBase.ProductBaseService;
import oop.CourseWork.model.receiving.Receiving;
import oop.CourseWork.model.receiving.ReceivingRepository;
import oop.CourseWork.model.receiving_product.ReceivingProduct;
import oop.CourseWork.model.receiving_product.ReceivingProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductLogService {

    private ProductLogRepository productLogRepository;
    private ReceivingProductRepository receivingProductRepository;
    private CheckProductBaseRepository checkProductBaseRepository;
    private ProductBaseService productBaseService;
    private OrderProductRepository orderProductRepository;

    @Autowired
    public ProductLogService(ProductLogRepository productLogRepository, ReceivingProductRepository receivingProductRepository, CheckProductBaseRepository checkProductBaseRepository, ProductBaseService productBaseService, OrderProductRepository orderProductRepository) {
        this.productLogRepository = productLogRepository;
        this.receivingProductRepository = receivingProductRepository;
        this.checkProductBaseRepository = checkProductBaseRepository;
        this.productBaseService = productBaseService;
        this.orderProductRepository = orderProductRepository;
    }

    public void addProductLog(ProductLog productLog) { productLogRepository.save(productLog); }

    public List<ProductLog> getAllProductLogs() { return productLogRepository.findAll(); }

    public void logReceiving(Receiving receiving) {
        List<ReceivingProduct> receivingProducts = receivingProductRepository.getReceivingProductsByReceiving(receiving);
        for (ReceivingProduct receivingProduct : receivingProducts) {
            ProductLog productLog = new ProductLog(null, ProductLogType.RECEIVING, receivingProduct.getCount(),
                    receivingProduct.getPrice(), new Date(System.currentTimeMillis()),
                    receivingProduct.getProduct(), receiving.getEmployee());

            addProductLog(productLog);

            productBaseService.adjustProduct(productLog);
        }
    }

    public void logOrder(Order order) {
        List<OrderProduct> orderProducts = orderProductRepository.findByOrderObj(order);
        for (OrderProduct orderProduct : orderProducts) {
            if (orderProduct.getRetrn() == 0) {
                continue;
            }

            ProductBase productBase = productBaseService.getByProductId(orderProduct.getProductObj().getId());
            ProductLog productLog = new ProductLog(null, ProductLogType.RETURNING, orderProduct.getRetrn(),
                    productBase.getPurchasePrice(), new Date(System.currentTimeMillis()),
                    productBase.getProduct(), order.getEmployee());

            addProductLog(productLog);

            productBaseService.adjustProduct(productLog);
        }
    }

    public void logCheck(Check check) {
        List<CheckProductBase> checkProductBases = checkProductBaseRepository.findByCheck(check);
        for (CheckProductBase checkProductBase : checkProductBases) {
            ProductBase productBase = productBaseService.getByProductId(checkProductBase.getProductBase().getProduct().getId());
            ProductLog productLog = new ProductLog(null, ProductLogType.CHECKINGOUT, checkProductBase.getCount(),
                    productBase.getPurchasePrice(), new Date(System.currentTimeMillis()),
                    productBase.getProduct(), checkProductBase.getCheck().getEmployee());

            addProductLog(productLog);

            productBaseService.adjustProduct(productLog);
        }
    }

    public List<ProductLog> getProductLogsByEmployee(Employee employee) { return productLogRepository.getProductLogsByEmployee(employee); }
}
