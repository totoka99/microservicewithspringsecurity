package jpasecurity.jpasecurity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jpasecurity.jpasecurity.model.dto.CreateUserDto;
import jpasecurity.jpasecurity.model.dto.UserDto;
import jpasecurity.jpasecurity.model.dto.UserRegistrationDto;
import jpasecurity.jpasecurity.model.entity.User;
import jpasecurity.jpasecurity.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create a user with USER authority")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201")
    public UserDto registeringNewUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        return this.userService.registering(userRegistrationDto);
    }
}
