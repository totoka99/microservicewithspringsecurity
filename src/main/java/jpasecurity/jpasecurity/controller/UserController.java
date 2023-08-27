package jpasecurity.jpasecurity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jpasecurity.jpasecurity.expcetion.UsernameIsTakenException;
import jpasecurity.jpasecurity.model.dto.user.CreateUserDto;
import jpasecurity.jpasecurity.model.dto.user.update.UpdateUserPasswordDto;
import jpasecurity.jpasecurity.model.dto.user.update.UpdateUsernameDto;
import jpasecurity.jpasecurity.model.dto.user.UserDto;
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
    public List<UserDto> getAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Return user's data")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }

    @GetMapping("/name")
    public UserDto getUserByUniqueName(@Param("username") String username){
        return userService.getUserByUniqueName(username);
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
