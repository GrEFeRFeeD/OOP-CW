package oop.CourseWork.model.productLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductLogController {

    private ProductLogService productLogService;

    @Autowired
    public ProductLogController(ProductLogService productLogService) {
        this.productLogService = productLogService;
    }

    @GetMapping("/productlog")
    public String getProductLogs(Model model) {

        List<ProductLog> productLogs = productLogService.getAllProductLogs();
        model.addAttribute("productLogs", productLogs);

        // TODO: min, max date; employee list; product groups

        return "productlog";
    }
}
