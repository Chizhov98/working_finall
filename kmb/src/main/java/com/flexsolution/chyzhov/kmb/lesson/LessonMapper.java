package com.flexsolution.chyzhov.kmb.lesson;

import com.flexsolution.chyzhov.kmb.AbstractMapper;
import com.flexsolution.chyzhov.kmb.course.RequestLessonDto;
import com.flexsolution.chyzhov.kmb.user.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper extends AbstractMapper {
    private final UserMapper mapper;

    public LessonMapper(UserMapper mapper) {
        this.mapper = mapper;
    }

    public LessonResponseDto toResponseDto(Lesson lesson){
        LessonResponseDto dto = new LessonResponseDto();
        BeanUtils.copyProperties(lesson,dto);
        dto.setSpeaker(mapper.toResponseDto(lesson.getSpeaker()));
        return dto;
    }

    public Lesson tiEntity(RequestLessonDto dto){
        Lesson lesson = new Lesson();
        BeanUtils.copyProperties(dto,lesson);
        lesson.setStartDate(toLocalDate(dto.getStartDate()));
        return lesson;
    }
}
