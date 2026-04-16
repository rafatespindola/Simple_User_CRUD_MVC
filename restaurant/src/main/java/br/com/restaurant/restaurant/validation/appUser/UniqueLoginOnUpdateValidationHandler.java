package br.com.restaurant.restaurant.validation.appUser;

import br.com.restaurant.restaurant.controller.UserController;
import br.com.restaurant.restaurant.dto.UpdateAppUserDTO;
import br.com.restaurant.restaurant.entity.AppUser;
import br.com.restaurant.restaurant.exception.BusinessException;
import br.com.restaurant.restaurant.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class UniqueLoginOnUpdateValidationHandler extends AppUserValidationOnUpdateHandler{

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UniqueLoginOnUpdateValidationHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void handle(UpdateAppUserDTO appUser, Long id) {

        List<AppUser> appUserList = this.userRepository.getAppUserByLogin(appUser.login());

        if (!appUserList.isEmpty()) {
            if (appUserList.size() == 1){
                if (!Objects.equals(appUserList.getFirst().getId(), id)){
                    throw new BusinessException("Login já é utilizado por outro usuário"
                            );
                }
            } else {
                throw new BusinessException("Login já é utilizado por outro usuário. Há mais de um usuário com o mesmo login. Isso é crítico");
            }
        }

        logger.info("Login ainda não utilizado ou no máximo utilizado pelo próprio usuário a ser atualizado");

        if (next != null) {
            next.handle(appUser, id);
        }
    }
}
