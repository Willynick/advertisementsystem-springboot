package com.senlainc.advertisementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.exceptionhandling.ControllerExceptionHandler;
import com.senlainc.advertisementsystem.serviceapi.CountryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CountryControllerTest {

    @InjectMocks
    private CountryController countryController;
    @Mock
    private CountryService countryService;
    @InjectMocks
    private ObjectMapper objectMapper;
    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;
    private MockMvc mockMvc;

    private static final Long id = 1L;
    private static List<ViewSortParameter> viewSortParameters;

    @BeforeAll
    static void initialize() {
        viewSortParameters = new ArrayList<>();
        ViewSortParameter parameter = new ViewSortParameter("name" , Sort.Direction.ASC);
        viewSortParameters.add(parameter);
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(countryController)
                .setControllerAdvice(controllerExceptionHandler)
                .build();
    }

    @Test
    void getById() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "countries/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(countryService, atLeastOnce()).getById(id);
    }

    @Test
    void get() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "countries")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(countryService, atLeastOnce()).get(viewSortParameters);
    }

    @Test
    void getFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "countries")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(countryService, never()).get(viewSortParameters);
    }

    @Test
    void getByCity() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "countries/city/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(countryService, atLeastOnce()).getByCity(id);
    }
}