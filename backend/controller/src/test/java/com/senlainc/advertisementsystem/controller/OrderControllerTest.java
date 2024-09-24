package com.senlainc.advertisementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.controller.mocks.AuthenticationFactoryMock;
import com.senlainc.advertisementsystem.dto.order.OrderUpdateDto;
import com.senlainc.advertisementsystem.exceptionhandling.ControllerExceptionHandler;
import com.senlainc.advertisementsystem.model.order.DeliveryType;
import com.senlainc.advertisementsystem.serviceapi.OrderService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;
    @Mock
    private OrderService orderService;
    @InjectMocks
    private ObjectMapper objectMapper;
    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;
    private MockMvc mockMvc;

    private static OrderUpdateDto updateDto;
    private static Authentication authentication;
    private static final Long id = 1L;
    private static final List list = new ArrayList();
    private static List<ViewSortParameter> viewSortParameters;

    @BeforeAll
    static void initialize() {
        updateDto = new OrderUpdateDto(DeliveryType.BY_COURIER);
        authentication = AuthenticationFactoryMock.getAuthentication();

        viewSortParameters = new ArrayList<>();
        ViewSortParameter parameter = new ViewSortParameter("deliveryType" , Sort.Direction.ASC);
        viewSortParameters.add(parameter);
        ViewSortParameter parameter2 = new ViewSortParameter("date" , Sort.Direction.ASC);
        viewSortParameters.add(parameter2);
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setControllerAdvice(controllerExceptionHandler)
                .build();
    }

    @Test
    void add() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);

        try {
            mockMvc.perform(post(Constants.BASE_URL + "orders")
                    .content(objectMapper.writeValueAsString(ids))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, atLeastOnce()).add(authentication.getPrincipal(), ids);
    }

    @Test
    void addFail() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "orders")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).add(eq(authentication.getPrincipal()), anyList());
    }

    @Test
    void update() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "orders/1")
                    .content(objectMapper.writeValueAsString(updateDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, atLeastOnce()).update(authentication.getPrincipal(), id, updateDto);
    }

    @Test
    void updateFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "orders/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).update(authentication.getPrincipal(), id, updateDto);
    }

    @Test
    void partiallyUpdate() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "orders/1")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, atLeastOnce()).partiallyUpdate(eq(authentication.getPrincipal()), eq(id), anyList());
    }

    @Test
    void partiallyUpdateFail() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "orders/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).partiallyUpdate(eq(authentication.getPrincipal()), eq(id), anyList());
    }

    @Test
    void delete() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "orders/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, atLeastOnce()).delete(authentication.getPrincipal(), id);
    }

    @Test
    void deleteFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "orders/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).delete(authentication.getPrincipal(), id);
    }

    @Test
    void getById() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "orders/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, atLeastOnce()).getById(authentication.getPrincipal(), id);
    }

    @Test
    void getByIdFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "orders/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).getById(authentication.getPrincipal(), id);
    }

    @Test
    void getByUser() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "orders/user")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, atLeastOnce()).getByUser(authentication.getPrincipal(), viewSortParameters);
    }

    @Test
    void getByUserFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "orders/user")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).getByUser(authentication.getPrincipal(), viewSortParameters);
    }

    @Test
    void getByUserInTimeFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "orders/user/time")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).getByUserInTime(eq(authentication.getPrincipal()), any(), any(), eq(viewSortParameters));
    }

    @Test
    void getCompleted() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "orders/user/completed")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, atLeastOnce()).getCompleted(authentication.getPrincipal(), viewSortParameters);
    }

    @Test
    void getCompletedFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "orders/user/completed")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).getCompleted(authentication.getPrincipal(), viewSortParameters);
    }

    @Test
    void getCompletedInTimeFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "orders/user/completed/time")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).getCompletedInTime(eq(authentication.getPrincipal()), any(), any(), eq(viewSortParameters));
    }

    @Test
    void order() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "orders/1/order")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, atLeastOnce()).order(authentication.getPrincipal(), id);
    }

    @Test
    void orderFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "orders/1/order")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).order(authentication.getPrincipal(), id);
    }

    @Test
    void getTotalPrice() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "orders/1/total_price")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, atLeastOnce()).getTotalPrice(authentication.getPrincipal(), id);
    }

    @Test
    void getTotalPriceFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "orders/1/total_price")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).getTotalPrice(authentication.getPrincipal(), id);
    }

    @Test
    void getAmountOfMoneyEarnedInTimeFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "orders/user/amount_of_money_earned/time")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).getAmountOfMoneyEarnedInTime(eq(authentication.getPrincipal()), any(), any());
    }

    @Test
    void getNumberCompletedInTimeFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "orders/user/number_completed/time")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).getNumberCompletedInTime(eq(authentication.getPrincipal()), any(), any());
    }
}