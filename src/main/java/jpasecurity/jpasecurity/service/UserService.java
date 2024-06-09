package jpasecurity.jpasecurity.service;

import jakarta.annotation.PostConstruct;
import jpasecurity.jpasecurity.config.jwt.JwtTokenDetailsService;
import jpasecurity.jpasecurity.controller.user.CreateUserDto;
import jpasecurity.jpasecurity.controller.user.UpdateUserPasswordDto;
import jpasecurity.jpasecurity.controller.user.UserDto;
import jpasecurity.jpasecurity.controller.user.UserRegistrationDto;
import jpasecurity.jpasecurity.expcetion.EmailAddressIsTakenException;
import jpasecurity.jpasecurity.expcetion.UserNotFoundException;
import jpasecurity.jpasecurity.repository.UserRepository;
import jpasecurity.jpasecurity.service.model.ModelMapper;
import jpasecurity.jpasecurity.service.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenDetailsService jwtTokenDetailsService;
    private final ModelMapper mapper;

    public UserDto findUserById(Long id) {
        return mapper.toUserDto(findUserIfPresent(id));
    }

    @PostConstruct
    void init() {
        CreateUserDto createUserDto = new CreateUserDto();
        createUserDto.setUsername("admin");
        createUserDto.setPassword("admin");
        createUserDto.setEmail("smithy@admin.com");
        createUserDto.setRoles("ROLE_ADMIN,ROLE_USER");
        saveNewUser(createUserDto);
    }

    public List<UserDto> findAllUsers() {
        return mapper.toUserDto(userRepository.findAll());
    }

    public UserDto getUserByUniqueName(String name) {
        return mapper.toUserDto(userRepository.findByUsernameIgnoreCase(name)
                .orElseThrow(() -> new UserNotFoundException(name)));
    }

    public UserDto saveNewUser(CreateUserDto createUserDto) throws EmailAddressIsTakenException {
        checkEmailAddressAvailable(createUserDto.getEmail());
        User user = new User();
        user.setEmail(createUserDto.getEmail());
        user.setUsername(createUserDto.getUsername());
        user.setRoles(createUserDto.getRoles());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        userRepository.save(user);
        return mapper.toUserDto(user);
    }

    public UserDto registering(UserRegistrationDto userRegistrationDto) {
        checkEmailAddressAvailable(userRegistrationDto.getEmail());
        User userToRegistering = new User();
        userToRegistering.setUsername(userRegistrationDto.getUsername());
        userToRegistering.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        userToRegistering.setEmail(userRegistrationDto.getEmail());
        userToRegistering.setRoles("ROLE_USER");
        return mapper.toUserDto(userRepository.save(userToRegistering));
    }

    @Transactional
    public void updateUserPassword(UpdateUserPasswordDto updateUserPasswordDto) {
        User user = findUserIfPresent(getUserId());
        user.setPassword(passwordEncoder.encode(updateUserPasswordDto.getPassword()));
    }

    public void deleteUserById(Long id) {
        userRepository.delete(findUserIfPresent(id));
    }

    private void isUsernameAvailable(String username) throws EmailAddressIsTakenException {
        Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
        if (user.isPresent()) {
            throw new EmailAddressIsTakenException(username);
        }
    }

    private void checkEmailAddressAvailable(String email) {
        Optional<User> user = userRepository.findByEmailIgnoreCase(email);
        if (user.isPresent()) {
            throw new EmailAddressIsTakenException(email);
        }
    }

    public UserDto getUserDetails() {
        Long userId = jwtTokenDetailsService.getUserIdFromJWTToken();
        return mapper.toUserDto(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId)));
    }

    public long getUserId() {
        return jwtTokenDetailsService.getUserIdFromJWTToken();

    }

    public User findUserIfPresent(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public void requestNewPassword(String emailAddress) {
        User user = userRepository.findByEmailIgnoreCase(emailAddress).orElseThrow(() -> new UserNotFoundException(emailAddress));
        user.setPwResetCode(passwordEncoder.encode("newPassword"));
        LocalDateTime theTimeNow = LocalDateTime.now();
        user.setPwResetCodeValidUntil(theTimeNow.plusMinutes(15));
        emailService.sendMailTo(user.getEmail(),
                "click on the link to reset your password its going to be valid for 15 minutes"
                , "password reset request");
    }
}
