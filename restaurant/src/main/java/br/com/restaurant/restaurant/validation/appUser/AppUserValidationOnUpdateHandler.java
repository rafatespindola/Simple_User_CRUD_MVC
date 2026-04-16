package br.com.restaurant.restaurant.validation.appUser;

import br.com.restaurant.restaurant.dto.UpdateAppUserDTO;

public abstract class AppUserValidationOnUpdateHandler {
    protected AppUserValidationOnUpdateHandler next;

    public AppUserValidationOnUpdateHandler setNext(AppUserValidationOnUpdateHandler next) {
        this.next = next;
        return next;
    }

    public abstract void handle(UpdateAppUserDTO appUser, Long id);
}
