package com.senlainc.advertisementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senlainc.advertisementsystem.backendutils.ViewGetParameter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.controller.mocks.AuthenticationFactoryMock;
import com.senlainc.advertisementsystem.dto.advertisement.AdvertisementUpdateDto;
import com.senlainc.advertisementsystem.exceptionhandling.ControllerExceptionHandler;
import com.senlainc.advertisementsystem.serviceapi.AdvertisementService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AdvertisementControllerTest {

    @InjectMocks
    private AdvertisementController advertisementController;
    @Mock
    private AdvertisementService advertisementService;
    @InjectMocks
    private ObjectMapper objectMapper;
    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;
    private MockMvc mockMvc;

    private static AdvertisementUpdateDto updateDto;
    private static Authentication authentication;
    private static final Long id = 1L;
    private static final List list = new ArrayList();
    private static List<ViewSortParameter> viewSortParameters;

    @BeforeAll
    static void initialize() {
        String title = "title";
        String description = "description";
        double price = 36.28;
        updateDto = AdvertisementUpdateDto.builder().title(title).description(description).price(price)
                .phoneNumber("546641964196").build();
        authentication = AuthenticationFactoryMock.getAuthentication();

        viewSortParameters = new ArrayList<>();
        ViewSortParameter parameter = new ViewSortParameter("title" , Sort.Direction.ASC);
        viewSortParameters.add(parameter);
        ViewSortParameter parameter2 = new ViewSortParameter("description" , Sort.Direction.ASC);
        viewSortParameters.add(parameter2);
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(advertisementController)
                .setControllerAdvice(controllerExceptionHandler)
                .build();
    }

    @Test
    void add() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "advertisements")
                    .content(objectMapper.writeValueAsString(updateDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).add(authentication.getPrincipal(), updateDto);
    }

    @Test
    void addFail() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "advertisements")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, never()).add(authentication.getPrincipal(), updateDto);
    }

    @Test
    void update() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "advertisements/1")
                    .content(objectMapper.writeValueAsString(updateDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).update(authentication.getPrincipal(), id, updateDto);
    }

    @Test
    void updateFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "advertisements/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, never()).update(authentication.getPrincipal(), id, updateDto);
    }

    @Test
    void partiallyUpdate() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "advertisements/1")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).partiallyUpdate(eq(authentication.getPrincipal()), eq(id), anyList());
    }

    @Test
    void partiallyUpdateFail() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "advertisements/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, never()).partiallyUpdate(eq(authentication.getPrincipal()), eq(id), anyList());
    }

    @Test
    void delete() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "advertisements/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).delete(authentication.getPrincipal(), id);
    }

    @Test
    void deleteFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "advertisements/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, never()).delete(authentication.getPrincipal(), id);
    }

    @Test
    void getById() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "advertisements/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).getById(id);
    }

    @Test
    void get() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "advertisements")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).get(viewSortParameters);
    }

    @Test
    void getFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "advertisements")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, never()).get(anyList());
    }

    @Test
    void getByParameters() {
        List<ViewGetParameter> parameters = new ArrayList<>();
        ViewGetParameter parameter = new ViewGetParameter("title" , "Hi", Sort.Direction.ASC);
        parameters.add(parameter);
        ViewGetParameter parameter2 = new ViewGetParameter("description" , "Hello", Sort.Direction.ASC);
        parameters.add(parameter2);

        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "advertisements/parameters")
                    .content(objectMapper.writeValueAsString(parameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).getByParameters(parameters);
    }

    @Test
    void getByParametersFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "advertisements/parameters")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, never()).getByParameters(anyList());
    }

    @Test
    void getByUser() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "advertisements/user/1")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).getByUser(id, viewSortParameters);
    }

    @Test
    void getByUserFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "advertisements/user/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, never()).getByUser(id, viewSortParameters);
    }

    @Test
    void getByCategory() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "advertisements/category/1")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).getByCategory(id, viewSortParameters);
    }

    @Test
    void getByCategoryFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "advertisements/category/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, never()).getByCategory(id, viewSortParameters);
    }

    @Test
    void getInTheTop() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "advertisements/top")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).getInTheTop();
    }

    @Test
    void getByInterests() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "advertisements/user/interests")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).getByInterests(authentication.getPrincipal());
    }

    @Test
    void getByInterestsFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "advertisements/user/interests")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, never()).getByInterests(authentication.getPrincipal());
    }

    @Test
    void getByCity() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "advertisements/city/1")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).getByCity(id, viewSortParameters);
    }

    @Test
    void getByCityFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "advertisements/city/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, never()).getByCity(id, viewSortParameters);
    }

    @Test
    void getByCountry() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "advertisements/country/1")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).getByCountry(id, viewSortParameters);
    }

    @Test
    void getByCountryFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "advertisements/country/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, never()).getByCountry(id, viewSortParameters);
    }

    @Test
    void raiseInTheTop() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "advertisements/1/raise")
                    .param("amount", "500")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).raiseInTheTop(authentication.getPrincipal(), id, 500);
    }

    @Test
    void raiseInTheTopFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "advertisements/1/raise")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, never()).raiseInTheTop(authentication.getPrincipal(), id, 500);
    }

    @Test
    void hide() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "advertisements/1/hide")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).hide(authentication.getPrincipal(), id);
    }

    @Test
    void hideFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "advertisements/1/hide")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, never()).hide(authentication.getPrincipal(), id);
    }

    @Test
    void restore() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "advertisements/1/restore")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).restore(authentication.getPrincipal(), id);
    }

    @Test
    void restoreFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "advertisements/1/restore")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, never()).restore(authentication.getPrincipal(), id);
    }
}