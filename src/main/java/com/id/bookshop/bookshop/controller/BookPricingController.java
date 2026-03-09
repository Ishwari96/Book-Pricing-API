package com.id.bookshop.bookshop.controller;

import com.id.bookshop.bookshop.model.ShoppingBasket;
import com.id.bookshop.bookshop.service.BookPricingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BookPricingController {

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
    public double calculatePrice(@RequestBody ShoppingBasket basket) {
        return bookPricingService.calculatePrice(basket.basket());
    }

}
