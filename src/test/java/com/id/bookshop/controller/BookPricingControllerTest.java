
package com.id.bookshop.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.id.bookshop.dto.ErrorResponse;
import com.id.bookshop.dto.ShoppingBasket;
import com.id.bookshop.exception.EmptyBasketException;
import com.id.bookshop.service.BookPricingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

// Replacement for @MockBean in Boot 4: provided by Spring Framework
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookPricingController.class)
class BookPricingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    BookPricingService bookPricingService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("POST /books/calculate-price with empty basket returns 0.0")
    void testCalculatePriceEmptyBasket() throws Exception {
        Map<String, Integer> empty = Map.of();
        ShoppingBasket basket = new ShoppingBasket(empty);

        when(bookPricingService.calculatePrice(empty)).thenReturn(0.0);

        mockMvc.perform(post("/api/v1/books/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(basket)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("0.0"));
    }


    @Test
    @DisplayName("POST /books/calculate-price when basket is empty -> 400 Bad Request")
    void testCalculatePriceEmptyBasketException() throws Exception {
        Map<String, Integer> empty = Map.of();
        ShoppingBasket basket = new ShoppingBasket(empty);

        // Book Pricing Service throws exception
        when(bookPricingService.calculatePrice(empty))
                .thenThrow(new EmptyBasketException("Book basket is empty"));

        MvcResult result = mockMvc.perform(post("/api/v1/books/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(basket)))
                .andExpect(status().isBadRequest())       // expect 400
                .andReturn();

        ErrorResponse responseBody = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(),
                ErrorResponse.class);


        assertEquals(400, responseBody.status());
        assertEquals("BAD_BASKET", responseBody.error());
        assertEquals("Book basket is empty", responseBody.message());

    }

    @Test
    @DisplayName("POST /books/calculate-price when books are in the basket -> 200")
    void testApiEndpointBasket() throws Exception {
        Map<String, Integer> request = new HashMap<String, Integer>();
        request.put("clean", 2);
        request.put("cleaner", 2);
        request.put("arch", 2);
        request.put("test", 1);
        request.put("working", 1);
        ShoppingBasket basket = new ShoppingBasket(request);
        System.out.println(objectMapper.writeValueAsString(basket));
        when(bookPricingService.calculatePrice(request)).thenReturn(320.0);
        MvcResult result = mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/books/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(basket)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        Double response = objectMapper.readValue(
                result.getResponse().getContentAsByteArray(),
                Double.class);


        assertEquals(320.0, response);
    }

}

