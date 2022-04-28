package oop.CourseWork.startdata;

import oop.CourseWork.model.product.Product;
import oop.CourseWork.model.product.ProductRepository;
import oop.CourseWork.model.productBase.ProductBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private ProductRepository productRepository;

    public void run(ApplicationArguments args) {
        productRepository.save(new Product(0, "Кефир", 123.12, null, Collections.emptySet()));
        productRepository.save(new Product(0, "Молоко", 142.32, null, Collections.emptySet()));
        productRepository.save(new Product(0, "Сметана", 35.45, null, Collections.emptySet()));
        productRepository.save(new Product(0, "Плавленный сыр", 56.37, null, Collections.emptySet()));
        productRepository.save(new Product(0, "Ряженка", 63.31, null, Collections.emptySet()));
    }
}
