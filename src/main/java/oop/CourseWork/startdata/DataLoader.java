package oop.CourseWork.startdata;

import oop.CourseWork.config.SecurityConfig;
import oop.CourseWork.config.ShopConfig;
import oop.CourseWork.model.check.Check;
import oop.CourseWork.model.check.CheckRepository;
import oop.CourseWork.model.check.CheckStatus;
import oop.CourseWork.model.check_productBase.CheckProductBase;
import oop.CourseWork.model.check_productBase.CheckProductBaseKey;
import oop.CourseWork.model.check_productBase.CheckProductBaseRepository;
import oop.CourseWork.model.employee.Employee;
import oop.CourseWork.model.employee.EmployeeRepository;
import oop.CourseWork.model.order.Order;
import oop.CourseWork.model.order.OrderRepository;
import oop.CourseWork.model.order.OrderStatus;
import oop.CourseWork.model.order_product.OrderProduct;
import oop.CourseWork.model.order_product.OrderProductKey;
import oop.CourseWork.model.order_product.OrderProductRepository;
import oop.CourseWork.model.product.Product;
import oop.CourseWork.model.product.ProductRepository;
import oop.CourseWork.model.productBase.ProductBase;
import oop.CourseWork.model.productBase.ProductBaseRepository;
import oop.CourseWork.model.productLog.ProductLog;
import oop.CourseWork.model.productLog.ProductLogRepository;
import oop.CourseWork.model.productLog.ProductLogType;
import oop.CourseWork.model.provider.Provider;
import oop.CourseWork.model.provider.ProviderRepository;
import oop.CourseWork.model.receiving.Receiving;
import oop.CourseWork.model.receiving.ReceivingRepository;
import oop.CourseWork.model.receiving.ReceivingStatus;
import oop.CourseWork.model.role.Role;
import oop.CourseWork.model.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.*;

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
    @Autowired
    private ReceivingRepository receivingRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private SecurityConfig securityConfig;
    @Autowired
    private CheckRepository checkRepository;
    @Autowired
    private ProductBaseRepository productBaseRepository;
    @Autowired
    private CheckProductBaseRepository checkProductBaseRepository;
    @Autowired
    private ProductLogRepository productLogRepository;

    public void run(ApplicationArguments args) {
        System.out.println(new Date(System.currentTimeMillis()) + " oop.CourseWork.startdata.DataLoader: Starting loading the start data...");

        ShopConfig shopConfig = ShopConfig.getInstance("Одеса", "вул. Шевченко, 1", 0.15);

        Product pt1 = new Product(null, new ArrayList<>(), "Кефір", 45.32, null, new HashSet<>(), new HashSet<>(), new HashSet<>());
        Product pt2 = new Product(null, new ArrayList<>(), "Молоко", 23.41, null, new HashSet<>(), new HashSet<>(), new HashSet<>());
        Product pt3 = new Product(null, new ArrayList<>(), "Сметана", 38.90, null, new HashSet<>(), new HashSet<>(), new HashSet<>());
        Product pt4 = new Product(null, new ArrayList<>(), "Плавлений сир", 12.10,  null, new HashSet<>(), new HashSet<>(), new HashSet<>());
        Product pt5 = new Product(null, new ArrayList<>(), "Ряженка", 28.74, null, new HashSet<>(), new HashSet<>(), new HashSet<>());

        Role role1 = new Role(null, "ROLE_MANAGER", new HashSet<>());
        Role role2 = new Role(null, "ROLE_CASHIER", new HashSet<>());
        Role role3 = new Role(null, "ROLE_RECEIVER", new HashSet<>());
        Role role4 = new Role(null, "ROLE_ADMIN", new HashSet<>());

        Employee e1 = new Employee(null, "Пригорченко", "Василь", "Миколайович", "+380964444444", "Менеджер", "manager1", securityConfig.passwordEncoder().encode("bebebe"), "bebebe", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        Employee e2 = new Employee(null, "Голослівна", "Олена", "Петрівна", "+380965555555", "Касир", "cashier1", securityConfig.passwordEncoder().encode("bebebe"), "bebebe", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        Employee e3 = new Employee(null, "Незабудько", "Грегор", "Модемович", "+380966666666", "Прийомщик", "receiver1", securityConfig.passwordEncoder().encode("bebebe"), "bebebe", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        Employee e4 = new Employee(null, "Чаїна", "Юлія", "Григоріївна", "+380911111111", "Адміністратор", "admin1", securityConfig.passwordEncoder().encode("bebebe"), "bebebe", new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>());
        e1.addRole(role1);
        role1.addEmployee(e1);
        e2.addRole(role2);
        role2.addEmployee(e2);
        e3.addRole(role3);
        role3.addEmployee(e3);
        e4.addRole(role4);
        role4.addEmployee(e4);

        Provider pr1 = new Provider(null, "ТОВ Біла лінія", "Email1@example.com", "+380781111111", new HashSet<>());
        Provider pr2 = new Provider(null, "ТОВ ДЛКМ", "Email2@example.com", "+380782222222", new HashSet<>());
        Provider pr3 = new Provider(null, "ФОП Стась О. С.", "Email3@example.com", "+380783333333", new HashSet<>());

        Order o1 = new Order(null, new Date(120, 10, 10), OrderStatus.OPEN, pr1, e1, new HashSet<>(), new HashSet<>(), new HashSet<>());
        pr1.addOrder(o1);
        e1.addOrder(o1);
        Order o2 = new Order(null, new Date(120, 11, 10), OrderStatus.OPEN, pr2, e1, new HashSet<>(), new HashSet<>(), new HashSet<>());
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

        Receiving r1 = new Receiving(null, new Date(System.currentTimeMillis()), ReceivingStatus.OPEN, o1, null, new HashSet<>());
        o1.addReceiving(r1);

        //Check c1 = new Check(null, new Date(System.currentTimeMillis()), CheckStatus.CLOSED, e2, new HashSet<>());
        //e2.addCheck(c1);

        //Check c2 = new Check(null, new Date(System.currentTimeMillis() + 100000), CheckStatus.OPEN, e2, new HashSet<>());
        //e2.addCheck(c2);

        ProductBase pb1 = new ProductBase(null, 43, pt1.getPrice(), pt1.getPrice()*(shopConfig.getMargin() + 1), pt1, new HashSet<>());
        pt1.setProductBase(pb1);
        ProductBase pb2 = new ProductBase(null, 36, pt2.getPrice(), pt2.getPrice()*(shopConfig.getMargin() + 1), pt2, new HashSet<>());
        pt2.setProductBase(pb2);
        ProductBase pb3 = new ProductBase(null, 57, pt3.getPrice(), pt3.getPrice()*(shopConfig.getMargin() + 1), pt3, new HashSet<>());
        pt3.setProductBase(pb3);
        ProductBase pb4 = new ProductBase(null, 24, pt4.getPrice(), pt4.getPrice()*(shopConfig.getMargin() + 1), pt4, new HashSet<>());
        pt4.setProductBase(pb4);
        ProductBase pb5 = new ProductBase(null, 10, pt5.getPrice(), pt5.getPrice()*(shopConfig.getMargin() + 1), pt5, new HashSet<>());
        pt5.setProductBase(pb5);

        /*CheckProductBase cpb11 = new CheckProductBase(new CheckProductBaseKey(), c1, pb1, 4, pb1.getSellingPrice());
        c1.addCheckBody(cpb11);
        pb1.addProductBaseBody(cpb11);
        CheckProductBase cpb12 = new CheckProductBase(new CheckProductBaseKey(), c1, pb3, 3, pb3.getSellingPrice());
        c1.addCheckBody(cpb12);
        pb3.addProductBaseBody(cpb12);
        CheckProductBase cpb13 = new CheckProductBase(new CheckProductBaseKey(), c1, pb5, 2, pb5.getSellingPrice());
        c1.addCheckBody(cpb13);
        pb5.addProductBaseBody(cpb13);
        CheckProductBase cpb21 = new CheckProductBase(new CheckProductBaseKey(), c2, pb2, 5, pb2.getSellingPrice());
        c2.addCheckBody(cpb21);
        pb2.addProductBaseBody(cpb21);
        CheckProductBase cpb22 = new CheckProductBase(new CheckProductBaseKey(), c2, pb4, 1, pb4.getSellingPrice());
        c2.addCheckBody(cpb22);
        pb4.addProductBaseBody(cpb22);*/

        /*ProductLog pl1 = new ProductLog(null, ProductLogType.RECEIVING, pb1.getCount(), pb1.getPurchasePrice(), new Date(System.currentTimeMillis()), pb1.getProduct(), e3);
        pb1.getProduct().addProductLog(pl1);
        e3.addProductLog(pl1);
        ProductLog pl2 = new ProductLog(null, ProductLogType.CHECKINGOUT, 10, pb1.getSellingPrice(), new Date(System.currentTimeMillis() + 1000000), pb1.getProduct(), e2);
        pb1.getProduct().addProductLog(pl2);
        e2.addProductLog(pl2);
        ProductLog pl3 = new ProductLog(null, ProductLogType.RETURNING, 5, pb1.getPurchasePrice(), new Date(System.currentTimeMillis()), pb1.getProduct(), e1);
        pb1.getProduct().addProductLog(pl3);
        e1.addProductLog(pl3);*/

        productRepository.save(pt1);
        productRepository.save(pt2);
        productRepository.save(pt3);
        productRepository.save(pt4);
        productRepository.save(pt5);

        roleRepository.save(role1);
        roleRepository.save(role2);
        roleRepository.save(role3);
        roleRepository.save(role4);

        employeeRepository.save(e1);
        employeeRepository.save(e2);
        employeeRepository.save(e3);
        employeeRepository.save(e4);

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

        //receivingRepository.save(r1);

        //checkRepository.save(c1);
        //checkRepository.save(c2);

        /*checkProductBaseRepository.save(cpb11);
        checkProductBaseRepository.save(cpb12);
        checkProductBaseRepository.save(cpb13);
        checkProductBaseRepository.save(cpb21);
        checkProductBaseRepository.save(cpb22);*/

        productBaseRepository.save(pb1);
        productBaseRepository.save(pb2);
        productBaseRepository.save(pb3);
        productBaseRepository.save(pb4);
        productBaseRepository.save(pb5);

        /*productLogRepository.save(pl1);
        productLogRepository.save(pl2);
        productLogRepository.save(pl3);*/

        System.out.println(new Date(System.currentTimeMillis()) + " oop.CourseWork.startdata.DataLoader: Start data successfully loaded.");
    }
}
