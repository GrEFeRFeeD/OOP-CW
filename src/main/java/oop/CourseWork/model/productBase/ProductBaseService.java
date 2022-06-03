package oop.CourseWork.model.productBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductBaseService {

    private ProductBaseRepository productBaseRepository;

    @Autowired
    public ProductBaseService(ProductBaseRepository productBaseRepository) {
        this.productBaseRepository = productBaseRepository;
    }

    public boolean isProductBaseExists(Long productBaseId) { return productBaseRepository.findById(productBaseId).isPresent(); }

    public int getAvailableProductCount(Long productBaseId) { return productBaseRepository.findById(productBaseId).get().getCount();}
}
