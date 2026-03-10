
package com.id.bookshop.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.id.bookshop.dto.ShoppingBasket;
import com.id.bookshop.service.BookPricingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

// Replacement for @MockBean in Boot 4: provided by Spring Framework
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

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
    void testCalculatePrice_emptyBasket() throws Exception {
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
}

