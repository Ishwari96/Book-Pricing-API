package com.id.bookshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import com.id.bookshop.exception.EmptyBasketException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class BookPricingServiceTest {

    @InjectMocks
    private BookPricingServiceImpl bookingPricingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCalculatePriceEmptyBasketException() {
        Map<String, Integer> emptyBasket = Map.of();

        assertThrows(EmptyBasketException.class,
                () -> bookingPricingService.calculatePrice(emptyBasket),
                "Expected exception when basket is empty");
    }

    @Test
    void testCalculatePriceNullBasketException() {
        assertThrows(EmptyBasketException.class,
                () -> bookingPricingService.calculatePrice(null),
                "Expected exception when basket is null");
    }
}