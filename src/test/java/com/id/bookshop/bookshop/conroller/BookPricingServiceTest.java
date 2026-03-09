package com.id.bookshop.bookshop.conroller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import com.id.bookshop.bookshop.service.BookPricingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class BookPricingServiceTest {
    @InjectMocks
    private BookPricingServiceImpl bookingPricingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEmptyBasketReturnsZero() {
        Map<String, Integer> basket = new HashMap<>();

        double price = bookingPricingService.calculatePrice(basket);

        assertEquals(0.0, price, "An empty basket should cost 0 EUR");
    }
}