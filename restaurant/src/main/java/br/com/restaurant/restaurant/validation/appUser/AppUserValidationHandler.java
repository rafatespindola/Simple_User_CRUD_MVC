package br.com.restaurant.restaurant.validation.appUser;

import br.com.restaurant.restaurant.dto.CreateAppUserDTO;

public abstract class AppUserValidationHandler {
    protected AppUserValidationHandler next;

    public AppUserValidationHandler setNext(AppUserValidationHandler next) {
        this.next = next;
        return next;
    }

    public abstract void handle(CreateAppUserDTO appUser);
}
