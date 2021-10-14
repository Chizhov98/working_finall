package com.flexsolution.chyzhov.kmb.security.role;

import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

   public Role findByTitle(Roles title){
        if(repository.existsByTitle(title)){
            return repository.findByTitle(title);
        }else{
            Role role = new Role(title);
            return role;
        }
    }
}
