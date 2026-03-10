package com.id.bookshop.serviceIT;

import com.id.bookshop.exception.EmptyBasketException;
import com.id.bookshop.service.BookPricingServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "book.unit.price=8.0"   // overrides real application properties
})
class BookPricingServiceImplIntTests {

    @Autowired
    private BookPricingServiceImpl bookPricingService;

    @Test
    @DisplayName("Integration: empty basket throws EmptyBasketException")
    void testEmptyBasketThrowsException() {
        assertThrows(EmptyBasketException.class,
                () -> bookPricingService.calculatePrice(Map.of()),
                "Expected an exception for empty basket");
    }

    @Test
    @DisplayName("Integration: null basket throws EmptyBasketException")
    void testNullBasketThrowsException() {
        assertThrows(EmptyBasketException.class,
                () -> bookPricingService.calculatePrice(null));
    }

    @Test
    @DisplayName("Integration: single book returns correct price")
    void testSingleBookPrice() {
        double result = bookPricingService.calculatePrice(Map.of("Clean Code (Robert Martin, 2008)", 1));
        assertEquals(8.0, result, 0.001);
    }

    @Test
    @DisplayName("Integration: group of 2 different books applies correct discount")
    void testGroupOfTwo() {
        Map<String, Integer> basket = Map.of(
                "Clean Code (Robert Martin, 2008)", 1,
                "The Clean Coder (Robert Martin, 2011)", 1
        );

        double result = bookPricingService.calculatePrice(basket);

        double expected = 8.0 * 2 * (1 - getDiscount(2));  // 5% discount if Potter Kata
        assertEquals(expected, result, 0.001);
    }

    @Test
    @DisplayName("Integration: complex basket uses optimized grouping")
    void testOptimizedGroups() {
        // Example: Potter Kata optimizing 5+3 → 4+4
        Map<String, Integer> basket = Map.of(
                "Clean Code (Robert Martin, 2008)", 2,
                "The Clean Coder (Robert Martin, 2011)", 2,
                "Clean Architecture (Robert Martin, 2017)", 2,
                "Test Driven Development by Example (Kent Beck, 2003)", 1,
                "Working Effectively With Legacy Code (Michael C. Feathers, 2004)", 1
        );

        double result = bookPricingService.calculatePrice(basket);

        // Expected optimized groups:
        // 5+3 → 4+4
        double expected =
                8.0 * 4 * (1 - getDiscount(4)) +
                        8.0 * 4 * (1 - getDiscount(4));

        assertEquals(expected, result, 0.001);
    }


    /* Utility – same discount logic as your service */
    private double getDiscount(int groupSize) {
        return switch (groupSize) {
            case 2 -> 0.05;
            case 3 -> 0.10;
            case 4 -> 0.20;
            case 5 -> 0.25;
            default -> 0.0;
        };
    }
}
