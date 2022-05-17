package oop.CourseWork.startdata;

import oop.CourseWork.model.employee.Employee;
import oop.CourseWork.model.employee.EmployeeRepository;
import oop.CourseWork.model.order.Order;
import oop.CourseWork.model.order.OrderRepository;
import oop.CourseWork.model.order_product.OrderProduct;
import oop.CourseWork.model.order_product.OrderProductKey;
import oop.CourseWork.model.order_product.OrderProductRepository;
import oop.CourseWork.model.product.Product;
import oop.CourseWork.model.product.ProductRepository;
import oop.CourseWork.model.provider.Provider;
import oop.CourseWork.model.provider.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProviderRepository providerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderProductRepository orderProductRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public void run(ApplicationArguments args) {
        System.out.println(new Date(System.currentTimeMillis()) + " oop.CourseWork.startdata.DataLoader: Starting loading the start data...");
        Product pt1 = new Product(null, "Кефір", 45.32, null, new HashSet<>(), new HashSet<>(), new HashSet<>());
        Product pt2 = new Product(null, "Молоко", 23.41, null, new HashSet<>(), new HashSet<>(), new HashSet<>());
        Product pt3 = new Product(null, "Сметана", 38.90, null, new HashSet<>(), new HashSet<>(), new HashSet<>());
        Product pt4 = new Product(null, "Плавлений сир", 12.10,  null, new HashSet<>(), new HashSet<>(), new HashSet<>());
        Product pt5 = new Product(null, "Ряженка", 28.74, null, new HashSet<>(), new HashSet<>(), new HashSet<>());

        Employee e1 = new Employee(null, "Пригорченко", "Василь", "Миколайович", "+380964444444", "Менеджер", "manag1", "bebebe", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        Employee e2 = new Employee(null, "Голослівна", "Олена", "Петрівна", "+380965555555", "Касир", "cashi1", "bebebe", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        Employee e3 = new Employee(null, "Незабудько", "Грегор", "Модемович", "+380966666666", "Прийомщик", "rec1", "bebebe", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());

        Provider pr1 = new Provider(null, "ТОВ Біла лінія", "Email1@example.com", "+380781111111", new HashSet<>());
        Provider pr2 = new Provider(null, "ТОВ ДЛКМ", "Email2@example.com", "+380782222222", new HashSet<>());
        Provider pr3 = new Provider(null, "ФОП Стась О. С.", "Email3@example.com", "+380783333333", new HashSet<>());

        Order o1 = new Order(null, new Date(120, 10, 10), pr1, e1, new HashSet<>(), new HashSet<>(), new HashSet<>());
        pr1.addOrder(o1);
        e1.addOrder(o1);
        Order o2 = new Order(null, new Date(120, 11, 10), pr2, e1, new HashSet<>(), new HashSet<>(), new HashSet<>());
        pr2.addOrder(o2);
        e1.addOrder(o2);

        OrderProduct op11 = new OrderProduct(new OrderProductKey(), o1, pt1, 3, 15, 1);
        o1.addOrderBody(op11);
        pt1.addProductBody(op11);
        OrderProduct op12 = new OrderProduct(new OrderProductKey(), o1, pt2, 1, 4, 2);
        o1.addOrderBody(op12);
        pt2.addProductBody(op12);
        OrderProduct op13 = new OrderProduct(new OrderProductKey(), o1, pt3, 5, 6, 11);
        o1.addOrderBody(op13);
        pt3.addProductBody(op13);
        OrderProduct op14 = new OrderProduct(new OrderProductKey(), o1, pt5, 4, 10, 3);
        o1.addOrderBody(op14);
        pt5.addProductBody(op14);
        OrderProduct op21 = new OrderProduct(new OrderProductKey(), o2, pt4, 0, 14, 4);
        o1.addOrderBody(op21);
        pt4.addProductBody(op21);
        OrderProduct op22 = new OrderProduct(new OrderProductKey(), o2, pt5, 2, 11, 0);
        o1.addOrderBody(op22);
        pt5.addProductBody(op22);

        productRepository.save(pt1);
        productRepository.save(pt2);
        productRepository.save(pt3);
        productRepository.save(pt4);
        productRepository.save(pt5);

        employeeRepository.save(e1);
        employeeRepository.save(e2);
        employeeRepository.save(e3);

        providerRepository.save(pr1);
        providerRepository.save(pr2);
        providerRepository.save(pr3);

        orderRepository.save(o1);
        orderRepository.save(o2);

        orderProductRepository.save(op11);
        orderProductRepository.save(op12);
        orderProductRepository.save(op13);
        orderProductRepository.save(op14);
        orderProductRepository.save(op21);
        orderProductRepository.save(op22);

        System.out.println(new Date(System.currentTimeMillis()) + " oop.CourseWork.startdata.DataLoader: Start data successfully loaded.");
    }
}
