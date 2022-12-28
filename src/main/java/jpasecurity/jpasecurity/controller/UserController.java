package jpasecurity.jpasecurity.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jpasecurity.jpasecurity.expcetion.UsernameIsTakenException;
import jpasecurity.jpasecurity.model.dto.CreateUserDto;
import jpasecurity.jpasecurity.model.dto.UpdateUserPasswordDto;
import jpasecurity.jpasecurity.model.dto.UpdateUsernameDto;
import jpasecurity.jpasecurity.model.entity.User;
import jpasecurity.jpasecurity.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new user")
    public User createNewUser(@RequestBody @Valid CreateUserDto createUserDto) throws UsernameIsTakenException {
        return userService.saveNewUser(createUserDto);
    }

    @PutMapping("/{id}/password")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Update user's password")
    public void updateUserPassword(@RequestBody @Valid UpdateUserPasswordDto updateUserPasswordDto, @PathVariable("id") Long id) {
        userService.updateUserPassword(id, updateUserPasswordDto);
    }

    @PutMapping("/{id}/username")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Update user's username")
    public void updateUsername(@RequestBody @Valid UpdateUsernameDto updateUsernameDto, @PathVariable("id") Long id) {
        userService.updateUsername(updateUsernameDto, id);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Return user's data")
    @ResponseStatus(HttpStatus.OK)
    public User findUsersAllData(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }
}
