package com.flexsolution.chyzhov.kmb.user;

import com.flexsolution.chyzhov.kmb.AbstractEntity;
import com.flexsolution.chyzhov.kmb.file.FileMetaData;
import com.flexsolution.chyzhov.kmb.security.role.Role;
import com.flexsolution.chyzhov.kmb.user.listener.UserAgeValidationListener;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "users")
@Data

@EntityListeners({
        UserAgeValidationListener.class
})
public class User extends AbstractEntity {

    @Column(unique = true)
    private String username;

    private String password;
    @Transient
    private String confirmPassword;

    private LocalDate dateOfBirth;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_file",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "file_id")}
    )
    private List<FileMetaData> files = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();


}
