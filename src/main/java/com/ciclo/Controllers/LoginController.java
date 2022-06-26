package com.ciclo.Controllers;

import com.ciclo.Dto.LoginRequest;
import com.ciclo.Dto.LoginResponse;
import com.ciclo.Dto.UserRequest;
import com.ciclo.Dto.UserResponse;
import com.ciclo.Entities.User;
import com.ciclo.Services.UserService;
import com.ciclo.Util.UserDtoConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class LoginController {
    private final UserService userService;

    private final UserDtoConverter userDtoConverter;

    public LoginController(UserService userService, UserDtoConverter userDtoConverter) {
        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest signupRequestDto) {
        User user = userService.createUser(signupRequestDto);
        return new ResponseEntity<>(userDtoConverter.convertUserToDto(user), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest request) {
        LoginResponse loginResponse = userService.authenticateUser(request);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

}
