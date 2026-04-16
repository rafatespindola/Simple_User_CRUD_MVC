package br.com.restaurant.restaurant.validation.appUser;

import br.com.restaurant.restaurant.controller.UserController;
import br.com.restaurant.restaurant.dto.CreateAppUserDTO;
import br.com.restaurant.restaurant.entity.AppUser;
import br.com.restaurant.restaurant.exception.BusinessException;
import br.com.restaurant.restaurant.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UniqueLoginValidationHandler extends AppUserValidationHandler{

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UniqueLoginValidationHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void handle(CreateAppUserDTO appUser) {
        String login = appUser.login();
        List<AppUser> appUsers = this.userRepository.getAppUserByLogin(login);

        logger.info("Verificando se o login já é utilizado");

        if (!appUsers.isEmpty()) {
            throw new BusinessException("Login já exitente. Utilize outro");
        }

        logger.info("Login ainda não utilizado");

        if (next != null) {
            next.handle(appUser);
        }
    }
}
