package com.id.bookshop.service;

import java.util.Map;

/**
 * Interface Book Price Service
 */
public interface BookPricingService {

    double calculatePrice(Map<String, Integer> basket);
}
