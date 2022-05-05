package oop.CourseWork.startdata;

import oop.CourseWork.model.product.Product;
import oop.CourseWork.model.product.ProductRepository;
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
        productRepository.save(new Product(0L, "Кефир", null, Collections.emptySet(), Collections.emptySet(), Collections.emptySet()));
        productRepository.save(new Product(0L, "Молоко", null, Collections.emptySet(), Collections.emptySet(), Collections.emptySet()));
        productRepository.save(new Product(0L, "Сметана", null, Collections.emptySet(), Collections.emptySet(), Collections.emptySet()));
        productRepository.save(new Product(0L, "Плавленный сыр",  null, Collections.emptySet(), Collections.emptySet(), Collections.emptySet()));
        productRepository.save(new Product(0L, "Ряженка", null, Collections.emptySet(), Collections.emptySet(), Collections.emptySet()));
    }
}
