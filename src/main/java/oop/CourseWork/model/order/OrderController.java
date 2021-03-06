package oop.CourseWork.model.order;

import oop.CourseWork.model.order_product.OrderProduct;
import oop.CourseWork.model.order_product.OrderProductService;
import oop.CourseWork.model.provider.Provider;
import oop.CourseWork.model.provider.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final ProviderService providerService;
    private final OrderProductService orderProductService;

    @Autowired
    public OrderController(OrderService orderService, ProviderService providerService, OrderProductService orderProductService) {
        this.orderService = orderService;
        this.providerService = providerService;
        this.orderProductService = orderProductService;
    }

    @GetMapping("/orders")
    public String redirectToOrdersPage() {
        return "redirect:/orders/all";
    }

    @GetMapping("/orders/all")
    public String getOrderList(Model model) {

        List<Order> orderList = orderService.getAllOrders();
        model.addAttribute("orders", orderList);
        model.addAttribute("providers", providerService.getAllProviders());
        model.addAttribute("orderSums", orderService.getAllOrderSums());
        if (!orderList.isEmpty()) {
            model.addAttribute("min_date", new Date((orderList.stream().map(o -> o.getDate().getTime()).min(Comparator.comparingLong(Long::longValue))).get()));
            model.addAttribute("max_date", new Date((orderList.stream().map(o -> o.getDate().getTime()).max(Comparator.comparingLong(Long::longValue))).get()));
        }
        return "orderlist";
    }

    @GetMapping("/orders/new_order")
    public String getNewOrder(@RequestParam(value = "provider") Long providerId, Model model) {

        Provider provider = providerService.getProviderById(providerId);
        Order order = orderService.addOrder(new Order(), providerId);

        return "redirect:/orders/" + order.getId();
    }

    @GetMapping("/orders/{order_id}")
    public String getOrder(@PathVariable(name = "order_id") Long orderId, Model model) {

        orderService.adjustOrderStatus(orderId);

        List<OrderProduct> orderProducts = orderProductService.getOrderProductsByOrder(orderId);
        model.addAttribute("order_products", orderProducts);

        Order order = orderService.getOrderById(orderId);
        model.addAttribute("order_id", orderId);
        model.addAttribute("provider_id", order.getProvider().getId());
        model.addAttribute("cur_date", order.getDate());
        model.addAttribute("status", order.getStatus());

        List<Order> orders = orderService.getOrdersByProvider(order.getProvider().getId());
        model.addAttribute("orders", orders);

        List<Order> tempOrders = orderService.getOrdersByProvider(order.getProvider().getId());
        tempOrders.remove(order);
        if (!tempOrders.isEmpty()) {
            model.addAttribute("prev_date", new Date((tempOrders.stream().map(o -> o.getDate().getTime()).max(Comparator.comparingLong(Long::longValue))).get()));
        }

        List<Provider> providers = providerService.getAllProviders();
        model.addAttribute("providers", providers);

        return "order";
    }

    @GetMapping(value = "/orders", params = "provider")
    public String getOrderByProvider(@RequestParam(value = "provider") Long providerId, Model model) {
        Provider provider = providerService.getProviderById(providerId);
        model.addAttribute("provider_id", providerId);

        List<Provider> providers = providerService.getAllProviders();
        model.addAttribute("providers", providers);

        List<Order> orders = orderService.getOrdersByProvider(providerId);
        model.addAttribute("orders", orders);
        return "order";
    }

    @GetMapping("/orders/{id}/delete")
    public String deleteOrder(@PathVariable(name = "id") Long orderId) {
        orderService.deleteOrder(orderId);
        return "redirect:/orders/all";
    }

    @PostMapping("/orders/{id}")
    public String editOrder(@PathVariable(name = "id") Long orderId,
                            @RequestParam(value = "productId[]") List<Long> productIdList,
                            @RequestParam(value = "balance[]") List<Integer> balanceList,
                            @RequestParam(value = "order[]") List<Integer> orderList,
                            @RequestParam(value = "return[]") List<Integer> returnList,
                            Model model) {

        Iterator<Integer> balanceIterator = balanceList.iterator();
        Iterator<Integer> orderIterator = orderList.iterator();
        Iterator<Integer> returnIterator = returnList.iterator();
        for (Long productId: productIdList) {
            OrderProduct o = orderProductService.getOrderProductById(orderId, productId);

            Integer currentValue = balanceIterator.next();
            o.setBalance(currentValue == null ? 0 : currentValue);
            currentValue = orderIterator.next();
            o.setOrder(currentValue == null ? 0 : currentValue);
            currentValue = returnIterator.next();
            o.setRetrn(currentValue == null ? 0 : currentValue);
            orderProductService.addOrderProduct(o);
        }
        return "redirect:/orders/" + orderId;
    }
}
