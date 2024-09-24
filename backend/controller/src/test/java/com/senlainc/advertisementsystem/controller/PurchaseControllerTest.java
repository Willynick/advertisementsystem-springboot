package com.senlainc.advertisementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.controller.mocks.AuthenticationFactoryMock;
import com.senlainc.advertisementsystem.exceptionhandling.ControllerExceptionHandler;
import com.senlainc.advertisementsystem.serviceapi.PurchaseService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PurchaseControllerTest {

    @InjectMocks
    private PurchaseController purchaseController;
    @Mock
    private PurchaseService purchaseService;
    @InjectMocks
    private ObjectMapper objectMapper;
    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;
    private MockMvc mockMvc;

    private static Authentication authentication;
    private static final Long id = 1L;
    private static List<ViewSortParameter> viewSortParameters;

    @BeforeAll
    static void initialize() {
        authentication = AuthenticationFactoryMock.getAuthentication();

        viewSortParameters = new ArrayList<>();
        ViewSortParameter parameter = new ViewSortParameter("earnedMoney" , Sort.Direction.ASC);
        viewSortParameters.add(parameter);
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(purchaseController)
                .setControllerAdvice(controllerExceptionHandler)
                .build();
    }

    @Test
    void add() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "purchases")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication)
                    .param("orderId", "1")
                    .param("advertisementId", "1"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, atLeastOnce()).add(authentication.getPrincipal(), id, id);
    }

    @Test
    void addFail() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "purchases")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, never()).add(authentication.getPrincipal(), id, id);
    }

    @Test
    void delete() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "purchases/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, atLeastOnce()).delete(authentication.getPrincipal(), id);
    }

    @Test
    void deleteFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "purchases/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, never()).delete(authentication.getPrincipal(), id);
    }

    @Test
    void getById() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "purchases/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, atLeastOnce()).getById(authentication.getPrincipal(), id);
    }

    @Test
    void getByIdFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "purchases/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, never()).getById(authentication.getPrincipal(), id);
    }

    @Test
    void getByOrder() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "purchases/order/1")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, atLeastOnce()).getByOrder(authentication.getPrincipal(), id, viewSortParameters);
    }

    @Test
    void getByOrderFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "purchases/order/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, never()).getByOrder(authentication.getPrincipal(), id, viewSortParameters);
    }

    @Test
    void getByUser() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "purchases/user")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, atLeastOnce()).getByUser(authentication.getPrincipal(), viewSortParameters);
    }

    @Test
    void getByUserFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "purchases/user")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, never()).getByUser(authentication.getPrincipal(), viewSortParameters);
    }

    @Test
    void getByUserInTimeFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "purchases/user/time")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, never()).getByUserInTime(eq(authentication.getPrincipal()), any(), any(), eq(viewSortParameters));
    }

    @Test
    void getSalesByUser() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "purchases/user/sales")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, atLeastOnce()).getSalesByUser(authentication.getPrincipal(), viewSortParameters);
    }

    @Test
    void getSalesByUserFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "purchases/user/sales")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, never()).getSalesByUser(authentication.getPrincipal(), viewSortParameters);
    }

    @Test
    void getSalesByUserInTimeFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "purchases/user/sales/time")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, never()).getSalesByUserInTime(eq(authentication.getPrincipal()), any(), any(), eq(viewSortParameters));
    }

    @Test
    void getByAdvertisement() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "purchases/advertisement/1")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, atLeastOnce()).getByAdvertisement(authentication.getPrincipal(), id, viewSortParameters);
    }

    @Test
    void getByAdvertisementFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "purchases/advertisement/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, never()).getByAdvertisement(authentication.getPrincipal(), id, viewSortParameters);
    }

    @Test
    void getByAdvertisementInTimeFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "purchases/advertisement/1/time")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, never()).getByAdvertisementInTime(eq(authentication.getPrincipal()), eq(id), any(), any(), eq(viewSortParameters));
    }
}