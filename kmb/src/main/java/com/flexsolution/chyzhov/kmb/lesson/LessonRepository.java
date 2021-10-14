package com.flexsolution.chyzhov.kmb.lesson;

import com.flexsolution.chyzhov.kmb.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findAllBySpeaker(User user);
}
