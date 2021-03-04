package com.oneworldaccuracy.onboardingservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oneworldaccuracy.onboardingservice.service.UserService;
import com.oneworldaccuracy.onboardingservice.util.Constants;
import com.oneworldaccuracy.onboardingservice.vo.UserDto;
import com.oneworldaccuracy.onboardingservice.vo.enums.Roles;
import com.oneworldaccuracy.onboardingservice.vo.enums.Status;
import com.oneworldaccuracy.onboardingservice.vo.request.RegisterRequest;
import com.oneworldaccuracy.onboardingservice.vo.request.UpdateRequest;
import com.oneworldaccuracy.onboardingservice.vo.response.SuccessResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
public class OnboardingControllerTest {

    @MockBean
    UserService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    HttpServletRequest req;


    @Test
    public void registerUser() throws Exception {

        UserDto dto = new UserDto(0L, "MR", "Hassan", "LastName", "admin@oneworldaccuracy.com", "08022318882", encoder.encode("user@1234"), Roles.ADMIN, LocalDate.now(),
                null, null, true, false, Status.VERIFIED, null);
        RegisterRequest request = new RegisterRequest("MR", "Hassan", "LastName", "xyz@gmail.com", "08022318882", "user@1234");
        when(service.registerUser(request, req)).thenReturn(new SuccessResponse(Constants.OPERATION_SUCCESS, null));

        this.mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }


    @Test
    public void getRegisteredUsers() throws Exception {

        List<UserDto> users = new ArrayList<>();
        UserDto dto = new UserDto(8L, "MR", "Hassan", "LastName", "admin@oneworldaccuracy.com", "08022318882", encoder.encode("user@1234"), Roles.ADMIN, LocalDate.now(),
                null, null, true, false, Status.VERIFIED, null);
        users.add(dto);
        when(service.getRegisteredUsers(1, 10)).thenReturn(new SuccessResponse(Constants.OPERATION_SUCCESS, users));

        this.mockMvc.perform(get("/users/1/10"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void updateUser() throws Exception {

        UserDto dto = new UserDto(8L, "MR", "Hassan", "LastName", "admin@oneworldaccuracy.com", "08022318882", encoder.encode("user@1234"), Roles.ADMIN, LocalDate.now(),
                null, null, true, false, Status.VERIFIED, null);
        UpdateRequest request = new UpdateRequest("MR", "Hassan", "LastName", "xyz@gmail.com", "08022318882");
        when(service.updateUser(8L, request)).thenReturn(new SuccessResponse(Constants.OPERATION_SUCCESS, null));

        this.mockMvc.perform(put("/user/8").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void deactivateUser() throws Exception {

        when(service.deactivateUser(10)).thenReturn(new SuccessResponse(Constants.OPERATION_SUCCESS, null));

        this.mockMvc.perform(delete("/user/10"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void verifyEmail() throws Exception {

        when(service.verifyUser("ib3brib3irbi3rb3ibr")).thenReturn(new SuccessResponse(Constants.OPERATION_SUCCESS, null));

        this.mockMvc.perform(get("/verify?code=ib3brib3irbi3rb3ibr"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
