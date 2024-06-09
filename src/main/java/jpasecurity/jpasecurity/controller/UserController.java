package jpasecurity.jpasecurity.controller;

import jakarta.validation.Valid;
import jpasecurity.jpasecurity.controller.user.CreateUserDto;
import jpasecurity.jpasecurity.controller.user.UpdateUserPasswordDto;
import jpasecurity.jpasecurity.controller.user.UserDto;
import jpasecurity.jpasecurity.expcetion.EmailAddressIsTakenException;
import jpasecurity.jpasecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers();
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/user-details")
    public UserDto getUserDetails() {
        return userService.getUserDetails();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }

    @GetMapping("/name")
    public UserDto getUserByUniqueName(@Param("username") String username) {
        return userService.getUserByUniqueName(username);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createNewUser(@RequestBody @Valid CreateUserDto createUserDto) throws EmailAddressIsTakenException {
        return userService.saveNewUser(createUserDto);
    }

    @PatchMapping("/user/password")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateUserPassword(@RequestBody @Valid UpdateUserPasswordDto updateUserPasswordDto) {
        userService.updateUserPassword(updateUserPasswordDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }
}
