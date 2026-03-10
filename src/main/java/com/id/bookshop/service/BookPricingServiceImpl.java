package com.id.bookshop.service;


import com.id.bookshop.exception.EmptyBasketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class BookPricingServiceImpl implements BookPricingService {

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
        double discount = 0d;

        List<Integer> notOptimizedGroup = getGroups(basket);
        List<Integer> optimizedList = getOptimizedGroups(notOptimizedGroup);

        for (Integer group : optimizedList) {
            discount = getDiscount(group);
            totalPrice += unitPriceBook * group * (1 - discount);
        }

        return totalPrice;
    }

    private List<Integer> getGroups(Map<String, Integer> basket) {
        Map<String, Integer> copyBasket = new HashMap<String, Integer>(basket);
        List<Integer> listOfGroups = new LinkedList<Integer>();
        boolean hasGroups = true;

        while (hasGroups) {
            int uniqueBooksCounter = 0;
            for (String book : copyBasket.keySet()) {
                int numberOfSameBook = copyBasket.get(book);
                if (numberOfSameBook >= 1) {
                    uniqueBooksCounter++;
                    copyBasket.replace(book, numberOfSameBook - 1);
                }

            }
            if (uniqueBooksCounter == 0) {
                hasGroups = false;
            } else {
                listOfGroups.add(uniqueBooksCounter);
            }
        }
        return listOfGroups;
    }

    private List<Integer> getOptimizedGroups(List<Integer> groupsToBeOptimized) {
        List<Integer> optimizedGroups = new LinkedList<Integer>(groupsToBeOptimized);

        while (optimizedGroups.contains(5) && optimizedGroups.contains(3)) {

            optimizedGroups.add(4);
            optimizedGroups.add(4);
            optimizedGroups.remove(Integer.valueOf(5));
            optimizedGroups.remove(Integer.valueOf(3));
        }

        return optimizedGroups;
    }

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
