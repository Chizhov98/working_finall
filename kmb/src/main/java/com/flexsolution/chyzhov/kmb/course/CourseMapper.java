package com.flexsolution.chyzhov.kmb.course;

import com.flexsolution.chyzhov.kmb.lesson.LessonMapper;
import com.flexsolution.chyzhov.kmb.lesson.LessonResponseDto;
import com.flexsolution.chyzhov.kmb.user.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    private final UserMapper userMapper;
    private final LessonMapper lessonMapper;

    public CourseMapper(UserMapper userMapper, LessonMapper lessonMapper) {
        this.userMapper = userMapper;
        this.lessonMapper = lessonMapper;
    }

    public CourseResponseDto toResponseDto(Course course) {
        CourseResponseDto dto = new CourseResponseDto();
        BeanUtils.copyProperties(course, dto);
        dto.setLessons(
                course.getLessons().stream()
                        .map(lessonMapper::toResponseDto)
                        .collect(Collectors.toList())
        );
        dto.setHours(calculateCourseHours(dto.getLessons()));
        dto.setListeners(
                course.getListeners().stream()
                        .map(userMapper::toResponseDto)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    private double calculateCourseHours(List<LessonResponseDto> lessons) {
        double result = 0.0;
        if (!lessons.isEmpty()) {
            for (LessonResponseDto obj : lessons) {
                result += obj.getHours();
            }
        }
        return result;
    }
}
