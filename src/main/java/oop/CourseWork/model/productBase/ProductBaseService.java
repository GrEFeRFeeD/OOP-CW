package oop.CourseWork.model.productBase;

import oop.CourseWork.config.ShopConfig;
import oop.CourseWork.model.product.Product;
import oop.CourseWork.model.product.ProductRepository;
import oop.CourseWork.model.productLog.ProductLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ProductBaseService {

    private ProductBaseRepository productBaseRepository;
    private ProductRepository productRepository;

    @Autowired
    public ProductBaseService(ProductBaseRepository productBaseRepository, ProductRepository productRepository) {
        this.productBaseRepository = productBaseRepository;
        this.productRepository = productRepository;
    }

    public boolean isProductBaseExists(Long productBaseId) { return productBaseRepository.findById(productBaseId).isPresent(); }

    public int getAvailableProductCount(Long productBaseId) { return productBaseRepository.findById(productBaseId).get().getCount();}

    List<ProductBase> getAllProductBases() { return productBaseRepository.findAll(); }

    public ProductBase getByProductId(Long productId) { return productBaseRepository.getById(productId); }

    public void adjustProduct(ProductLog productLog) {

        Optional<ProductBase> productBaseOptional = productBaseRepository.findById(productLog.getProduct().getId());

        ProductBase productBase = productBaseOptional.orElseGet(
                () -> new ProductBase(null, 0, 0, 0, productLog.getProduct(), new HashSet<>()));

        switch (productLog.getType()) {
            case RECEIVING:
                productBase.setCount(productBase.getCount() + productLog.getCount());
                productBase.setPurchasePrice(productLog.getPrice());
                productBase.setSellingPrice(productLog.getPrice() * (1 + ShopConfig.getInstance().getMargin()));
                break;

            case CHECKINGOUT:
            case RETURNING:
                productBase.setCount(productBase.getCount() - productLog.getCount());
                break;
        }
    }

}
