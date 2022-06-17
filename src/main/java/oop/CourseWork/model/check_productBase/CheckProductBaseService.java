package oop.CourseWork.model.check_productBase;

import oop.CourseWork.model.check.Check;
import oop.CourseWork.model.check.CheckRepository;
import oop.CourseWork.model.productBase.ProductBase;
import oop.CourseWork.model.productBase.ProductBaseRepository;
import oop.CourseWork.model.productBase.ProductBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CheckProductBaseService {

    private final CheckProductBaseRepository checkProductBaseRepository;
    private final CheckRepository checkRepository;
    private final ProductBaseRepository productBaseRepository;
    private final ProductBaseService productBaseService;

    @Autowired
    public CheckProductBaseService(CheckProductBaseRepository checkProductBaseRepository, CheckRepository checkRepository, ProductBaseRepository productBaseRepository, ProductBaseService productBaseService) {
        this.checkProductBaseRepository = checkProductBaseRepository;
        this.checkRepository = checkRepository;
        this.productBaseRepository = productBaseRepository;
        this.productBaseService = productBaseService;
    }

    public void addCheckProductBase(CheckProductBase checkProductBase) {
        checkProductBaseRepository.save(checkProductBase);
    }

    public void deleteCheckProductBase(CheckProductBase checkProductBase) {
        checkProductBaseRepository.delete(checkProductBase);
    }

    public List<CheckProductBase> getCheckProductBasesByCheck(Check check) {
        return checkProductBaseRepository.findByCheck(check);
    }


    public void addOneCheckProductBase(Long checkId, Long productBaseId) {
        Check check = checkRepository.getById(checkId);
        ProductBase productBase = productBaseRepository.getById(productBaseId);

        Optional<CheckProductBase> checkProductBase = checkProductBaseRepository
                .findById(new CheckProductBaseKey(checkId, productBaseId));

        if (checkProductBase.isPresent()) {
            setCheckProductBaseCount(checkId, productBaseId, checkProductBase.get().getCount() + 1);
        } else {
            CheckProductBase newCheckProductBase = new CheckProductBase(new CheckProductBaseKey(), check, productBase,
                    1, productBase.getSellingPrice());
            addCheckProductBase(newCheckProductBase);
        }
    }

    public void setCheckProductBaseCount(Long checkId, Long productBaseId, int count) {
        CheckProductBase checkProductBase = checkProductBaseRepository.getById(new CheckProductBaseKey(checkId, productBaseId));

        if (count > 0) {
            checkProductBase.setCount(Math.min(productBaseService.getAvailableProductCount(productBaseId), count));
            addCheckProductBase(checkProductBase);
        }
        if (count == 0) {
            deleteCheckProductBase(checkProductBase);
        }
    }
}
