package com.id.bookshop.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record ShoppingBasket(@NotNull Map<String, Integer> basket) {

}