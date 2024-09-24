package com.senlainc.advertisementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senlainc.advertisementsystem.backendutils.ViewGetParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.dto.address.country.CountryUpdateDto;
import com.senlainc.advertisementsystem.dto.address.country.city.CityUpdateDto;
import com.senlainc.advertisementsystem.dto.advertisement.AdvertisementUpdateDto;
import com.senlainc.advertisementsystem.dto.category.CategoryUpdateDto;
import com.senlainc.advertisementsystem.dto.user.UserUpdateDto;
import com.senlainc.advertisementsystem.dto.user.profile.ProfileUpdateDto;
import com.senlainc.advertisementsystem.exceptionhandling.ControllerExceptionHandler;
import com.senlainc.advertisementsystem.model.order.DeliveryType;
import com.senlainc.advertisementsystem.serviceapi.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @InjectMocks
    private AdminController adminController;
    @Mock
    private AdvertisementService advertisementService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private CityService cityService;
    @Mock
    private CommentService commentService;
    @Mock
    private CountryService countryService;
    @Mock
    private MessageService messageService;
    @Mock
    private OrderService orderService;
    @Mock
    private ProfileService profileService;
    @Mock
    private PurchaseService purchaseService;
    @Mock
    private RatingService ratingService;
    @Mock
    private UserService userService;
    @InjectMocks
    private ObjectMapper objectMapper;
    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;
    private MockMvc mockMvc;

    private static AdvertisementUpdateDto advertisementUpdateDto;
    private static CategoryUpdateDto categoryUpdateDto;
    private static CityUpdateDto cityUpdateDto;
    private static String text;
    private static CountryUpdateDto countryUpdateDto;
    private static ProfileUpdateDto profileUpdateDto;
    private static UserUpdateDto userUpdateDto;
    private static final Long id = 1L;
    private static final List list = new ArrayList();
    private static ViewUpdateParameter viewUpdateParameter;

    @BeforeAll
    static void initialize() {
        advertisementUpdateDto = AdvertisementUpdateDto.builder().title("title").description("description")
                .phoneNumber("102561464").build();
        categoryUpdateDto = new CategoryUpdateDto("category", 9L);
        cityUpdateDto = new CityUpdateDto("city", 9L);
        text = "text";
        countryUpdateDto = new CountryUpdateDto("country");
        profileUpdateDto = new ProfileUpdateDto();
        userUpdateDto = new UserUpdateDto();
        viewUpdateParameter = new ViewUpdateParameter();
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                .setControllerAdvice(controllerExceptionHandler)
                .build();
    }

    @Test
    void updateAdvertisement() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "admin/advertisements/1")
                    .content(objectMapper.writeValueAsString(advertisementUpdateDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).update(id, advertisementUpdateDto);
    }

    @Test
    void updateAdvertisementFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "admin/advertisements/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, never()).update(eq(id), any());
    }


    @Test
    void partiallyUpdateAdvertisement() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "admin/advertisements/1")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).partiallyUpdate(eq(id), anyList());
    }

    @Test
    void partiallyUpdateAdvertisementFail() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "admin/advertisements/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, never()).partiallyUpdate(id, eq(anyList()));
    }

    @Test
    void deleteAdvertisement() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "admin/advertisements/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).delete(id);
    }

    @Test
    void raiseInTheTopAdvertisement() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "admin/advertisements/1/raise")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).raiseInTheTop(id);
    }

    @Test
    void hideAdvertisement() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "admin/advertisements/1/hide")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).hide(id);
    }

    @Test
    void restoreAdvertisement() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "admin/advertisements/1/restore")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(advertisementService, atLeastOnce()).restore(id);
    }

    @Test
    void addCategory() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "admin/categories")
                    .content(objectMapper.writeValueAsString(categoryUpdateDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(categoryService, atLeastOnce()).add(categoryUpdateDto);
    }

    @Test
    void addCategoryFail() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "admin/categories")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(categoryService, never()).add(any());
    }

    @Test
    void updateCategory() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "admin/categories/1")
                    .content(objectMapper.writeValueAsString(categoryUpdateDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(categoryService, atLeastOnce()).update(id, categoryUpdateDto);
    }

    @Test
    void updateCategoryFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "admin/categories/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(categoryService, never()).update(eq(id), any());
    }

    @Test
    void partiallyUpdateCategory() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "admin/categories/1")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(categoryService, atLeastOnce()).partiallyUpdate(eq(id), anyList());
    }

    @Test
    void partiallyUpdateCategoryFail() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "admin/categories/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(categoryService, never()).partiallyUpdate(eq(id), anyList());
    }

    @Test
    void deleteCategory() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "admin/categories/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(categoryService, atLeastOnce()).delete(id);
    }

    @Test
    void addCity() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "admin/cities")
                    .content(objectMapper.writeValueAsString(cityUpdateDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(cityService, atLeastOnce()).add(cityUpdateDto);
    }

    @Test
    void addCityFail() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "admin/cities")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(cityService, never()).add(any());
    }

    @Test
    void updateCity() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "admin/cities/1")
                    .content(objectMapper.writeValueAsString(cityUpdateDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(cityService, atLeastOnce()).update(id, cityUpdateDto);
    }

    @Test
    void updateCityFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "admin/cities/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(cityService, never()).update(eq(id), any());
    }

    @Test
    void partiallyUpdateCity() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "admin/cities/1")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(cityService, atLeastOnce()).partiallyUpdate(eq(id), anyList());
    }

    @Test
    void partiallyUpdateCityFail() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "admin/cities/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(cityService, never()).partiallyUpdate(eq(id), anyList());
    }

    @Test
    void deleteCity() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "admin/cities/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(cityService, atLeastOnce()).delete(id);
    }

    @Test
    void updateComment() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "admin/comments/1")
                    .content(text)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, atLeastOnce()).update(id, text);
    }

    @Test
    void partiallyUpdateComment() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "admin/comments/1")
                    .content(objectMapper.writeValueAsString(viewUpdateParameter))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, atLeastOnce()).partiallyUpdate(id, viewUpdateParameter);
    }

    @Test
    void partiallyUpdateCommentFail() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "admin/comments/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, never()).partiallyUpdate(id, viewUpdateParameter);
    }

    @Test
    void deleteComment() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "admin/comments/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(commentService, atLeastOnce()).delete( id);
    }

    @Test
    void addCountry() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "admin/countries")
                    .content(objectMapper.writeValueAsString(countryUpdateDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(countryService, atLeastOnce()).add(countryUpdateDto);
    }

    @Test
    void addCountryFail() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "admin/countries")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(countryService, never()).add(any());
    }

    @Test
    void updateCountry() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "admin/countries/1")
                    .content(objectMapper.writeValueAsString(countryUpdateDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(countryService, atLeastOnce()).update(id, countryUpdateDto);
    }

    @Test
    void updateCountryFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "admin/countries/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(countryService, never()).update(eq(id), any());
    }

    @Test
    void partiallyUpdateCountry() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "admin/countries/1")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(countryService, atLeastOnce()).partiallyUpdate(eq(id), anyList());
    }

    @Test
    void partiallyUpdateCountryFail() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "admin/countries/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(countryService, never()).partiallyUpdate(eq(id), anyList());
    }

    @Test
    void deleteCountry() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "admin/countries/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(countryService, atLeastOnce()).delete(id);
    }

    @Test
    void deleteMessage() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "admin/messages/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, atLeastOnce()).delete(id);
    }

    @Test
    void getByIdMessage() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/messages/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, atLeastOnce()).getById(id);
    }

    @Test
    void getMessages() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/messages")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, atLeastOnce()).get(anyList());
    }

    @Test
    void getMessagesFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/messages")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, never()).get(anyList());
    }

    @Test
    void getSentMessages() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/messages/user/1/sent")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, atLeastOnce()).getSent(eq(id), anyList());
    }

    @Test
    void getSentMessagesFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/messages/user/1/sent")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, never()).getSent(eq(id), anyList());
    }

    @Test
    void getReceivedMessages() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/messages/user/1/received")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, atLeastOnce()).getReceived(eq(id), anyList());
    }

    @Test
    void getReceivedMessagesFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/messages/user/1/received")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, never()).getReceived(eq(id), anyList());
    }

    @Test
    void getSentInTimeMessagesFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/messages/user/1/sent/time")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, never()).getSentInTime(eq(id), any(), any(), anyList());
    }

    @Test
    void getReceivedInTimeMessagesFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/messages/user/1/recieved/time")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(404));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(messageService, never()).getReceivedInTime(eq(id), any(), any(), anyList());
    }

    @Test
    void deleteOrder() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "admin/orders/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, atLeastOnce()).delete(id);
    }

    @Test
    void getByIdOrder() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/orders/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, atLeastOnce()).getById(id);
    }

    @Test
    void getOrders() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/orders")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, atLeastOnce()).get(anyList());
    }

    @Test
    void getOrdersFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/orders")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).get(anyList());
    }

    @Test
    void getByParametersOrders() {
        List<ViewGetParameter> parameters = new ArrayList<>();
        ViewGetParameter parameter = new ViewGetParameter("deliveryType" , DeliveryType.BY_MAIL, Sort.Direction.ASC);
        parameters.add(parameter);

        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/orders/parameters")
                    .content(objectMapper.writeValueAsString(parameters))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, atLeastOnce()).getByParameters(anyList());
    }

    @Test
    void getByParametersOrdersFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/orders/parameters")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).getByParameters(anyList());
    }

    @Test
    void getByUserOrders() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/orders/user/1")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, atLeastOnce()).getByUser(eq(id), anyList());
    }

    @Test
    void getByUserOrdersFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/orders/user/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).getByUser(eq(id), anyList());
    }

    @Test
    void getByUserInTimeOrdersFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/orders/user/1/time")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).getByUserInTime(eq(id), any(), any(), anyList());
    }

    @Test
    void getCompletedOrders() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/orders/user/1/completed")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, atLeastOnce()).getCompleted(eq(id), anyList());
    }

    @Test
    void getCompletedOrdersFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/orders/user/1/completed")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).getCompleted(eq(id), anyList());
    }

    @Test
    void getCompletedAllOrders() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/orders/completed")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, atLeastOnce()).getCompletedAll(anyList());
    }

    @Test
    void getCompletedAllOrdersFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/orders/completed")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).getCompletedAll(anyList());
    }

    @Test
    void getCompletedInTimeOrdersFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/orders/user/1/completed/time")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).getCompletedInTime(eq(id), any(), any(), anyList());
    }

    @Test
    void getCompletedInTimeAllOrdersFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/orders/completed/time")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).getCompletedInTimeAll(any(), any(), anyList());
    }

    @Test
    void getTotalPriceOrder() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/orders/1/total_price")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, atLeastOnce()).getTotalPrice(id);
    }

    @Test
    void getAmountOfMoneyEarnedInTimeOrdersFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/orders/user/1/amount_of_money_earned/time")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).getAmountOfMoneyEarnedInTime(eq(id), any(), any());
    }

    @Test
    void getNumberCompletedInTimeOrdersFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/orders/user/1/number_completed/time")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(orderService, never()).getNumberCompletedInTime(eq(id), any(), any());
    }

    @Test
    void updateProfile() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "admin/profiles/user/1")
                    .content(objectMapper.writeValueAsString(profileUpdateDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, atLeastOnce()).update(id, profileUpdateDto);
    }

    @Test
    void updateProfileFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "admin/profiles/user/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, never()).update(eq(id), any());
    }

    @Test
    void partiallyUpdateProfile() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "admin/profiles/user/1")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, atLeastOnce()).partiallyUpdate(eq(id), anyList());
    }

    @Test
    void partiallyUpdateProfileFail() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "admin/profiles/user/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, never()).partiallyUpdate(eq(id), anyList());
    }

    @Test
    void deleteProfile() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "admin/profiles/user/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, atLeastOnce()).delete(id);
    }

    @Test
    void addMoneyProfile() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "admin/profiles/user/1/money")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("amount", "300"))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, atLeastOnce()).addMoney(id, 300);
    }

    @Test
    void addMoneyProfileFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "admin/profiles/user/1/money")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(profileService, never()).addMoney(id, 300);
    }

    @Test
    void deletePurchase() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "admin/purchases/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, atLeastOnce()).delete(id);
    }

    @Test
    void getByIdPurchase() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/purchases/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, atLeastOnce()).getById(id);
    }

    @Test
    void getPurchases() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/purchases")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, atLeastOnce()).get(anyList());
    }

    @Test
    void getPurchasesFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/purchases")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, never()).get(anyList());
    }

    @Test
    void getByOrderPurchases() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/purchases/order/1")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, atLeastOnce()).getByOrder(eq(id), anyList());
    }

    @Test
    void getByOrderPurchasesFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/purchases/order/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, never()).getByOrder(eq(id), anyList());
    }

    @Test
    void getByUserPurchases() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/purchases/user/1")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, atLeastOnce()).getByUser(eq(id), anyList());
    }

    @Test
    void getByUserPurchasesFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/purchases/user/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, never()).getByUser(eq(id), anyList());
    }

    @Test
    void getByUserInTimePurchasesFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/purchases/user/1/time")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, never()).getByUserInTime(eq(id), any(), any(), anyList());
    }

    @Test
    void getSalesByUserPurchases() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/purchases/user/1/sales")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, atLeastOnce()).getSalesByUser(eq(id), anyList());
    }

    @Test
    void getSalesByUserPurchasesFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/purchases/user/1/sales")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, never()).getSalesByUser(eq(id), anyList());
    }

    @Test
    void getSalesByUserInTimePurchasesFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/purchases/user/1/sales/time")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, never()).getSalesByUserInTime(eq(id), any(), any(), anyList());
    }

    @Test
    void getByAdvertisementPurchases() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/purchases/advertisement/1")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, atLeastOnce()).getByAdvertisement(eq(id), anyList());
    }

    @Test
    void getByAdvertisementPurchasesFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/purchases/advertisement/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, never()).getByAdvertisement(eq(id), anyList());
    }

    @Test
    void getByAdvertisementInTimePurchasesFail() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/purchases/advertisement/1/time")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(purchaseService, never()).getByAdvertisementInTime(eq(null), eq(id), any(), any(), anyList());
    }

    @Test
    void deleteRating() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "admin/ratings/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(ratingService, atLeastOnce()).delete(id);
    }

    @Test
    void updateUser() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "admin/users/1")
                    .content(objectMapper.writeValueAsString(userUpdateDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(userService, atLeastOnce()).update(id, userUpdateDto);
    }

    @Test
    void updateUserFail() {
        try {
            mockMvc.perform(put(Constants.BASE_URL + "admin/users/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(userService, never()).update(eq(id), any());
    }

    @Test
    void partiallyUpdateUser() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "admin/users/1")
                    .content(objectMapper.writeValueAsString(list))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(userService, atLeastOnce()).partiallyUpdate(eq(id), anyList());
    }

    @Test
    void partiallyUpdateUserFail() {
        try {
            mockMvc.perform(patch(Constants.BASE_URL + "admin/users/1")
                    .content(objectMapper.writeValueAsString(null))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(400));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(userService, never()).partiallyUpdate(eq(id), anyList());
    }

    @Test
    void deleteUser() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete(Constants.BASE_URL + "admin/users/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(userService, atLeastOnce()).delete(id);
    }

    @Test
    void getRolesUser() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(Constants.BASE_URL + "admin/users/1/roles")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(userService, atLeastOnce()).getRoles(id);
    }
}