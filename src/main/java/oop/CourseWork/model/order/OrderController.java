package oop.CourseWork.model.order;

import oop.CourseWork.model.provider.Provider;
import oop.CourseWork.model.provider.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {

    private OrderService orderService;
    private ProviderService providerService;

    @Autowired
    public OrderController(OrderService orderService, ProviderService providerService) {
        this.orderService = orderService;
        this.providerService = providerService;
    }

    @GetMapping("/orders")
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
}
