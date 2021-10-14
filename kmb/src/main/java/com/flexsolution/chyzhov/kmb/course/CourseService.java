package com.flexsolution.chyzhov.kmb.course;

import com.flexsolution.chyzhov.kmb.AbstractService;
import com.flexsolution.chyzhov.kmb.exeption.RestBadRequestException;
import com.flexsolution.chyzhov.kmb.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService extends AbstractService<Course, CourseRepository> {

    public CourseService(CourseRepository repository) {
        super(repository);
    }

    public List<Course> findAllByListener(User user) {
        return repository.findAllByListeners(user);
    }

    public Course findById(Long id) {
        if (repository.existsById(id)) {
            return repository.findById(id).get();
        } else throw new RestBadRequestException(String.format("Course with id %d does not exist", id));
    }
}
