package com.flexsolution.chyzhov.kmb.lesson;

import com.flexsolution.chyzhov.kmb.AbstractEntity;
import com.flexsolution.chyzhov.kmb.user.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class Lesson extends AbstractEntity {

    private LocalDate startDate;
    private double hours;
    private String title;

    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    @JoinTable(name = "leson_speacer",
            joinColumns = {@JoinColumn(name = "lesson_id")},
            inverseJoinColumns = {@JoinColumn(name = "speaker_id")})
    private User speaker;
}
