package br.com.restaurant.restaurant.exception;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}