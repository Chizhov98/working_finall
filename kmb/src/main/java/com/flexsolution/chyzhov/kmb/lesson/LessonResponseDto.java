package com.flexsolution.chyzhov.kmb.lesson;

import com.flexsolution.chyzhov.kmb.user.dto.UserResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LessonResponseDto {

    private Long id;
    private double hours;
    private LocalDate startDate;
    private UserResponseDto speaker;
}
