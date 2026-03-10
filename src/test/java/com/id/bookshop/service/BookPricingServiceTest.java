package com.id.bookshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import com.id.bookshop.exception.EmptyBasketException;
import org.junit.jupiter.api.Test;

public class BookPricingServiceTest {

    private final BookPricingServiceImpl bookPricingService = new BookPricingServiceImpl(50);


    @Test
    void testCalculatePriceEmptyBasketException() {
        Map<String, Integer> emptyBasket = Map.of();

        assertThrows(EmptyBasketException.class,
                () -> bookPricingService.calculatePrice(emptyBasket),
                "Expected exception when basket is empty");

    }

    @Test
    void testCalculatePriceNullBasketException() {
        assertThrows(EmptyBasketException.class,
                () -> bookPricingService.calculatePrice(null),
                "Expected exception when basket is null");
    }

    @Test
    void testBasketReturnsPriceForOneBook() {
        Map<String, Integer> basket = new HashMap<>();

        basket.put("Clean Code", 1);

        double price = bookPricingService.calculatePrice(basket);

        assertEquals(50.0, price, "A basket with 1 book should cost 50 EUR");
    }

    @Test
    void testBasketReturnsPriceForSimilarBooks() {
        Map<String, Integer> basket = new HashMap<>();

        basket.put("Clean Code", 2);

        double price = bookPricingService.calculatePrice(basket);

        assertEquals(100.0, price, "A basket with 2 similar books should cost 100 EUR");

    }

    @Test
    void testBasketReturnsPriceFor2DifferentBooks() {
        Map<String, Integer> basket = new HashMap<>();

        basket.put("Clean Code", 1);
        basket.put("The Clean Coder", 1);

        double price = bookPricingService.calculatePrice(basket);

        assertEquals(95.0, price, "A basket with 2 different books should cost 95 EUR");

    }

    @Test
    void testBasketReturnsPriceFor3DifferentBooks() {
        Map<String, Integer> basket = new HashMap<>();

        basket.put("Clean Code", 1);
        basket.put("The Clean Coder", 1);
        basket.put("The Clean Architecture", 1);

        double price = bookPricingService.calculatePrice(basket);

        assertEquals(135.0, price, "A basket with 3 different books should cost 135 EUR");

    }

    @Test
    void testBasketReturnsPriceFor4DifferentBooks() {
        Map<String, Integer> basket = new HashMap<>();

        basket.put("Clean Code", 1);
        basket.put("The Clean Coder", 1);
        basket.put("The Clean Architecture", 1);
        basket.put("Test Driven Development", 1);

        double price = bookPricingService.calculatePrice(basket);

        assertEquals(160.0, price, "A basket with 4 different books should cost 160 EUR");

    }

    @Test
    void testBasketReturnsPriceFor5DifferentBooks() {
        Map<String, Integer> basket = new HashMap<>();

        basket.put("Clean Code", 1);
        basket.put("The Clean Coder", 1);
        basket.put("The Clean Architecture", 1);
        basket.put("Test Driven Development", 1);
        basket.put("Working with legacy code", 1);

        double price = bookPricingService.calculatePrice(basket);

        assertEquals(187.5, price, "A basket with 5 different books should cost 187,50 EUR");

    }

    @Test
    void testFullSetAndOneBasketReturnsPrice() {
        Map<String, Integer> basket = new HashMap<>();

        basket.put("Clean Code", 2);
        basket.put("The Clean Coder", 1);
        basket.put("The Clean Architecture", 1);
        basket.put("Test Driven Development", 1);
        basket.put("Working with legacy code", 1);

        double price = bookPricingService.calculatePrice(basket);

        assertEquals(237.5, price, "A basket with 5 different books and one similar book should cost 237,50 EUR");

    }

    @Test
    void testComplexUseCaseBasketReturnsPrice() {
        Map<String, Integer> basket = new HashMap<>();

        basket.put("Clean Code", 2);
        basket.put("The Clean Coder", 2);
        basket.put("The Clean Architecture", 2);
        basket.put("Test Driven Development", 1);
        basket.put("Working with legacy code", 1);

        double price = bookPricingService.calculatePrice(basket);

        assertEquals(320, price, "A basket with 2 groups of 4 different books should cost 320 EUR");

    }


}