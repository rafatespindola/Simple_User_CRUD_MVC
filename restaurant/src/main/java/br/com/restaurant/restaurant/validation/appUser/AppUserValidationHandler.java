package br.com.restaurant.restaurant.validation.appUser;

import br.com.restaurant.restaurant.entity.AppUser;

public abstract class AppUserValidationHandler {
    protected AppUserValidationHandler next;

    public AppUserValidationHandler setNext(AppUserValidationHandler next) {
        this.next = next;
        return next;
    }

    public abstract void handle(AppUser appUser);
}
