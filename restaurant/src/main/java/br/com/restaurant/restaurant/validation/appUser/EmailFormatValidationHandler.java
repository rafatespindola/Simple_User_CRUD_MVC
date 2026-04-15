package br.com.restaurant.restaurant.validation.appUser;

import br.com.restaurant.restaurant.controller.UserController;
import br.com.restaurant.restaurant.entity.AppUser;
import br.com.restaurant.restaurant.exception.EmailFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailFormatValidationHandler extends AppUserValidationHandler{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Override
    public void handle(AppUser appUser) {
        String email = appUser.getEmail();

        if (email == null || email.isBlank()) {
            throw new EmailFormatException("O e-mail está nulo ou vazio");
        }

        if (!email.contains("@")) {
            throw new EmailFormatException("O e-mail está sem @.");
        }

        if (email.chars().filter(c -> c == '@').count() > 1) {
            throw new EmailFormatException("O e-mail deve conter apenas 1 @");
        }

        if (email.length() > 255) {
            throw new EmailFormatException("O tamanho do e-mail não deve ultrapassar 255 caracteres");
        }

        logger.info("EmailFormatValidationHandler: E-mail bem formatado. Verificado com sucesso.");

        if (next != null) {
            next.handle(appUser);
        }
    }
}
