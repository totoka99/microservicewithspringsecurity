package jpasecurity.jpasecurity.controller;

import jpasecurity.jpasecurity.model.dto.CreateUserDto;
import jpasecurity.jpasecurity.model.dto.UserRegistrationDto;
import jpasecurity.jpasecurity.model.entity.User;
import jpasecurity.jpasecurity.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/registration")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User registeringNewUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        return this.userService.registering(userRegistrationDto);
    }
}
