package com.senlainc.advertisementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.controller.mocks.AuthenticationFactoryMock;
import com.senlainc.advertisementsystem.exceptionhandling.ControllerExceptionHandler;
import com.senlainc.advertisementsystem.serviceapi.RatingService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RatingControllerTest {

    @InjectMocks
    private RatingController ratingController;
    @Mock
    private RatingService ratingService;
    @InjectMocks
    private ObjectMapper objectMapper;
    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;
    private MockMvc mockMvc;

    private static int rating;
    private static Authentication authentication;
    private static final Long id = 1L;
    private static ViewSortParameter viewSortParameter;
    private static ViewUpdateParameter viewUpdateParameter;

    @BeforeAll
    static void initialize() {
        rating = 9;
        authentication = AuthenticationFactoryMock.getAuthentication();

        viewSortParameter = new ViewSortParameter("rating" , Sort.Direction.ASC);
        viewUpdateParameter = new ViewUpdateParameter();
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController)
                .setControllerAdvice(controllerExceptionHandler)
                .build();
    }

    @Test
    void add() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "ratings")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("recipientId", "1")
                    .param("rating", String.valueOf(rating))
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(ratingService, atLeastOnce()).add(authentication.getPrincipal(), id, rating);
    }

    @Test
    void addFail() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "ratings")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(ratingService, never()).add(authentication.getPrincipal(), id, rating);
    }

    @Test
    void update() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "ratings/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("rating", String.valueOf(rating))
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(ratingService, atLeastOnce()).update(authentication.getPrincipal(), id, rating);
    }

    @Test
    void updateFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "ratings/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(ratingService, never()).update(authentication.getPrincipal(), id, rating);
    }

    @Test
    void partiallyUpdate() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "ratings/1")
                    .content(objectMapper.writeValueAsString(viewUpdateParameter))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(ratingService, atLeastOnce()).partiallyUpdate(authentication.getPrincipal(), id, viewUpdateParameter);
    }

    @Test
    void partiallyUpdateFail() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "ratings/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(ratingService, never()).partiallyUpdate(authentication.getPrincipal(), id, viewUpdateParameter);
    }

    @Test
    void delete() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "ratings/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(ratingService, atLeastOnce()).delete(authentication.getPrincipal(), id);
    }

    @Test
    void deleteFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "ratings/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(ratingService, never()).delete(authentication.getPrincipal(), id);
    }

    @Test
    void getById() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "ratings/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(ratingService, atLeastOnce()).getById(id);
    }

    @Test
    void get() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "ratings")
                    .content(objectMapper.writeValueAsString(viewSortParameter))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(ratingService, atLeastOnce()).get(viewSortParameter);
    }

    @Test
    void getFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "ratings")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(ratingService, never()).get(viewSortParameter);
    }

    @Test
    void getSent() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "ratings/user/sent")
                    .content(objectMapper.writeValueAsString(viewSortParameter))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(ratingService, atLeastOnce()).getSent(authentication.getPrincipal(), viewSortParameter);
    }

    @Test
    void getSentFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "ratings/user/sent")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(ratingService, never()).getSent(authentication.getPrincipal(), viewSortParameter);
    }

    @Test
    void getReceived() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "ratings/user/received")
                    .content(objectMapper.writeValueAsString(viewSortParameter))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(ratingService, atLeastOnce()).getReceived(authentication.getPrincipal(), viewSortParameter);
    }

    @Test
    void getReceivedFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "ratings/user/received")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON)
                    .principal(authentication))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(ratingService, never()).getReceived(authentication.getPrincipal(), viewSortParameter);
    }
}