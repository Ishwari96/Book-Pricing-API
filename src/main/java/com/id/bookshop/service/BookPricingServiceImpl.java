package com.id.bookshop.service;

import com.id.bookshop.exception.EmptyBasketException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.id.bookshop.service.BookPricingServiceImpl.DiscountRate.FIVE;
import static com.id.bookshop.service.BookPricingServiceImpl.DiscountRate.THREE;
import static com.id.bookshop.service.BookPricingServiceImpl.DiscountRate.FOUR;

/**
 * Implementation class for book price service
 */

@Service
public class BookPricingServiceImpl implements BookPricingService {

    public static final int GROUP_SIZE = 0;
    private final double unitPriceBook;

    // Enum for discount rates
    protected enum DiscountRate {
        NONE(0, 0.0),
        TWO(2, 0.05),
        THREE(3, 0.10),
        FOUR(4, 0.20),
        FIVE(5, 0.25);

        private final int groupSize;
        private final double rate;

        DiscountRate(int groupSize, double rate) {
            this.groupSize = groupSize;
            this.rate = rate;
        }

        public static double getRate(int groupSize) {
            for (DiscountRate discountRate : values()) {
                if (discountRate.groupSize == groupSize) {
                    return discountRate.rate;
                }
            }
            return NONE.rate;
        }
    }

    public BookPricingServiceImpl(@Value("${book.unit.price}") double unitPrice) {
        this.unitPriceBook = unitPrice;
    }

    /**
     * Calculate the discounted price based on requirement
     *
     * @param basket request for books
     * @return calculated price
     */
    @Override
    public double calculatePrice(Map<String, Integer> basket) {

        if (basket == null || basket.isEmpty()) {
            throw new EmptyBasketException("Book basket is empty");
        }

        List<Integer> initialGroups = getGroups(basket);
        List<Integer> optimizedGroups = getOptimizedGroups(initialGroups);

        return optimizedGroups.stream()
                .mapToDouble(group -> unitPriceBook * group * (1 - getDiscount(group)))
                .sum();
    }

    private List<Integer> getGroups(Map<String, Integer> basket) {

        Map<String, Integer> remaining = new HashMap<>(basket);
        List<Integer> groups = new ArrayList<>();
        // count how many unique books are still available
        while (true) {
            int groupSize = remaining.entrySet().stream()
                    .map(e -> {
                        if (e.getValue() > GROUP_SIZE) {
                            e.setValue(e.getValue() - 1);
                            return 1;
                        }
                        return 0;
                    })
                    .reduce(GROUP_SIZE, Integer::sum);

            if (groupSize == GROUP_SIZE) {
                break;
            }
            groups.add(groupSize);
        }
        return groups;
    }

    private List<Integer> getOptimizedGroups(List<Integer> groupsToBeOptimized) {
        List<Integer> optimizedGroups = new ArrayList<>(groupsToBeOptimized);

        while (optimizedGroups.contains(FIVE.groupSize) && optimizedGroups.contains(THREE.groupSize)) {
            optimizedGroups.add(FOUR.groupSize);
            optimizedGroups.add(FOUR.groupSize);
            optimizedGroups.remove(Integer.valueOf(FIVE.groupSize));
            optimizedGroups.remove(Integer.valueOf(THREE.groupSize));
        }
        return optimizedGroups;
    }

    /**
     * Discount calculation logic
     *
     * @param uniqueBooks unique books
     * @return calculated price
     */
    private double getDiscount(int uniqueBooks) {
        return DiscountRate.getRate(uniqueBooks);
    }

}
