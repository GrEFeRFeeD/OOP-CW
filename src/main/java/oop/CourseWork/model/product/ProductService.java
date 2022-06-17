package oop.CourseWork.model.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id) { return productRepository.getById(id); }

    public boolean isProductExists(Long productId) {
        return productRepository.findById(productId).isPresent();
    }

}
