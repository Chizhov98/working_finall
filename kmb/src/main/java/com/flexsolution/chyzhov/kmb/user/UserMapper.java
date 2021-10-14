package com.flexsolution.chyzhov.kmb.user;

import com.flexsolution.chyzhov.kmb.AbstractMapper;
import com.flexsolution.chyzhov.kmb.security.role.Role;
import com.flexsolution.chyzhov.kmb.security.role.RoleService;
import com.flexsolution.chyzhov.kmb.security.role.Roles;
import com.flexsolution.chyzhov.kmb.user.dto.RequestUserDto;
import com.flexsolution.chyzhov.kmb.user.dto.UserResponseDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component("userMapper")
public class UserMapper extends AbstractMapper {

    private final RoleService service;

    public UserMapper(RoleService service) {
        this.service = service;
    }

    public User requestDtoToEntity(RequestUserDto dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setDateOfBirth(toLocalDate(dto.getDateOfBirth()));
        for (Roles role : dto.getRoles()) {
            user.getRoles().add(service.findByTitle(role));
        }
        return user;
    }

    public RequestUserDto toRequestDto(User user) {
        RequestUserDto userDto = new RequestUserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setDateOfBirth(fromLocalDate(user.getDateOfBirth()));
        for (Role role : user.getRoles()) {
            userDto.getRoles().add(role.getTitle());
        }
        return userDto;
    }

    public UserResponseDto toResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        BeanUtils.copyProperties(user, dto);
        dto.setDateOfBirth(fromLocalDate(user.getDateOfBirth()));
        for (Role role : user.getRoles()) {
            dto.getRoles().add(role.getTitle());
        }
        return dto;
    }

}
