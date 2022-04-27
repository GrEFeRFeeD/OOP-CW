package oop.CourseWork.model.productBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductBaseService {

    @Autowired
    ProductBaseRepository productBaseRepository;

    public void addProductBase(ProductBase productBase) {
        productBaseRepository.save(productBase);
    }

    public List<ProductBase> getAllProductBases() {
        return productBaseRepository.findAll();
    }
}
