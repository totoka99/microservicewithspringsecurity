package jpasecurity.jpasecurity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jpasecurity.jpasecurity.expcetion.UsernameIsTakenException;
import jpasecurity.jpasecurity.model.dto.CreateUserDto;
import jpasecurity.jpasecurity.model.dto.UpdateUserPasswordDto;
import jpasecurity.jpasecurity.model.dto.UpdateUsernameDto;
import jpasecurity.jpasecurity.model.dto.UserDto;
import jpasecurity.jpasecurity.model.entity.User;
import jpasecurity.jpasecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    @Operation(summary = "Return user's data")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200")
    public UserDto findUsersAllData(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new user")
    @ApiResponse(responseCode = "201")
    public UserDto createNewUser(@RequestBody @Valid CreateUserDto createUserDto) throws UsernameIsTakenException {
        return userService.saveNewUser(createUserDto);
    }

    @PatchMapping("/{userId}/username")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Update user's username")
    @ApiResponse(responseCode = "202")
    public void updateUsername(@RequestBody @Valid UpdateUsernameDto updateUsernameDto, @PathVariable Long userId) {
        userService.updateUsername(userId, updateUsernameDto);
    }

    @PatchMapping("/{userId}/password")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Update user's password")
    @ApiResponse(responseCode = "202")
    public void updateUserPassword(@RequestBody @Valid UpdateUserPasswordDto updateUserPasswordDto, @PathVariable Long userId) {
        userService.updateUserPassword(userId, updateUserPasswordDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete user by id")
    @ApiResponse(responseCode = "204")
    public void deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }
}
