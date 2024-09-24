package com.senlainc.advertisementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.controller.mocks.AuthenticationFactoryMock;
import com.senlainc.advertisementsystem.exceptionhandling.ControllerExceptionHandler;
import com.senlainc.advertisementsystem.serviceapi.MessageService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MessageControllerTest {

    @InjectMocks
    private MessageController messageController;
    @Mock
    private MessageService messageService;
    @InjectMocks
    private ObjectMapper objectMapper;
    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;
    private MockMvc mockMvc;

    private static String message;
    private static Authentication authentication;
    private static final Long id = 1L;
    private static List<ViewSortParameter> viewSortParameters;
    private static ViewUpdateParameter viewUpdateParameter;

    @BeforeAll
    static void initialize() {
        message = "Wow";
        authentication = AuthenticationFactoryMock.getAuthentication();

        viewSortParameters = new ArrayList<>();
        ViewSortParameter parameter = new ViewSortParameter("message" , Sort.Direction.ASC);
        viewSortParameters.add(parameter);
        viewUpdateParameter = new ViewUpdateParameter();
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(messageController)
                .setControllerAdvice(controllerExceptionHandler)
                .build();
    }

    @Test
    void add() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "messages")
                    .content(message)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("recipientId", "1")
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, atLeastOnce()).add(authentication.getPrincipal(), id, message);
    }

    @Test
    void addFail() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "messages")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, never()).add(authentication.getPrincipal(), id, message);
    }

    @Test
    void update() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "messages/1")
                    .content(message)
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, atLeastOnce()).update(authentication.getPrincipal(), id, message);
    }

    @Test
    void updateFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "messages/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, never()).update(authentication.getPrincipal(), id, message);
    }

    @Test
    void partiallyUpdate() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "messages/1")
                    .content(objectMapper.writeValueAsString(viewUpdateParameter))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, atLeastOnce()).partiallyUpdate(authentication.getPrincipal(), id, viewUpdateParameter);
    }

    @Test
    void partiallyUpdateFail() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "messages/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, never()).partiallyUpdate(authentication.getPrincipal(), id, viewUpdateParameter);
    }

    @Test
    void delete() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "messages/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, atLeastOnce()).delete(authentication.getPrincipal(), id);
    }

    @Test
    void deleteFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "messages/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, never()).delete(authentication.getPrincipal(), id);
    }

    @Test
    void getById() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "messages/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, atLeastOnce()).getById(authentication.getPrincipal(), id);
    }

    @Test
    void getByIdFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "messages/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, never()).getById(authentication.getPrincipal(), id);
    }

    @Test
    void getSent() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "messages/user/sent")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, atLeastOnce()).getSent(authentication.getPrincipal(), viewSortParameters);
    }

    @Test
    void getSentFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "messages/user/sent")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, never()).getSent(authentication.getPrincipal(), viewSortParameters);
    }

    @Test
    void getReceived() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "messages/user/received")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, atLeastOnce()).getReceived(authentication.getPrincipal(), viewSortParameters);
    }

    @Test
    void getReceivedFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "messages/user/received")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, never()).getReceived(authentication.getPrincipal(), viewSortParameters);
    }

    @Test
    void getSentInTimeFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "messages/user/sent/time")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, never()).getSentInTime(eq(authentication.getPrincipal()), any(), any(), eq(viewSortParameters));
    }

    @Test
    void getReceivedInTimeFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "messages/user/recieved/time")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(404));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, never()).getReceivedInTime(eq(authentication.getPrincipal()), any(), any(), eq(viewSortParameters));
    }

    @Test
    void setReaded() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "messages/readed/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, atLeastOnce()).setReaded(authentication.getPrincipal(), id);
    }

    @Test
    void setReadedFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "messages/readed/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, never()).setReaded(authentication.getPrincipal(), id);
    }
}