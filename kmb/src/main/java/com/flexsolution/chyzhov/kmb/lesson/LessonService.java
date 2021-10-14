package com.flexsolution.chyzhov.kmb.lesson;

import com.flexsolution.chyzhov.kmb.AbstractService;
import com.flexsolution.chyzhov.kmb.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService extends AbstractService<Lesson, LessonRepository> {

    public LessonService(LessonRepository repository) {
        super(repository);
    }

    public List<Lesson> findAllBySpeaker(User speaker) {
        return repository.findAllBySpeaker(speaker);
    }
}
