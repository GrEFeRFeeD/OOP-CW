package oop.CourseWork.model.check_productBase;

import oop.CourseWork.model.check.Check;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckProductBaseRepository extends JpaRepository<CheckProductBase, CheckProductBaseKey> {
    List<CheckProductBase> findByCheck(Check check);
}
