package com.flexsolution.chyzhov.kmb.user;

import com.flexsolution.chyzhov.kmb.course.Course;
import com.flexsolution.chyzhov.kmb.course.CourseService;
import com.flexsolution.chyzhov.kmb.exeption.RestBadRequestException;
import com.flexsolution.chyzhov.kmb.AbstractService;
import com.flexsolution.chyzhov.kmb.lesson.Lesson;
import com.flexsolution.chyzhov.kmb.lesson.LessonService;
import com.flexsolution.chyzhov.kmb.security.role.Role;
import com.flexsolution.chyzhov.kmb.security.role.RoleRepository;
import com.flexsolution.chyzhov.kmb.security.role.Roles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class UserService extends AbstractService<User, UserRepository> {

    private final CourseService courseService;
    private final PasswordEncoder passwordEncoder;
    private final LessonService lessonService;
    private final RoleRepository roleRepository;

    public UserService(UserRepository repository, CourseService courseService, PasswordEncoder passwordEncoder,
                       LessonService lessonService, RoleRepository roleRepository) {

        super(repository);
        this.courseService = courseService;
        this.passwordEncoder = passwordEncoder;
        this.lessonService = lessonService;
        this.roleRepository = roleRepository;
    }

    @Override
    public User create(@Valid User user) {
        if (!repository.existsByUsername(user.getUsername())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setConfirmPassword(user.getPassword());
            return repository.save(user);
        } else {
            throw new RestBadRequestException("Username is already exist");
        }
    }

    @Override
    public User findById(Long id) {
        if (repository.existsById(id)) {
            return repository.findById(id).get();
        } else {
            throw new RestBadRequestException(String.format("User with id %d does not exist", id));
        }
    }

    @Override
    public User update(User user, Long id) {
        if (repository.existsById(id)) {
            User u = repository.findById(id).get();
            user.setId(u.getId());
            repository.save(user);
            return u;
        } else {
            throw new RestBadRequestException(String.format("User with id %d does not exist", id));
        }
    }

    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        if (repository.existsById(id)) {
            super.deleteById(id);
        } else {
            throw new RestBadRequestException(String.format("User with id %d does not exist", id));
        }
    }

    public Page<User> findAll(Pageable pageable, Specification filter) {
        if (filter != null) {
            return repository.findAll(filter, pageable);
        }
        return repository.findAll(pageable);
    }

    public User findByUsername(String username) {
        if (repository.existsByUsername(username)) {
            return repository.findByUsername(username);
        }
        throw new RestBadRequestException(String.format("User with username %s not fount", username));
    }

    public User findByUsernameAndPassword(String username, String password) {
        if (repository.existsByUsername(username)) {
            User user = repository.findByUsername(username);
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            } else throw new RestBadRequestException(String.format("Wrong password by user %s ", username));
        } else throw new RestBadRequestException(String.format("User with username %s not found", username));
    }


    public List<Course> findAllCoursesByListener(Long listenerId) {
        User user = findById(listenerId);
        return courseService.findAllByListener(user);
    }


    public List<Lesson> findLessonsBySpeaker(Long speakerId) {
        User user = findById(speakerId);
        return lessonService.findAllBySpeaker(user);
    }

    public List<User> findAllSpeakers() {
        Role role = roleRepository.findByTitle(Roles.ROLE_SPEAKER);
        return repository.findAllByRoles(role);
    }

}
