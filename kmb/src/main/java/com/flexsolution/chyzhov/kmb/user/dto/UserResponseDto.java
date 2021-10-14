package com.flexsolution.chyzhov.kmb.user.dto;

import com.flexsolution.chyzhov.kmb.file.FileMetaData;
import com.flexsolution.chyzhov.kmb.security.role.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.HashSet;
import java.util.List;

@NoArgsConstructor
@Data
public class UserResponseDto {

        private Long id;
        private String username;
        private String dateOfBirth;
        private List<FileMetaData> files;
        private Set<Roles> roles = new HashSet<>();
}
