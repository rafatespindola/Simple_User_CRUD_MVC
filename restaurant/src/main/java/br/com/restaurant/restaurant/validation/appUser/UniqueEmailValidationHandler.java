package br.com.restaurant.restaurant.validation.appUser;

import br.com.restaurant.restaurant.controller.UserController;
import br.com.restaurant.restaurant.dto.CreateAppUserDTO;
import br.com.restaurant.restaurant.entity.AppUser;
import br.com.restaurant.restaurant.exception.BusinessException;
import br.com.restaurant.restaurant.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UniqueEmailValidationHandler extends AppUserValidationHandler{

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UniqueEmailValidationHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void handle(CreateAppUserDTO appUser) {
        boolean alreadyExistsUserEmail = this.userRepository.existsByEmail(appUser.email());

        if (alreadyExistsUserEmail) {
            throw new BusinessException("E-mail já utilizado por outro usuário");
        }

        logger.info("E-mail ainda não utilizado");

        if (next != null) {
            next.handle(appUser);
        }
    }


}
