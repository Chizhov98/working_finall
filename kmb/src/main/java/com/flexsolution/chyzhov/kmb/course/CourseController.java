package com.flexsolution.chyzhov.kmb.course;

import com.flexsolution.chyzhov.kmb.lesson.Lesson;
import com.flexsolution.chyzhov.kmb.lesson.LessonMapper;
import com.flexsolution.chyzhov.kmb.lesson.LessonService;
import com.flexsolution.chyzhov.kmb.security.CustomUserDetailsService;
import com.flexsolution.chyzhov.kmb.security.JwtFilter;
import com.flexsolution.chyzhov.kmb.security.JwtProvider;
import com.flexsolution.chyzhov.kmb.security.role.RoleRepository;
import com.flexsolution.chyzhov.kmb.security.role.RoleService;
import com.flexsolution.chyzhov.kmb.security.role.Roles;
import com.flexsolution.chyzhov.kmb.user.User;
import com.flexsolution.chyzhov.kmb.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final JwtProvider jwtProvider;
    private final CourseMapper courseMapper;
    private final LessonMapper lessonMapper;
    private final LessonService lessonService;
    private final UserService userService;
    private final CourseService courseService;
    private final RoleService roleService;

    public CourseController(JwtProvider jwtProvider,
                            CustomUserDetailsService customUserDetailsService,
                            CourseMapper courseMapper,
                            LessonMapper lessonMapper,
                            LessonService lessonService,
                            CourseService courseService,
                            UserService userService, RoleRepository roleRepository, RoleService roleService) {

        this.jwtProvider = jwtProvider;
        this.courseMapper = courseMapper;
        this.lessonMapper = lessonMapper;
        this.lessonService = lessonService;
        this.userService = userService;
        this.courseService = courseService;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity getAllCourses() {
        return ResponseEntity.ok(courseService.findAll().stream()
                .map(courseMapper::toResponseDto)
                .collect(Collectors.toList()
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity getCourseInfo(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(courseMapper.toResponseDto(
                courseService.findById(id)
        ));
    }

    @GetMapping("/{id}/lessons")
    public ResponseEntity getCourseLessons(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(courseService.findById(id)
                .getLessons()
                .stream()
                .map(lessonMapper::toResponseDto)
                .collect(Collectors.toList()
                ));
    }

    @GetMapping("/lessons/{id}")
    public ResponseEntity getLessonInfo(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(lessonMapper.toResponseDto(
                lessonService.findById(id)
        ));
    }

    @PostMapping("/{id}/lessons")
    public ResponseEntity createNewLesson(HttpServletRequest servletRequest,
                                          @RequestBody RequestLessonDto dto,
                                          @PathVariable(name = "id") Long id) {
        Lesson lesson = lessonMapper.tiEntity(dto);
        String token = JwtFilter.getTokenFromRequest(servletRequest);
        String userLogin = jwtProvider.getLoginFromToken(token);
        User user = userService.findByUsername(userLogin);
        user.getRoles().add(roleService.findByTitle(Roles.ROLE_SPEAKER));
        lesson.setSpeaker(user);
        Course course = courseService.findById(id);
        course.getLessons().add(lesson);
        course = courseService.update(course, id);

        return ResponseEntity.status(HttpStatus.CREATED).body(courseMapper.toResponseDto(course));
    }

    @PostMapping
    public ResponseEntity createCourse(@RequestBody Course course) {
        course = courseService.create(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    @Operation(description = "Subscribe user by course")
    @PutMapping("/{id}/subscribe")
    public ResponseEntity subscribeByCourse(HttpServletRequest servletRequest,
                                            @PathVariable(name = "id") Long id) {

        Course course = courseService.findById(id);
        String token = JwtFilter.getTokenFromRequest(servletRequest);
        String userLogin = jwtProvider.getLoginFromToken(token);
        User user = userService.findByUsername(userLogin);
        user.getRoles().add(roleService.findByTitle(Roles.ROLE_LISTENER));
        course.getListeners().add(user);
        course = courseService.update(course, id);
        return ResponseEntity.ok(courseMapper.toResponseDto(course));
    }

}
