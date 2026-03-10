package com.id.bookshop.dto;

public record ErrorResponse(int status, String error, String message) {

}