package com.id.bookshop.service;

import java.util.Map;

public interface BookPricingService {

    double calculatePrice(Map<String, Integer> basket);
}
