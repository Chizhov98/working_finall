package com.flexsolution.chyzhov.kmb.user.dto;

import com.flexsolution.chyzhov.kmb.file.FileMetaData;
import com.flexsolution.chyzhov.kmb.security.role.Roles;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class RequestUserDto {

    @Size(min = 5, message = "Username must be more then 5 characters")
    private String username;

    @Size(min = 6, max = 20)
    @Pattern(regexp = ".*[A-Z].*", message = "Password must exist at least one capital letter")
    @Pattern(regexp = ".*[a-z].*", message = "Password must exist at least one digit letter")
    @Pattern(regexp = ".*\\d.*", message = " Password must exist at least one number")
    private String password;

    @Size(min = 6, max = 20)
    @Transient
    private String confirmPassword;

    private String dateOfBirth;
    private List<Roles> roles = new ArrayList<>();
}
