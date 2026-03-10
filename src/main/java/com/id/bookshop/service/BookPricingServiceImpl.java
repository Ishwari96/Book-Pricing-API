package com.id.bookshop.service;


import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BookPricingServiceImpl implements BookPricingService {

    /**
     * Calculate the discounted price based on requirement
     *
     * @param basket request for books
     * @return calculated price
     */
    public double calculatePrice(Map<String, Integer> basket) {
        return 0.0;
    }

}
