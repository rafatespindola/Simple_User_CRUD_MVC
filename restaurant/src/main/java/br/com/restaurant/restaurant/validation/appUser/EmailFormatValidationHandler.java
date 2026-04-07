package br.com.restaurant.restaurant.validation.appUser;

import br.com.restaurant.restaurant.entity.AppUser;

public class EmailFormatValidationHandler extends AppUserValidationHandler{
    @Override
    public void handle(AppUser appUser) {
        String email = appUser.getEmail();

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("O e-mail está nulo ou vazio");
        }

        if (!email.contains("@")) {
            throw new IllegalArgumentException("O e-mail sem @.");
        }

        if (email.chars().filter(c -> c == '@').count() > 1) {
            throw new IllegalArgumentException("O e-mail deve conter apenas 1 @");
        }

        if (email.length() > 255) {
            throw new IllegalArgumentException("O tamanho do e-mail não deve ultrapassar 255 caracteres");
        }

        if (next != null) {
            next.handle(appUser);
        }
    }
}
