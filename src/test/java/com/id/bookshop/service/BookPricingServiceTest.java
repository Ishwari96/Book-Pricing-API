package com.id.bookshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import com.id.bookshop.exception.EmptyBasketException;
import org.junit.jupiter.api.Test;

public class BookPricingServiceTest {

    private final BookPricingServiceImpl bookingPricingService = new BookPricingServiceImpl(40);


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

    @Test
    public void testBasketReturnsPriceForOneBook() {
        Map<String, Integer> basket = new HashMap<>();

        basket.put("Clean Code", 1);

        double price = bookingPricingService.calculatePrice(basket);

        assertEquals(40.0, price, "A basket with 1 book should cost 40 EUR");
    }
}