package com.flexsolution.chyzhov.kmb.security.role;

import com.flexsolution.chyzhov.kmb.AbstractEntity;
import com.flexsolution.chyzhov.kmb.security.role.Roles;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private Roles title;

    public Role(Roles role) {
        this.title = role;
    }
}
