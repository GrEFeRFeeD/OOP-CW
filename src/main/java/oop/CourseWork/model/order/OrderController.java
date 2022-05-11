package oop.CourseWork.model.order;

import oop.CourseWork.model.order_product.OrderProduct;
import oop.CourseWork.model.order_product.OrderProductService;
import oop.CourseWork.model.provider.Provider;
import oop.CourseWork.model.provider.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    private OrderService orderService;
    private ProviderService providerService;
    private OrderProductService orderProductService;

    @Autowired
    public OrderController(OrderService orderService, ProviderService providerService, OrderProductService orderProductService) {
        this.orderService = orderService;
        this.providerService = providerService;
        this.orderProductService = orderProductService;
    }

    @GetMapping("/orders/all")
    public String getOrderList(Model model) {

        // Show exist orders
        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("orders", orderList);

        // Add providers to upper select, new order button
        List<Provider> providerList = providerService.getAllProviders();
        model.addAttribute("providers", providerList);

        model.addAttribute("min_date", new Date((orderList.stream().map(o -> o.getDate().getTime()).min(Comparator.comparingLong(Long::longValue))).get()));
        model.addAttribute("max_date", new Date((orderList.stream().map(o -> o.getDate().getTime()).max(Comparator.comparingLong(Long::longValue))).get()));

        return "orderlist";
    }

    @GetMapping("/orders/new_order")
    public String getNewOrder(@RequestParam(value = "provider") Long providerId,
                              //@RequestParam(value = "employee") Long employeeId,
                              Model model) {

        Provider provider = providerService.getProviderById(providerId);
        //TODO: employee sign (Spring Security)
        Order order = orderService.addOrder(new Order(), providerId, null);

        return "redirect:/orders/" + order.getId();
    }

    @GetMapping("/orders/{order_id}")
    public String getOrder(@PathVariable(name = "order_id") Long orderId, Model model) {
        List<OrderProduct> orderProducts = orderProductService.getOrderProductsByOrder(orderId);
        model.addAttribute("order_products", orderProducts);

        Order order = orderService.getOrderById(orderId);
        model.addAttribute("order_id", orderId);
        model.addAttribute("provider_id", order.getProvider().getId());

        List<Order> orders = orderService.getOrdersByProvider(order.getProvider().getId());
        model.addAttribute("orders", orders);

        List<Provider> providers = providerService.getAllProviders();
        model.addAttribute("providers", providers);
        //System.out.println("P_ID ="+order.getProvider().getId()+";P_LIST="+providers);
        return "order";
    }

    @GetMapping("/orders")
    public String getOrderByProvider(@RequestParam(value = "provider") Long providerId, Model model) {
        Provider provider = providerService.getProviderById(providerId);
        model.addAttribute("provider_id", providerId);

        List<Provider> providers = providerService.getAllProviders();
        model.addAttribute("providers", providers);

        List<Order> orders = orderService.getOrdersByProvider(providerId);
        model.addAttribute("orders", orders);
        return "order";
    }
}
