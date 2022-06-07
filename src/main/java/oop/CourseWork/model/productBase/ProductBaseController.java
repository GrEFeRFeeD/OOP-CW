package oop.CourseWork.model.productBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProductBaseController {

    private ProductBaseService productBaseService;

    @Autowired
    public ProductBaseController(ProductBaseService productBaseService) {
        this.productBaseService = productBaseService;
    }

    @GetMapping("/productbase")
    public String getProductBases(Model model) {

        List<ProductBase> productBases = productBaseService.getAllProductBases();
        model.addAttribute("productBases", productBases);

        // TODO: min, max date; product groups

        return "productbase";
    }
}
