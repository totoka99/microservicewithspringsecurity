package jpasecurity.jpasecurity.controller;

import jpasecurity.jpasecurity.config.jwt.TokenService;
import jpasecurity.jpasecurity.controller.user.LoginUserDto;
import jpasecurity.jpasecurity.controller.user.UserDto;
import jpasecurity.jpasecurity.controller.user.UserRegistrationDto;
import jpasecurity.jpasecurity.service.AuthService;
import jpasecurity.jpasecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String login(@RequestBody LoginUserDto loginUserDto) {
        return tokenService.generateToken(authService.authenticateLoginRequest(loginUserDto));
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registeringNewUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        return this.userService.registering(userRegistrationDto);
    }

    @PostMapping("/password-request")
    @ResponseStatus(HttpStatus.CREATED)
    public void requestPassword(@RequestBody String emailAddress) {
        userService.requestNewPassword(emailAddress);
    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void resetPassword(@RequestBody String pwResetCode) {

    }
}
