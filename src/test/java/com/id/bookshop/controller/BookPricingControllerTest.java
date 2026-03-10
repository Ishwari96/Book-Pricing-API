package com.id.bookshop.controller;

import com.id.bookshop.dto.ShoppingBasket;
import com.id.bookshop.service.BookPricingServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
class BookPricingControllerTest {

    @Mock
    BookPricingServiceImpl bookPricingService;
    @InjectMocks
    BookPricingController controller;

    @Test
    void testCalculatePriceEmptyBasket() {
        Map<String, Integer> m = Map.of();

        when(bookPricingService.calculatePrice(m)).thenReturn(0.0);
        double result = controller.calculatePrice(new ShoppingBasket(m));

        // then
        assertEquals(0.0, result);
    }
}
