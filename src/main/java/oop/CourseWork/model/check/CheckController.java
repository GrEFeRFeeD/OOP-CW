package oop.CourseWork.model.check;

import oop.CourseWork.model.check_productBase.CheckProductBase;
import oop.CourseWork.model.check_productBase.CheckProductBaseService;
import oop.CourseWork.model.employee.Employee;
import oop.CourseWork.model.employee.EmployeeService;
import oop.CourseWork.model.productBase.ProductBaseService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CheckController {

    private CheckService checkService;
    private CheckProductBaseService checkProductBaseService;
    private EmployeeService employeeService;
    private ProductBaseService productBaseService;

    @Autowired
    public CheckController(CheckService checkService, CheckProductBaseService checkProductBaseService, EmployeeService employeeService, ProductBaseService productBaseService) {
        this.checkService = checkService;
        this.checkProductBaseService = checkProductBaseService;
        this.employeeService = employeeService;
        this.productBaseService = productBaseService;
    }

    @GetMapping("/checks")
    public String initializeCheck() {

        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeService.findEmployeeByUsername(authentication.getName());
        List<Check> activeChecks = checkService.findByEmployeeAndCheckStatus(employee, CheckStatus.OPEN);
        if (activeChecks.isEmpty()) {
            return "redirect:/checks/new_check";
        } else {
            return "redirect:/checks/" + activeChecks.iterator().next().getId();
        }
    }

    @GetMapping("/checks/{id}")
    public String getCheck(@PathVariable(name = "id") Long checkId, Model model) {

        Check check = checkService.getById(checkId);
        model.addAttribute("curCheck", check);

        List<CheckProductBase> checkProductBases = checkProductBaseService.getCheckProductBasesByCheck(check);
        model.addAttribute("checkProductBases", checkProductBases);

        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeService.findEmployeeByUsername(authentication.getName());
        List<Check> activeChecks = checkService.findByEmployeeAndCheckStatus(employee, CheckStatus.OPEN);
        model.addAttribute("activeChecks", activeChecks);

        model.addAttribute("activeNotEmptyChecksCount", checkService.getCurrentEmployeeNotEmptyActiveChecksCount());
        model.addAttribute("closable", !checkService.isCheckEmpty(checkId));
        model.addAttribute("declinable", !checkService.isCheckEmpty(checkId) ||
                checkService.getCurrentEmployeeActiveChecksCount() > 1);
        return "check";
    }

    @GetMapping(value = "/checks/{id}", params = "product")
    public String addProduct(@PathVariable(name = "id") Long checkId,
                             @RequestParam(name = "product") Long productId,
                             Model model) {

        if (!productBaseService.isProductBaseExists(productId)) {
            model.addAttribute("inputValue", productId);
            return getCheck(checkId, model);
        }

        checkProductBaseService.addOneCheckProductBase(checkId, productId);
        return "redirect:/checks/" + checkId;
    }

    @GetMapping(value = "/checks/{id}", params = {"product", "count"})
    public String editProductCount(@PathVariable(name = "id") Long checkId,
                                   @RequestParam(name = "product") Long productId,
                                   @RequestParam(name = "count") int count,
                                   Model model) {

        checkProductBaseService.setCheckProductBaseCount(checkId, productId, count);
        return "redirect:/checks/" + checkId;
    }

    @GetMapping("/checks/{id}/close")
    public String closeCheck(@PathVariable(name = "id") Long checkId) {

        if (!checkService.isCheckEmpty(checkId)) {
            checkService.closeCheck(checkId);
            return "redirect:/checks";
        }

        return "redirect:/checks/" + checkId;
    }

    @GetMapping("/checks/{id}/decline")
    public String declineCheck(@PathVariable(name = "id") Long checkId) {

        if (!checkService.isCheckEmpty(checkId) || checkService.getCurrentEmployeeActiveChecksCount() > 1) {
            checkService.declineCheck(checkId);
            return "redirect:/checks";
        }

        return "redirect:/checks/" + checkId;
    }

    @GetMapping("/checks/decline_all")
    public String declineAll() {

        checkService.declineAllCurrentEmployeeActiveChecks();

        return "redirect:/logout";
    }

    @GetMapping("/checks/new_check")
    public String createNewCheck() {

        Check newCheck = checkService.addEmptyAssignedCheck();
        return "redirect:/checks/" + newCheck.getId();
    }
}
