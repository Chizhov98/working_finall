package com.flexsolution.chyzhov.kmb.course;

import com.flexsolution.chyzhov.kmb.lesson.LessonResponseDto;
import com.flexsolution.chyzhov.kmb.user.dto.UserResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CourseResponseDto {

    private Long id;
    private List<UserResponseDto> listeners = new ArrayList<>();
    private String title;
    private double hours;
    private List<LessonResponseDto> lessons = new ArrayList<>();


}
