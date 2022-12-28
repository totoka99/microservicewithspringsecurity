package jpasecurity.jpasecurity.service;

import jpasecurity.jpasecurity.expcetion.UserNotFoundException;
import jpasecurity.jpasecurity.expcetion.UsernameIsTakenException;
import jpasecurity.jpasecurity.model.dto.CreateUserDto;
import jpasecurity.jpasecurity.model.dto.UpdateUserPasswordDto;
import jpasecurity.jpasecurity.model.dto.UpdateUsernameDto;
import jpasecurity.jpasecurity.model.entity.User;
import jpasecurity.jpasecurity.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User saveNewUser(CreateUserDto createUserDto) throws UsernameIsTakenException {
        isUsernameAvailable(createUserDto.getName());
        User user = new User();
        user.setUsername(createUserDto.getName());
        user.setRoles(createUserDto.getRoles());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        userRepository.save(user);
        return user;
    }

    private void isUsernameAvailable(String username) throws UsernameIsTakenException {
        Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
        if (user.isPresent())
            throw new UsernameIsTakenException(username);
    }

    @Transactional
    public void updateUserPassword(Long id, UpdateUserPasswordDto updateUserPasswordDto) {
        User user = findUserIfPresent(id);
        user.setPassword(passwordEncoder.encode(updateUserPasswordDto.getPassword()));
    }

    @Transactional
    public void updateUsername(UpdateUsernameDto updateUsernameDto, Long id) {
        User user = findUserIfPresent(id);
        isUsernameAvailable(updateUsernameDto.getUsername());
        user.setUsername(updateUsernameDto.getUsername());
    }

    private User findUserIfPresent(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User findUserById(Long id) {
        return findUserIfPresent(id);
    }

    public void deleteUserById(Long id) {
        userRepository.delete(findUserIfPresent(id));
    }

    public User registering(CreateUserDto createUserDto) {
        isUsernameAvailable(createUserDto.getName());
        User userToRegistering = new User();
        userToRegistering.setUsername(createUserDto.getName());
        userToRegistering.setPassword(createUserDto.getPassword());
        userToRegistering.setRoles("ROLE_USER");
        return userRepository.save(userToRegistering);
    }
}
