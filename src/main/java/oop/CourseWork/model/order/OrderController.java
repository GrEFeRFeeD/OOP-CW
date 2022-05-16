package oop.CourseWork.model.order;

import oop.CourseWork.model.order_product.OrderProduct;
import oop.CourseWork.model.order_product.OrderProductKey;
import oop.CourseWork.model.order_product.OrderProductService;
import oop.CourseWork.model.product.ProductService;
import oop.CourseWork.model.provider.Provider;
import oop.CourseWork.model.provider.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class OrderController {

    private OrderService orderService;
    private ProviderService providerService;
    private OrderProductService orderProductService;
    private ProductService productService;

    @Autowired
    public OrderController(OrderService orderService, ProviderService providerService, OrderProductService orderProductService, ProductService productService) {
        this.orderService = orderService;
        this.providerService = providerService;
        this.orderProductService = orderProductService;
        this.productService = productService;
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
        model.addAttribute("cur_date", order.getDate());

        List<Order> orders = orderService.getOrdersByProvider(order.getProvider().getId());
        model.addAttribute("orders", orders);

        orders.remove(order);
        if (!orders.isEmpty()) {
            model.addAttribute("prev_date", new Date((orders.stream().map(o -> o.getDate().getTime()).max(Comparator.comparingLong(Long::longValue))).get()));
        }

        List<Provider> providers = providerService.getAllProviders();
        model.addAttribute("providers", providers);

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

        List<OrderProduct> orderProductList = new ArrayList<>();
        Iterator<Integer> balanceIterator = balanceList.iterator();
        Iterator<Integer> orderIterator = orderList.iterator();
        Iterator<Integer> returnIterator = returnList.iterator();
        for (Long productId: productIdList) {
            OrderProduct o = orderProductService.getOrderProductById(orderId, productId);
            o.setBalance(balanceIterator.next());
            o.setOrder(orderIterator.next());
            o.setRetrn(returnIterator.next());
            orderProductService.addOrderProduct(o);
        }
        return "redirect:/orders/" + orderId;
    }
}
