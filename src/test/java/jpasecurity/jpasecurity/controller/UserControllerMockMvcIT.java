package jpasecurity.jpasecurity.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpasecurity.jpasecurity.model.dto.user.CreateUserDto;
import jpasecurity.jpasecurity.model.dto.user.update.UpdateUserPasswordDto;
import jpasecurity.jpasecurity.model.dto.user.update.UpdateUsernameDto;
import jpasecurity.jpasecurity.model.entity.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
@Sql(statements = "delete from users", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class UserControllerMockMvcIT {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    CreateUserDto createUserDto;
    User user;

    @BeforeEach
    @WithMockUser(roles = "ADMIN")
    void init() throws Exception {
        createUserDto = new CreateUserDto();
        createUserDto.setUsername("testuser");
        createUserDto.setPassword("testpassword");
        createUserDto.setRoles("ROLE_ADMIN");

        MvcResult mockResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto))
                        .with(user("admin").roles("ADMIN")))
                .andReturn();

        String content = mockResult.getResponse().getContentAsString();
        user = objectMapper.readValue(content, User.class);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findUsersAllDataTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/{id}", user.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findUsersAllDataUSerNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/{id}", user.getId() + 1))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "USER")
    void findUsersAllDataForbiddenTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/user/{id}", user.getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USER")
    void createNewUserForbiddenTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createNewUserAuthorizedTest() throws Exception {
        createUserDto.setUsername("jackson");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createNewUserAuthorizedBadRequestParamValidationFailTest() throws Exception {
        createUserDto.setUsername("");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUserPasswordTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/user/{id}/password", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpdateUserPasswordDto("passwwrod"))))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateUserPasswordForbiddenTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/user/{id}/password", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpdateUserPasswordDto("something"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void updateUserPasswordIsUnauthorizedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/user/{id}/password", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpdateUserPasswordDto("something"))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUserPasswordBadRequestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/user/{id}/password", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpdateUserPasswordDto(""))))
                .andExpect(status().isBadRequest());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUsernameTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/user/{id}/username", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpdateUsernameDto("something"))))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateUsernameBadRquestTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/user/{id}/username", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpdateUsernameDto(""))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "USER")
    void updateUsernameForbiddenTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/user/{id}/username", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpdateUsernameDto("something"))))
                .andExpect(status().isForbidden());
    }

    @Test
    void updateUsernameUnauthorizedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/user/{id}/username", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UpdateUsernameDto("something"))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUserByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/{id}", user.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteUserByIdNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/{userId}", user.getId() + 1))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    void deleteUserByIdForbiddenTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/{id}", user.getId()))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteUserByIdUnauthorizedTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/user/{id}", user.getId()))
                .andExpect(status().isUnauthorized());
    }
}