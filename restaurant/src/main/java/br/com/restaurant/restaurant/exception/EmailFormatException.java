package br.com.restaurant.restaurant.exception;

public class EmailFormatException extends RuntimeException{
    public EmailFormatException(String message) {
        super(message);
    }
}
