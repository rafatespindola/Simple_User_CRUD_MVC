package br.com.restaurant.restaurant.validation.appUser;

import br.com.restaurant.restaurant.controller.UserController;
import br.com.restaurant.restaurant.entity.AppUser;
import br.com.restaurant.restaurant.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class UniqueEmailOnUpdateValidationHandler extends AppUserValidationHandler{

    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UniqueEmailOnUpdateValidationHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void handle(AppUser appUser) {
        List<AppUser> appUserList = this.userRepository.getAppUserByEmail(appUser.getEmail());

        if (!appUserList.isEmpty()) {
            if (appUserList.size() == 1){
                if (!Objects.equals(appUserList.getFirst().getId(), appUser.getId())){
                    throw new IllegalArgumentException("E-mail já é utilizado por outro usuário");
                }
            } else {
                throw new IllegalArgumentException("E-mail já é utilizado por outro usuário");
            }
        }

        logger.info("E-mail ainda não utilizado ou no máximo utilizado pelo próprio usuário a ser atualizado.");

        if (next != null) {
            next.handle(appUser);
        }
    }

}