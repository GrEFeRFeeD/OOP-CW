package oop.CourseWork.model.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CheckService {
    @Autowired
    private CheckRepository checkRepository;

    public void addCheck(Check check) {
        checkRepository.save(check);
    }

    public List<Check> getAllChecks() {
        return checkRepository.findAll();
    }
}
