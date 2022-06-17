package oop.CourseWork.controllers;

import oop.CourseWork.model.employee.EmployeeService;
import oop.CourseWork.model.order.Order;
import oop.CourseWork.model.order.OrderService;
import oop.CourseWork.model.provider.Provider;
import oop.CourseWork.model.provider.ProviderService;
import oop.CourseWork.model.receiving.Receiving;
import oop.CourseWork.model.receiving.ReceivingService;
import oop.CourseWork.model.receiving.ReceivingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
public class ManagePageController {

    private final OrderService orderService;
    private final ProviderService providerService;
    private final ReceivingService receivingService;
    private final EmployeeService employeeService;

    @Autowired
    public ManagePageController(OrderService orderService, ProviderService providerService, ReceivingService receivingService, EmployeeService employeeService) {
        this.orderService = orderService;
        this.providerService = providerService;
        this.receivingService = receivingService;
        this.employeeService = employeeService;
    }

    @GetMapping("/manage_page")
    public String getManagePage(Model model) {

        List<Order> orderList = orderService.getAllOrders().stream()
                .filter(o -> 7 >= TimeUnit.DAYS.convert(
                        Math.abs(System.currentTimeMillis() - o.getDate().getTime()), TimeUnit.MILLISECONDS))
                .collect(Collectors.toList());
        model.addAttribute("orders", orderList);

        Map<Long, Map<String, Double>> orderSums = orderService.getAllOrderSums();
        model.addAttribute("orderSums", orderSums);

        List<Provider> providers = providerService.getAllProviders();
        model.addAttribute("providers", providers);

        List<Receiving> receivings = receivingService.getAllReceivings().stream()
                .filter(r -> r.getStatus() == ReceivingStatus.CLOSED)
                .filter(r -> 7 >= TimeUnit.DAYS.convert(
                        Math.abs(System.currentTimeMillis() - r.getDate().getTime()), TimeUnit.MILLISECONDS))
                .collect(Collectors.toList());
        model.addAttribute("receivings", receivings);

        model.addAttribute("isAdmin", employeeService.isCurrentEmployeeAdmin());

        Map<Long, Double> receivingSums = receivingService.getAllReceivingSums();
        model.addAttribute("receivingSums", receivingSums);

        return "managepage";
    }

    @GetMapping("/manage_page/new_provider")
    public String addNewProvider(@RequestParam String name,
                                 @RequestParam String email,
                                 @RequestParam String phoneNumber) {

        Provider provider = new Provider(null, name, email, phoneNumber, new HashSet<>());
        providerService.addProvider(provider);
        return "redirect:/manage_page";
    }

    @GetMapping("/manage_page/delete_provider")
    public String deleteProvider(@RequestParam(name = "id") Long providerId) {

        providerService.deleteProvider(providerId);
        return "redirect:/manage_page";
    }

}
