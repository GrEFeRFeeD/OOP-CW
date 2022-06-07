package oop.CourseWork.model.receiving;

import oop.CourseWork.model.employee.EmployeeService;
import oop.CourseWork.model.order.Order;
import oop.CourseWork.model.order.OrderService;
import oop.CourseWork.model.order_product.OrderProduct;
import oop.CourseWork.model.order_product.OrderProductService;
import oop.CourseWork.model.product.Product;
import oop.CourseWork.model.product.ProductService;
import oop.CourseWork.model.provider.Provider;
import oop.CourseWork.model.provider.ProviderService;
import oop.CourseWork.model.receiving_product.ReceivingProduct;
import oop.CourseWork.model.receiving_product.ReceivingProductKey;
import oop.CourseWork.model.receiving_product.ReceivingProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class ReceivingController {

    private ReceivingService receivingService;
    private ProviderService providerService;
    private OrderService orderService;
    private OrderProductService orderProductService;
    private ProductService productService;
    private ReceivingProductService receivingProductService;
    private EmployeeService employeeService;

    @Autowired
    public ReceivingController(ReceivingService receivingService, ProviderService providerService, OrderService orderService, OrderProductService orderProductService, ProductService productService, ReceivingProductService receivingProductService, EmployeeService employeeService) {
        this.receivingService = receivingService;
        this.providerService = providerService;
        this.orderService = orderService;
        this.orderProductService = orderProductService;
        this.productService = productService;
        this.receivingProductService = receivingProductService;
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/receivings")
    public String getEmptyPage(Model model){

        List<Receiving> receivings = receivingService.getAllReceivings();
        model.addAttribute("receivings", receivings);

        List<Provider> providers = providerService.getAllProviders();
        model.addAttribute("providers", providers);

        return "receiving";
    }

    @GetMapping(value = "/receivings", params = {"provider"})
    public String getEmptyPageWithProvider(@RequestParam(name = "provider") Long providerId, Model model){

        List<Receiving> receivings = receivingService.getAllReceivings();
        model.addAttribute("receivings", receivings);

        List<Provider> providers = providerService.getAllProviders();
        model.addAttribute("providers", providers);
        model.addAttribute("providerId", providerId);

        List<Order> orders = orderService.getOrdersByProvider(providerId);
        model.addAttribute("orders", orders);

        return "receiving";
    }

    @GetMapping(value = "/receivings", params = {"provider", "order"})
    public String getEmptyPageWithOrder(@RequestParam(name = "provider") Long providerId,
                                        @RequestParam(name = "order") Long orderId,
                                        Model model){

        List<Receiving> receivings = receivingService.getAllReceivings();
        model.addAttribute("receivings", receivings);

        List<Provider> providers = providerService.getAllProviders();
        model.addAttribute("providers", providers);
        model.addAttribute("providerId", providerId);

        List<Order> orders = orderService.getOrdersByProvider(providerId);
        model.addAttribute("orders", orders);
        model.addAttribute("orderId", orderId);

        List<OrderProduct> orderProducts = orderProductService.getOrderProductsByOrder(orderId);
        model.addAttribute("orderProducts", orderProducts);

        return "receiving";
    }

    @GetMapping("/receivings/{id}")
    public String getReceiving(@PathVariable(name = "id") Long receivingId, Model model) {

        List<Receiving> receivings = receivingService.getAllReceivings();
        model.addAttribute("receivings", receivings);

        List<Provider> providers = providerService.getAllProviders();
        model.addAttribute("providers", providers);

        Receiving receiving = receivingService.getReceivingById(receivingId);
        List<Order> orders = orderService.getOrdersByProvider(receiving.getOrder().getProvider().getId());
        model.addAttribute("orders", orders);

        model.addAttribute("providerId", receiving.getOrder().getProvider().getId());
        model.addAttribute("orderId", receiving.getOrder().getId());
        model.addAttribute("receivingId", receivingId);

        List<OrderProduct> orderProducts = orderProductService.getOrderProductsByOrder(receiving.getOrder().getId());
        model.addAttribute("orderProducts", orderProducts);

        List<ReceivingProduct> receivingProducts = receivingProductService.getReceivingProductsByReceiving(receiving);
        model.addAttribute("receivingProducts", receivingProducts);

        Map<OrderProduct, String> difference = receivingProductService.getReceivingProductOrderProductDifference(orderProducts, receivingProducts);
        model.addAttribute("differenceMap", difference);
        model.addAttribute("differenceKey", difference.keySet());

        model.addAttribute("status", receiving.getStatus());

        return "receiving";
    }

    @GetMapping(value = "/receivings/{id}", params = {"product"})
    public String addProduct(@PathVariable(value = "id") Long receivingId,
                             @RequestParam(name = "product") Long productId,
                             Model model) {

        if (!productService.isProductExists(productId)) {
            model.addAttribute("inputValue", productId);
            return getReceiving(receivingId, model);
        }

        receivingProductService.addOneReceivingProduct(receivingId, productId);
        return "redirect:/receivings/" + receivingId;
    }

    @GetMapping(value = "/receivings/{id}", params = {"product", "count"})
    public String editProductCount(@PathVariable(value = "id") Long receivingId,
                                   @RequestParam(name = "product") Long productId,
                                   @RequestParam(name = "count") int count) {

        receivingProductService.setReceivingProductCount(receivingId, productId, count);
        return "redirect:/receivings/" + receivingId;
    }

    @GetMapping("/receivings/{id}/close")
    public String closeReceiving(@PathVariable(value = "id") Long receivingId, Model model) {

        receivingService.closeReceiving(receivingId);
        return "redirect:/receivings/" + receivingId;
    }

    @GetMapping("/receivings/new_receiving")
    public String newReceiving(@RequestParam(name = "order") Long orderId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Receiving receiving = new Receiving(null,
                new Date(System.currentTimeMillis()),
                ReceivingStatus.OPEN,
                orderService.getOrderById(orderId),
                employeeService.findEmployeeByUsername(authentication.getName()), new HashSet<>());
        Long receivingId = receivingService.addReceiving(receiving).getId();
        return "redirect:/receivings/" + receivingId;
    }

}
