package jpasecurity.jpasecurity.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jpasecurity.jpasecurity.model.dto.user.LoginUserDto;
import jpasecurity.jpasecurity.model.dto.user.UserDto;
import jpasecurity.jpasecurity.model.dto.user.UserRegistrationDto;
import jpasecurity.jpasecurity.service.AuthService;
import jpasecurity.jpasecurity.service.TokenService;
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
    public String login(@RequestBody LoginUserDto loginUserDto, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return tokenService.generateToken(authService.authenticateLoginRequest(loginUserDto, session));
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registeringNewUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        return this.userService.registering(userRegistrationDto);
    }
}
