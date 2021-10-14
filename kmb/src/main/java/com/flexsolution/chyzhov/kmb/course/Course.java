package com.flexsolution.chyzhov.kmb.course;

import com.flexsolution.chyzhov.kmb.AbstractEntity;
import com.flexsolution.chyzhov.kmb.lesson.Lesson;
import com.flexsolution.chyzhov.kmb.user.User;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Course extends AbstractEntity {

    @NotBlank
    private String title;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "course_lessons",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "lesson_id")})
    private Set<Lesson> lessons = new HashSet();

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH})
    @JoinTable(name = "course_listener",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "Listener_id")})
    private Set<User> listeners = new HashSet<>();
}
