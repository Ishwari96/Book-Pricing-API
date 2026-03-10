package com.id.bookshop.service;


import com.id.bookshop.exception.EmptyBasketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class BookPricingServiceImpl implements BookPricingService {

    public static final int GROUP_SIZE = 0;
    private final double unitPriceBook;

    @Autowired
    public BookPricingServiceImpl(@Value("${book.unit.price}") double unitPrice) {
        this.unitPriceBook = unitPrice;
    }

    /**
     * Calculate the discounted price based on requirement
     *
     * @param basket request for books
     * @return calculated price
     */
    public double calculatePrice(Map<String, Integer> basket) {

        if (basket == null || basket.isEmpty()) {
            throw new EmptyBasketException("Book basket is empty");
        }

        double totalPrice = 0d;
        double discount;

        List<Integer> notOptimizedGroup = getGroups(basket);
        List<Integer> optimizedList = getOptimizedGroups(notOptimizedGroup);

        for (Integer group : optimizedList) {
            discount = getDiscount(group);
            totalPrice += unitPriceBook * group * (1 - discount);
        }

        return totalPrice;
    }


    private List<Integer> getGroups(Map<String, Integer> basket) {

        Map<String, Integer> remaining = new HashMap<>(basket);
        List<Integer> groups = new ArrayList<>();

        while (true) {
            // count how many unique books are still available
            int groupSize = GROUP_SIZE;

            for (Map.Entry<String, Integer> entry : remaining.entrySet()) {
                if (entry.getValue() > GROUP_SIZE) {
                    groupSize++;
                    entry.setValue(entry.getValue() - 1);
                }
            }

            if (groupSize == GROUP_SIZE) {
                break;
            }

            groups.add(groupSize);
        }

        return groups;
    }


    private List<Integer> getOptimizedGroups(List<Integer> groupsToBeOptimized) {
        List<Integer> optimizedGroups = new ArrayList<>(groupsToBeOptimized);

        while (optimizedGroups.contains(5) && optimizedGroups.contains(3)) {

            optimizedGroups.add(4);
            optimizedGroups.add(4);
            optimizedGroups.remove(Integer.valueOf(5));
            optimizedGroups.remove(Integer.valueOf(3));
        }

        return optimizedGroups;
    }

    /**
     * Discount calculation logic
     * @param uniqueBooks unique books
     * @return            calculated price
     */
    private double getDiscount(int uniqueBooks) {
        return switch (uniqueBooks) {
            case 2 -> 0.05;
            case 3 -> 0.10;
            case 4 -> 0.20;
            case 5 -> 0.25;
            default -> 0d;
        };
    }

}
