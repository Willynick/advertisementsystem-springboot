package com.senlainc.advertisementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.controller.mocks.AuthenticationFactoryMock;
import com.senlainc.advertisementsystem.exceptionhandling.ControllerExceptionHandler;
import com.senlainc.advertisementsystem.serviceapi.CommentService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;
    @Mock
    private CommentService commentService;
    @InjectMocks
    private ObjectMapper objectMapper;
    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;
    private MockMvc mockMvc;

    private static String text;
    private static Authentication authentication;
    private static final Long id = 1L;
    private static List<ViewSortParameter> viewSortParameters;
    private static ViewUpdateParameter viewUpdateParameter;

    @BeforeAll
    static void initialize() {
        text = "Wow";
        authentication = AuthenticationFactoryMock.getAuthentication();

        viewSortParameters = new ArrayList<>();
        ViewSortParameter parameter = new ViewSortParameter("text" , Sort.Direction.ASC);
        viewSortParameters.add(parameter);
        viewUpdateParameter = new ViewUpdateParameter();
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController)
                .setControllerAdvice(controllerExceptionHandler)
                .build();
    }

    @Test
    void add() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "comments")
                    .content(text)
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("advertisementId", "1")
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, atLeastOnce()).add(authentication.getPrincipal(), id, text);
    }

    @Test
    void addFail() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "comments")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, never()).add(authentication.getPrincipal(), id, text);
    }

    @Test
    void update() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "comments/1")
                    .content(text)
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, atLeastOnce()).update(authentication.getPrincipal(), id, text);
    }

    @Test
    void updateFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "comments/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, never()).update(authentication.getPrincipal(), id, text);
    }

    @Test
    void partiallyUpdate() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "comments/1")
                    .content(objectMapper.writeValueAsString(viewUpdateParameter))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, atLeastOnce()).partiallyUpdate(authentication.getPrincipal(), id, viewUpdateParameter);
    }

    @Test
    void partiallyUpdateFail() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "comments/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, never()).partiallyUpdate(authentication.getPrincipal(), id, viewUpdateParameter);
    }

    @Test
    void delete() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "comments/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, atLeastOnce()).delete(authentication.getPrincipal(), id);
    }

    @Test
    void deleteFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "comments/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, never()).delete(authentication.getPrincipal(), id);
    }

    @Test
    void getById() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "comments/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, atLeastOnce()).getById(id);
    }

    @Test
    void get() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "comments")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, atLeastOnce()).get(viewSortParameters);
    }

    @Test
    void getFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "comments")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, never()).get(anyList());
    }

    @Test
    void getByUser() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "comments/user/1")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, atLeastOnce()).getByUser(id, viewSortParameters);
    }

    @Test
    void getByUserFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "comments/user/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, never()).getByUser(id, viewSortParameters);
    }

    @Test
    void getByAdvertisement() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "comments/advertisement/1")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, atLeastOnce()).getByAdvertisement(id, viewSortParameters);
    }

    @Test
    void getByAdvertisementFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "comments/advertisement/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, never()).getByAdvertisement(id, viewSortParameters);
    }

    @Test
    void getByUserInTimeFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "comments/user/1/time")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, never()).getByUserInTime(eq(id), any(), any(), eq(viewSortParameters));

    }

    @Test
    void getByAdvertisementInTimeFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "comments/advertisement/1/time")
                    .content(objectMapper.writeValueAsString(viewSortParameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, never()).getByAdvertisementInTime(eq(id), any(), any(), eq(viewSortParameters));
    }
}