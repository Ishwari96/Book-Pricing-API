package com.id.bookshop.controller;

import com.id.bookshop.dto.ShoppingBasket;
import com.id.bookshop.service.BookPricingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BookPricingController {

    /** The bookPricingService instance of service class */
    private final BookPricingService bookPricingService;

    public BookPricingController(BookPricingService bookPricingService) {
        this.bookPricingService = bookPricingService;
    }

    /**
     * Post api for calculating price
     *
     * @param basket request contains book list
     * @return final price
     */
    @PostMapping("/books/calculate-price")
    public double calculatePrice(@Valid @RequestBody ShoppingBasket basket) {
        return bookPricingService.calculatePrice(basket.basket());
    }

}
