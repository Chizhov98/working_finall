package com.flexsolution.chyzhov.kmb.course;

import com.flexsolution.chyzhov.kmb.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByListeners(User user);
}
