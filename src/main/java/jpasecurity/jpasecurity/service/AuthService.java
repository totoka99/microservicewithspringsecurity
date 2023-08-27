package jpasecurity.jpasecurity.service;

import jakarta.servlet.http.HttpSession;
import jpasecurity.jpasecurity.config.SecurityUser;
import jpasecurity.jpasecurity.model.ModelMapper;
import jpasecurity.jpasecurity.model.dto.user.LoginUserDto;
import jpasecurity.jpasecurity.model.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import nonapi.io.github.classgraph.json.JSONUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JpaUserDetailsManager jpaUserDetailsManager;
    private final ModelMapper mapper;

    public Authentication authenticateLoginRequest(LoginUserDto loginUserDto, HttpSession httpSession) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
    }
}
