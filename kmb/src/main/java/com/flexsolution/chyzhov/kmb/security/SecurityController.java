package com.flexsolution.chyzhov.kmb.security;

import com.flexsolution.chyzhov.kmb.exeption.RestBadRequestException;
import com.flexsolution.chyzhov.kmb.security.role.Roles;
import com.flexsolution.chyzhov.kmb.user.User;
import com.flexsolution.chyzhov.kmb.user.dto.RequestUserDto;
import com.flexsolution.chyzhov.kmb.user.UserService;
import com.flexsolution.chyzhov.kmb.user.dto.ResponseBodyDto;
import com.flexsolution.chyzhov.kmb.user.UserMapper;
import com.flexsolution.chyzhov.kmb.user.dto.UserResponseDto;
import com.flexsolution.chyzhov.kmb.user.listener.UserPasswordMather;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SecurityController {

    private final UserService service;
    private final UserMapper mapper;
    private final JwtProvider jwtProvider;

    public SecurityController(UserService service, UserMapper mapper, JwtProvider jwtProvider) {
        this.service = service;
        this.mapper = mapper;
        this.jwtProvider = jwtProvider;
    }

    @Operation(description = "Create new User")
    @PostMapping("/register")
    public ResponseEntity create(@RequestBody @Valid RequestUserDto user,
                                 BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseBodyDto(mapper.returnMapErrors(result),
                            HttpStatus.BAD_REQUEST.value()));
        }
        UserPasswordMather.passMatcher(user);
        List<Roles> role = new ArrayList<>();
        role.add(Roles.ROLE_USER);
        user.setRoles(role);
        UserResponseDto userDto = mapper.toResponseDto(service.create(mapper.requestDtoToEntity(user)));
        return ResponseEntity.ok(new ResponseBodyDto(userDto, HttpStatus.OK.value()));
    }

    @GetMapping("/login")
    public ResponseEntity authentication(@RequestBody RequestUserDto dto) {
        if (dto.getPassword() != null && dto.getUsername() != null) {
            dto.setPassword(dto.getPassword());
            User user = service.findByUsernameAndPassword(dto.getUsername(), dto.getPassword());
            String token = jwtProvider.generateToken(user.getUsername());
            return ResponseEntity.ok(token);
        } else throw new RestBadRequestException("Username and password can not be null");
    }
}
