package com.flexsolution.chyzhov.kmb.user;

import com.flexsolution.chyzhov.kmb.security.role.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    boolean existsByUsername(String username);

    Page<User> findAll(Pageable pageable);

    User findByUsername(String username);

    List<User> findAllByRoles(Role role);
}
