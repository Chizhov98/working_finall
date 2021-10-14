package com.flexsolution.chyzhov.kmb.user;

import com.flexsolution.chyzhov.kmb.user.dto.PageResponseDto;
import com.flexsolution.chyzhov.kmb.user.dto.ResponseBodyDto;
import com.flexsolution.chyzhov.kmb.user.dto.RequestUserDto;
import com.flexsolution.chyzhov.kmb.user.dto.UserResponseDto;
import com.flexsolution.chyzhov.kmb.user.filter.UserSpecificationBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @Autowired
    public UserController(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/speakers")
    public ResponseEntity findAllSpeakers() {
        List<User> speakers = service.findAllSpeakers();
        List<UserResponseDto> response = speakers.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @Operation(description = "Find all user and return in Page format. If filters are specified, add them to quary")
    @GetMapping("/page")
    public ResponseEntity getAllInPage(@Parameter(description = "Add filter to searching by part of username ")
                                       @RequestParam(name = "userNameLike", required = false) String nameLike,
                                       @Parameter(description = "Lower bound for range searches by id. Must using with lastId paramm")
                                       @RequestParam(name = "firstId", required = false) Long firstId,
                                       @Parameter(description = "Upper bound for range searches by id. Must using with firstId paramm")
                                       @RequestParam(name = "lastId", required = false) Long lastId,
                                       @Parameter(description = "Add filter for searches by ageOfBirth, greater then")
                                       @RequestParam(name = "afterDate", required = false) String afterDate,
                                       @Parameter(description = "Lower bound for range searches by ageOfBirth. Must using with lastDate paramm")
                                       @RequestParam(name = "firstDate", required = false) String firstDate,
                                       @Parameter(description = "Upper bound for range searches by ageOfBirth. Must using with firstDate paramm")
                                       @RequestParam(name = "lastDate", required = false) String lastDate,
                                       Pageable page) {

        Specification<User> filter = UserSpecificationBuilder.empty()
                .setAfterDate(afterDate)
                .setEndDate(lastDate)
                .setStartDate(firstDate)
                .setEndId(lastId)
                .setStartId(firstId)
                .setNameLike(nameLike)
                .build();

        Page<User> users = service.findAll(page, filter);

        List<UserResponseDto> dtoList = users.stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());

        PageResponseDto pageDto = new PageResponseDto(page.getPageNumber(),
                page.getPageSize(),
                users.getNumberOfElements(),
                users.getTotalPages(),
                dtoList);

        return ResponseEntity.ok(pageDto);
    }

    @Operation(description = "Find user by id in pathParamm")
    @GetMapping("/{id}")
    public ResponseBodyDto getById(@PathVariable(name = "id") Long id) {
        UserResponseDto userDto = mapper.toResponseDto(service.findById(id));
        return new ResponseBodyDto(userDto, HttpStatus.OK.value());
    }

    @Operation(description = "Update user by id in path")
    @PutMapping("/{id}")
    public ResponseBodyDto updateById(@PathVariable(name = "id") Long id,
                                      @Parameter(description = "New user data")
                                      @RequestBody @Valid RequestUserDto user,
                                      BindingResult result) {

        if (result.hasErrors()) {
            return new ResponseBodyDto(mapper.returnMapErrors(result),
                    HttpStatus.BAD_REQUEST.value());
        }
        User usr = mapper.requestDtoToEntity(user);
        UserResponseDto userDto = mapper.toResponseDto(service.update(usr, id));
        return new ResponseBodyDto(userDto,
                HttpStatus.OK.value());
    }




}
