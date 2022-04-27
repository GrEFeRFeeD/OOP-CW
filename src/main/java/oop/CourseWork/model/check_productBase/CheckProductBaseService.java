package oop.CourseWork.model.check_productBase;

import oop.CourseWork.model.order.Order;
import oop.CourseWork.model.productBase.ProductBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckProductBaseService {

    @Autowired
    CheckProductBaseRepository checkProductBaseRepository;

    public void addCheckProductBase(CheckProductBase checkProductBase) {
        checkProductBaseRepository.save(checkProductBase);
    }

    public List<CheckProductBase> getAllChecksProductBases() {
        return checkProductBaseRepository.findAll();
    }
}
