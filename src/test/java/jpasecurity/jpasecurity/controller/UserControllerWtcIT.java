package jpasecurity.jpasecurity.controller;

import jpasecurity.jpasecurity.model.dto.CreateUserDto;
import jpasecurity.jpasecurity.model.dto.UpdateUserPasswordDto;
import jpasecurity.jpasecurity.model.dto.UpdateUsernameDto;
import jpasecurity.jpasecurity.model.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
@AutoConfigureMockMvc
@Sql(statements = "delete from users", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserControllerWtcIT {
    /*
        negative tests are broken with webtestclient, til further information they are disabled
     */

    @Autowired
    WebTestClient webTestClient;

    CreateUserDto createUserDto;
    User user;


    @BeforeEach
    void init() throws Exception {


        createUserDto = new CreateUserDto();
        createUserDto.setUsername("testuser");
        createUserDto.setPassword("testpassword");
        createUserDto.setRoles("ROLE_ADMIN");


        long userid = webTestClient
                .post().uri("/api/user")
                .bodyValue(createUserDto)
                .exchange()
                .expectBody(User.class)
                .returnResult()
                .getResponseBody()
                .getId();

        user = new User();
        user.setId(userid);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findUsersAllDataTest() throws Exception {
        webTestClient
                .get()
                .uri("/api/user/{id}", user.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Disabled
    @WithMockUser(roles = "ADMIN")
    void findUsersAllDataUSerNotFoundTest() throws Exception {
        webTestClient
                .get()
                .uri("/api/user/{id}", user.getId() + 1)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Disabled
    @WithMockUser(roles = "USER")
    void findUsersAllDataForbiddenTest() throws Exception {
        webTestClient
                .get()
                .uri("/api/user/{id}", user.getId())
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @Disabled
    @WithMockUser(roles = "USER")
    void createNewUserForbiddenTest() throws Exception {
        webTestClient
                .post()
                .uri("/api/user", user.getId())
                .bodyValue(createUserDto)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createNewUserAuthorizedTest() throws Exception {
        createUserDto.setUsername("jackson");
        webTestClient
                .post()
                .uri("/api/user", user.getId())
                .bodyValue(createUserDto)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createNewUserAuthorizedBadRequestParamValidationFailTest() throws Exception {
        createUserDto.setUsername("");
        webTestClient
                .post()
                .uri("/api/user", user.getId())
                .bodyValue(createUserDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUserPasswordTest() throws Exception {
        webTestClient
                .put()
                .uri("/api/user/{id}/password", user.getId())
                .bodyValue(new UpdateUserPasswordDto("password"))
                .exchange()
                .expectStatus().isAccepted();
    }

    @Test
    @Disabled
    @WithMockUser(roles = "USER")
    void updateUserPasswordForbiddenTest() throws Exception {
        webTestClient
                .put()
                .uri("/api/user/{id}/password", user.getId())
                .bodyValue(new UpdateUserPasswordDto("password"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody().isEmpty();

    }

    @Test
    @Disabled
    void updateUserPasswordIsUnauthorizedTest() throws Exception {
        webTestClient
                .put()
                .uri("/api/user/{id}/password", user.getId())
                .bodyValue(new UpdateUserPasswordDto("password"))
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUserPasswordBadRequestTest() throws Exception {
        webTestClient
                .put()
                .uri("/api/user/{id}/password", user.getId())
                .bodyValue(new UpdateUserPasswordDto(""))
                .exchange()
                .expectStatus().isBadRequest();
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUsernameTest() throws Exception {
        webTestClient
                .put()
                .uri("/api/user/{id}/username", user.getId())
                .bodyValue(new UpdateUsernameDto("newusername"))
                .exchange()
                .expectStatus().isAccepted();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUsernameBadRquestTest() throws Exception {
        webTestClient
                .put()
                .uri("/api/user/{id}/username", user.getId())
                .bodyValue(new UpdateUsernameDto(""))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Disabled
    @WithMockUser(roles = "USER")
    void updateUsernameForbiddenTest() throws Exception {
        webTestClient
                .put()
                .uri("/api/user/{id}/username", user.getId())
                .bodyValue(new UpdateUsernameDto("newusername"))
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @Disabled
    void updateUsernameUnauthorizedTest() throws Exception {
        webTestClient
                .put()
                .uri("/api/user/{id}/username", user.getId())
                .bodyValue(new UpdateUsernameDto("newusername"))
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUserByIdTest() throws Exception {
        webTestClient
                .delete()
                .uri("/api/user/{id}", user.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @Disabled
    @WithMockUser(roles = "ADMIN")
    void deleteUserByIdNotFoundTest() throws Exception {
        webTestClient
                .delete()
                .uri("/api/user/{id}", user.getId() + 1)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @Disabled
    @WithMockUser(roles = "USER")
    void deleteUserByIdForbiddenTest() throws Exception {
        webTestClient
                .delete()
                .uri("/api/user/{id}", user.getId())
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    @Disabled
    void deleteUserByIdUnauthorizedTest() throws Exception {
        webTestClient
                .delete()
                .uri("/api/user/{id}", user.getId())
                .exchange()
                .expectStatus().isUnauthorized();
    }
}
