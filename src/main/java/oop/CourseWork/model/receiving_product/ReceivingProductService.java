package oop.CourseWork.model.receiving_product;

import oop.CourseWork.model.order_product.OrderProduct;
import oop.CourseWork.model.product.Product;
import oop.CourseWork.model.product.ProductRepository;
import oop.CourseWork.model.receiving.Receiving;
import oop.CourseWork.model.receiving.ReceivingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReceivingProductService {

    private final ProductRepository productRepository;
    private final ReceivingRepository receivingRepository;
    private final ReceivingProductRepository receivingProductRepository;

    @Autowired
    public ReceivingProductService(ProductRepository productRepository, ReceivingRepository receivingRepository, ReceivingProductRepository receivingProductRepository) {
        this.productRepository = productRepository;
        this.receivingRepository = receivingRepository;
        this.receivingProductRepository = receivingProductRepository;
    }

    public void addReceivingProduct(ReceivingProduct receivingProduct) {
        receivingProductRepository.save(receivingProduct);
    }

    public void addOneReceivingProduct(Long receivingId, Long productId) {

        Product product = productRepository.getById(productId);
        Receiving receiving = receivingRepository.getById(receivingId);

        List<ReceivingProduct> receivingProducts = receivingProductRepository.getReceivingProductsByReceiving(receiving);
        for (ReceivingProduct receivingProduct : receivingProducts) {
            if (Objects.equals(receivingProduct.getProduct().getId(), productId)) {
                receivingProduct.setCount(receivingProduct.getCount() + 1);
                addReceivingProduct(receivingProduct);
                return;
            }
        }

        ReceivingProduct newReceivingProduct = new ReceivingProduct(new ReceivingProductKey(),
                receiving, product, 1, product.getPrice());
        addReceivingProduct(newReceivingProduct);
    }

    public void setReceivingProductCount(Long receivingId, Long productId, int count) {
        ReceivingProduct receivingProduct = receivingProductRepository.getById(new ReceivingProductKey(receivingId, productId));
        if (count > 0) {
            receivingProduct.setCount(count);
            receivingProductRepository.save(receivingProduct);
        }
        if (count == 0) {
            receivingProductRepository.delete(receivingProduct);
        }
    }

    public List<ReceivingProduct> getReceivingProductsByReceiving(Receiving receiving) {
        return receivingProductRepository.getReceivingProductsByReceiving(receiving);
    }

    public Map<OrderProduct, String> getReceivingProductOrderProductDifference(List<OrderProduct> subtrahendListOrig, List<ReceivingProduct> minuendListOrig) {
        List<OrderProduct> subtrahendList = new ArrayList<>();
        for (OrderProduct op : subtrahendListOrig) subtrahendList.add(new OrderProduct(op.getId(), op.getOrderObj(), op.getProductObj(), op.getBalance(), op.getOrder(), op.getRetrn()));
        List<ReceivingProduct> minuendList = new ArrayList<>();
        for (ReceivingProduct rp : minuendListOrig) minuendList.add(new ReceivingProduct(rp.getId(), rp.getReceiving(), rp.getProduct(), rp.getCount(), rp.getPrice()));

        Map<OrderProduct, String> difference = new HashMap<>();

        for (OrderProduct subtrahend : subtrahendList) {
            boolean isMinuendConsists = false;
            for (ReceivingProduct minuend : minuendList) {
                if (minuend.getProduct().equals(subtrahend.getProductObj())) {
                    isMinuendConsists = true;
                    if (subtrahend.getOrder() > minuend.getCount()) {
                        // Shortage
                        subtrahend.setOrder(subtrahend.getOrder() - minuend.getCount());
                        difference.put(subtrahend, "yellow");
                    } else {
                        if (subtrahend.getOrder() < minuend.getCount()) {
                            // Overdone
                            subtrahend.setOrder(minuend.getCount() - subtrahend.getOrder());
                            difference.put(subtrahend, "orange");
                        }
                    }
                    break;
                }
            }
            if (!isMinuendConsists) {
                // Not taken
                difference.put(subtrahend, "red");
            }
        }

        for (ReceivingProduct minuend : minuendList) {
            boolean isSubtrahendConsists = false;
            for (OrderProduct subtrahend: subtrahendList) {
                if (minuend.getProduct().equals(subtrahend.getProductObj())) {
                    isSubtrahendConsists = true;
                    break;
                }
            }
            if (!isSubtrahendConsists) {
                // Taken something different
                difference.put(new OrderProduct(null, null, minuend.getProduct(), 0,
                        minuend.getCount(), 0), "lightblue");
            }
        }

        return difference;
    }

}
