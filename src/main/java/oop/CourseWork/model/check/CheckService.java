package oop.CourseWork.model.check;

import oop.CourseWork.model.check_productBase.CheckProductBase;
import oop.CourseWork.model.check_productBase.CheckProductBaseRepository;
import oop.CourseWork.model.employee.Employee;
import oop.CourseWork.model.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class CheckService {

    private CheckRepository checkRepository;
    private EmployeeRepository employeeRepository;
    private CheckProductBaseRepository checkProductBaseRepository;

    @Autowired
    public CheckService(CheckRepository checkRepository, EmployeeRepository employeeRepository, CheckProductBaseRepository checkProductBaseRepository) {
        this.checkRepository = checkRepository;
        this.employeeRepository = employeeRepository;
        this.checkProductBaseRepository = checkProductBaseRepository;
    }

    public Check addEmptyAssignedCheck() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeRepository.findEmployeeByUsername(authentication.getName());
        Check check = new Check(null, new Date(System.currentTimeMillis()), CheckStatus.OPEN, employee, new HashSet<>());
        return checkRepository.save(check);
    }

    public Check getById(Long id) {
        return checkRepository.getById(id);
    }

    public List<Check> findByEmployeeAndCheckStatus(Employee employee, CheckStatus checkStatus) {
        return checkRepository.findByEmployeeAndStatus(employee, checkStatus);
    }

    public void closeCheck(Long checkId) {
        Check check = checkRepository.getById(checkId);
        check.setStatus(CheckStatus.CLOSED);
        //TODO: Logging + ProductBase manipulating
        checkRepository.save(check);
    }

    public void declineCheck(Long checkId) {
        Check check = checkRepository.getById(checkId);
        for (CheckProductBase cpb : checkProductBaseRepository.findByCheck(check)) {
            checkProductBaseRepository.delete(cpb);
        }
        checkRepository.delete(check);
    }

    public boolean isCheckEmpty(Long checkId) {
        Check check = checkRepository.getById(checkId);
        return checkProductBaseRepository.findByCheck(check).isEmpty();
    }

    public int getCurrentEmployeeActiveChecksCount() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeRepository.findEmployeeByUsername(authentication.getName());
        List<Check> activeChecks = findByEmployeeAndCheckStatus(employee, CheckStatus.OPEN);
        return activeChecks.size();
    }

    public void declineAllCurrentEmployeeActiveChecks() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeRepository.findEmployeeByUsername(authentication.getName());
        List<Check> activeChecks = findByEmployeeAndCheckStatus(employee, CheckStatus.OPEN);
        for (Check check : activeChecks) {
            declineCheck(check.getId());
        }
    }

    public int getCurrentEmployeeNotEmptyActiveChecksCount() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        Employee employee = employeeRepository.findEmployeeByUsername(authentication.getName());
        List<Check> activeChecks = findByEmployeeAndCheckStatus(employee, CheckStatus.OPEN);
        return (int) activeChecks.stream().filter(o -> !isCheckEmpty(o.getId())).count();
    }
}