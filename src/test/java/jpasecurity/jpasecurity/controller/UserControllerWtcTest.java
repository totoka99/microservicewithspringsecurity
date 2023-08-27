package jpasecurity.jpasecurity.controller;

import jpasecurity.jpasecurity.model.ModelMapper;
import jpasecurity.jpasecurity.model.dto.user.CreateUserDto;
import jpasecurity.jpasecurity.model.dto.user.update.UpdateUserPasswordDto;
import jpasecurity.jpasecurity.model.dto.user.update.UpdateUsernameDto;
import jpasecurity.jpasecurity.model.dto.user.UserDto;
import jpasecurity.jpasecurity.model.entity.User;
import jpasecurity.jpasecurity.repository.UserRepository;
import jpasecurity.jpasecurity.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ProblemDetail;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.MethodName.class)
@WebMvcTest(UserController.class)
@Import({UserService.class, ModelMapper.class})
@AutoConfigureMockMvc(addFilters = false)
class UserControllerWtcTest {
    @MockBean
    UserRepository userRepository;
    @MockBean
    PasswordEncoder passwordEncoder;
    @MockBean
    ModelMapper mapper;
    @Autowired
    WebTestClient webTestClient;
    User user;

    @BeforeEach
    void init() {
        user = new User();
        user.setId(1L);
        user.setPassword("password");
        user.setUsername("joe");
        user.setRoles("ROLE_ADMIN,ROLE_USER");
    }

    @Test
    void createNewUserTest() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(userRepository.findByUsernameIgnoreCase(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("passwordEncoded");
        when(mapper.toUserDto(any(User.class)))
                .thenReturn(new UserDto(user.getId(), user.getUsername(), user.getPassword()));

        webTestClient
                .post()
                .uri("/api/user")
                .bodyValue(new CreateUserDto(user.getUsername(), user.getPassword(), user.getRoles()))
                .exchange()
                .expectAll(
                        resp -> resp.expectBody(UserDto.class)
                                .returnResult()
                                .getResponseBody()
                                .getUsername().equals(user.getUsername()),
                        resp -> resp
                                .expectStatus()
                                .isCreated()
                );
    }

    @Test
    void createNewUserBadRequestBadParametersTest() {
        webTestClient
                .post()
                .uri("/api/user")
                .bodyValue(new CreateUserDto("", "password", "roles"))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void createNewUserWithTakenUsernameTest() {
        when(userRepository.findByUsernameIgnoreCase(any())).thenReturn(Optional.of(user));
        webTestClient
                .post()
                .uri("/api/user")
                .bodyValue(new CreateUserDto(user.getUsername(), "password", "roles"))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ProblemDetail.class);
    }

    @Test
    void updateUserPasswordTest() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("passwordEncoded");

        webTestClient
                .patch().uri("/api/user/{id}/password", user.getId())
                .bodyValue(new UpdateUserPasswordDto("passwordEncoded"))
                .exchange()
                .expectStatus().isAccepted();

    }

    @Test
    void updateUserPasswordBadRequestEmptyPasswordTest() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(anyString())).thenReturn("passwordEncoded");

        webTestClient
                .patch().uri("/api/user/{id}/password", user.getId())
                .bodyValue(new UpdateUserPasswordDto(""))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateUsernameTest() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        webTestClient
                .patch().uri("/api/user/{id}/username", user.getId())
                .bodyValue(new UpdateUsernameDto("username"))
                .exchange()
                .expectStatus().isAccepted();
    }

    @Test
    void updateUsernameBadRequestEmptyUsernameTest() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        webTestClient
                .patch().uri("/api/user/{id}/username", user.getId())
                .bodyValue(new UpdateUsernameDto(""))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateUsernameAlreadyTakenUsernameTest() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        User userWithSameName = new User();
        userWithSameName.setUsername("sameName");
        when(userRepository.findByUsernameIgnoreCase(any())).thenReturn(Optional.of(userWithSameName));

        webTestClient
                .patch().uri("/api/user/{id}/username", user.getId())
                .bodyValue(new UpdateUsernameDto("sameName"))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ProblemDetail.class);
    }

    @Test
    void findUsersAllDataTest() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(mapper.toUserDto(any(User.class)))
                .thenReturn(new UserDto(user.getId(), user.getUsername(), user.getPassword()));
        System.out.println(webTestClient
                .get().uri("/api/user/{id}", user.getId())
                .exchange()
                .expectBody(UserDto.class)
                .returnResult().getResponseBody());
//                .expectAll(
//                        resp -> resp.expectStatus().isOk(),
//                        resp -> resp.expectBody(UserDto.class).consumeWith(e -> assertEquals(e.getResponseBody().getId(), user.getId())),
//                        resp -> resp.expectBody(UserDto.class).consumeWith(e -> assertEquals(e.getResponseBody().getUsername(), user.getUsername()))
//                );
    }

    @Test
    void findUsersAllDataNotFoundTest() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        webTestClient
                .get().uri("/api/user/{id}", user.getId())
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void deleteUserByIdTest() {
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        webTestClient
                .delete()
                .uri("/api/user/{id}", user.getId())
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    void deleteUserByIdNotFoundTest() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());
        webTestClient
                .delete()
                .uri("/api/user/{id}", user.getId())
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}
