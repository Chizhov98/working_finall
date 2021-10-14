package com.flexsolution.chyzhov.kmb.security.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByTitle(Roles title);

    boolean existsByTitle(Roles title);
}
