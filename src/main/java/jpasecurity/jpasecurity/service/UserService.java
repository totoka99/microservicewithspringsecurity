package jpasecurity.jpasecurity.service;

import jakarta.annotation.PostConstruct;
import jpasecurity.jpasecurity.expcetion.UserNotFoundException;
import jpasecurity.jpasecurity.expcetion.UsernameIsTakenException;
import jpasecurity.jpasecurity.model.ModelMapper;
import jpasecurity.jpasecurity.model.dto.*;
import jpasecurity.jpasecurity.model.entity.User;
import jpasecurity.jpasecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    public UserDto findUserById(Long id) {
        return mapper.toUserDto(findUserIfPresent(id));
    }
    @PostConstruct
    void init(){
        saveNewUser(new CreateUserDto("admin","admin","ROLE_ADMIN"));
    }

    public UserDto saveNewUser(CreateUserDto createUserDto) throws UsernameIsTakenException {
        isUsernameAvailable(createUserDto.getUsername());
        User user = new User();
        user.setUsername(createUserDto.getUsername());
        user.setRoles(createUserDto.getRoles());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        userRepository.save(user);
        return mapper.toUserDto(user);
    }

    public UserDto registering(UserRegistrationDto userRegistrationDto) {
        isUsernameAvailable(userRegistrationDto.getUsername());
        User userToRegistering = new User();
        userToRegistering.setUsername(userRegistrationDto.getUsername());
        userToRegistering.setPassword(userRegistrationDto.getPassword());
        userToRegistering.setRoles("ROLE_USER");
        return mapper.toUserDto(userRepository.save(userToRegistering));
    }

    @Transactional
    public void updateUserPassword(Long id, UpdateUserPasswordDto updateUserPasswordDto) {
        User user = findUserIfPresent(id);
        user.setPassword(passwordEncoder.encode(updateUserPasswordDto.getPassword()));
    }

    @Transactional
    public void updateUsername(Long id, UpdateUsernameDto updateUsernameDto) {
        User user = findUserIfPresent(id);
        isUsernameAvailable(updateUsernameDto.getUsername());
        user.setUsername(updateUsernameDto.getUsername());
    }

    public void deleteUserById(Long id) {
        userRepository.delete(findUserIfPresent(id));
    }

    private void isUsernameAvailable(String username) throws UsernameIsTakenException {
        Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
        if (user.isPresent()) {
            throw new UsernameIsTakenException(username);
        }
    }

    private User findUserIfPresent(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
