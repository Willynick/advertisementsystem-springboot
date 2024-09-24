package com.senlainc.advertisementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senlainc.advertisementsystem.backendutils.ViewGetParameter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.controller.mocks.AuthenticationFactoryMock;
import com.senlainc.advertisementsystem.dto.user.profile.ProfileUpdateDto;
import com.senlainc.advertisementsystem.exceptionhandling.ControllerExceptionHandler;
import com.senlainc.advertisementsystem.serviceapi.ProfileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {

    @InjectMocks
    private ProfileController profileController;
    @Mock
    private ProfileService profileService;
    @InjectMocks
    private ObjectMapper objectMapper;
    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;
    private MockMvc mockMvc;

    private static ProfileUpdateDto updateDto;
    private static Authentication authentication;
    private static final Long id = 1L;
    private static final List list = new ArrayList();
    private static List<ViewSortParameter> viewSortParameters;

    @BeforeAll
    static void initialize() {
        updateDto = new ProfileUpdateDto();
        authentication = AuthenticationFactoryMock.getAuthentication();

        viewSortParameters = new ArrayList<>();
        ViewSortParameter parameter = new ViewSortParameter("firstName" , Sort.Direction.ASC);
        viewSortParameters.add(parameter);
        ViewSortParameter parameter2 = new ViewSortParameter("secondName" , Sort.Direction.DESC);
        viewSortParameters.add(parameter2);
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(profileController)
                .setControllerAdvice(controllerExceptionHandler)
                .build();
    }

    @Test
    void update() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "profiles")
                    .content(objectMapper.writeValueAsString(updateDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, atLeastOnce()).update(authentication.getPrincipal(), updateDto);
    }

    @Test
    void updateFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "profiles")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, never()).update(authentication.getPrincipal(), updateDto);
    }

    @Test
    void partiallyUpdate() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "profiles")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, atLeastOnce()).partiallyUpdate(eq(authentication.getPrincipal()), anyList());
    }

    @Test
    void partiallyUpdateFail() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "profiles")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, never()).partiallyUpdate(eq(authentication.getPrincipal()), anyList());
    }

    @Test
    void delete() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "profiles")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, atLeastOnce()).delete(authentication.getPrincipal());
    }

    @Test
    void deleteFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "profiles")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, never()).delete(authentication.getPrincipal());
    }

    @Test
    void getById() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "profiles/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, atLeastOnce()).getById(id);
    }

    @Test
    void get() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "profiles")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, atLeastOnce()).get(viewSortParameters);
    }

    @Test
    void getFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "profiles")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, never()).get(anyList());
    }

    @Test
    void getByParameters() {
        List<ViewGetParameter> parameters = new ArrayList<>();
        ViewGetParameter parameter = new ViewGetParameter("firstName" , "Hi", Sort.Direction.ASC);
        parameters.add(parameter);
        ViewGetParameter parameter2 = new ViewGetParameter("secondName" , "Hello", Sort.Direction.ASC);
        parameters.add(parameter2);

        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "profiles/parameters")
                    .content(objectMapper.writeValueAsString(parameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, atLeastOnce()).getByParameters(parameters);
    }

    @Test
    void getByParametersFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "profiles/parameters")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, never()).getByParameters(anyList());
    }

    @Test
    void getByUser() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "profiles/user/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, atLeastOnce()).getByUser(id);
    }

    @Test
    void getByUsername() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "profiles/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication)
                    .param("username", "username"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, atLeastOnce()).getByUsername("username");
    }

    @Test
    void getByCity() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "profiles/city/1")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, atLeastOnce()).getByCity(id, viewSortParameters);
    }

    @Test
    void getByCityFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "profiles/city/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, never()).getByCity(id, viewSortParameters);
    }

    @Test
    void getByCountry() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "profiles/country/1")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, atLeastOnce()).getByCountry(id, viewSortParameters);
    }

    @Test
    void getByCountryFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "profiles/country/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, never()).getByCountry(id, viewSortParameters);
    }

    @Test
    void addMoney() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "profiles/money")
                    .content(objectMapper.writeValueAsString(updateDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication)
                    .param("amount", "300"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, atLeastOnce()).addMoney(authentication.getPrincipal(), 300);
    }

    @Test
    void addMoneyFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "profiles/money")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, never()).addMoney(authentication.getPrincipal(), 300);
    }
}