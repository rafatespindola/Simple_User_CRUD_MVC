package br.com.restaurant.restaurant.service;

import br.com.restaurant.restaurant.auth.PasswordUtil;
import br.com.restaurant.restaurant.controller.UserController;
import br.com.restaurant.restaurant.dto.CreateAppUserDTO;
import br.com.restaurant.restaurant.dto.UpdateAppUserDTO;
import br.com.restaurant.restaurant.entity.AppUser;
import br.com.restaurant.restaurant.exception.CreateResourceException;
import br.com.restaurant.restaurant.exception.ResourceNotFoundException;
import br.com.restaurant.restaurant.repository.UserRepository;
import br.com.restaurant.restaurant.validation.appUser.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void createAppUser(CreateAppUserDTO appUser) {
        AppUserValidationHandler uniqueLoginValidation = new UniqueLoginValidationHandler(this.userRepository);
        AppUserValidationHandler uniqueEmailValidation = new UniqueEmailValidationHandler(this.userRepository);

        uniqueLoginValidation.setNext(uniqueEmailValidation);

        logger.info("createUser: Inicia validações. AppUser: {}", appUser.toString());
        uniqueLoginValidation.handle(appUser);
        logger.info("createUser: Validações de criação de usuário bem sucedidadas. AppUser: {}", appUser.toString());

        String hashedPassword = PasswordUtil.hash(appUser.password());

        var create = this.userRepository.createUser(appUser, hashedPassword);

        if (create != 1) {
            throw new CreateResourceException("Erro ao criar usuário. AppUser: "+ appUser.toString());
        }
    }

    public void updateAppUser(UpdateAppUserDTO appUser, Long id) {
        logger.info("updateAppUser: Criando corrente de validações de criação de usuário");

        AppUserValidationOnUpdateHandler uniqueLoginOnUpdateValidationHandler = new UniqueLoginOnUpdateValidationHandler(this.userRepository);
        AppUserValidationOnUpdateHandler uniqueEmailOnUpdateValidationHandler = new UniqueEmailOnUpdateValidationHandler(this.userRepository);

        logger.info("updateAppUser: Iniciado de fato validação de criação de usuário");
        uniqueLoginOnUpdateValidationHandler.setNext(uniqueEmailOnUpdateValidationHandler);

        uniqueLoginOnUpdateValidationHandler.handle(appUser, id);

        logger.info("updateAppUser: Validações de atualização do usuário bem sucedidas");
        var update = this.userRepository.updateAppUser(appUser, id);

        if (update == 0) {
            throw new ResourceNotFoundException("Erro ao atualizar appUser. AppUser: " + appUser.toString());
        }
    }

    public List<AppUser> getAllAppUsers(int page, int size) {
        int offset = (page - 1) * size;
        return this.userRepository.getAllAppUsers(size, offset);
    }

    public List<AppUser> getAppUserByName(String name) {
        return this.userRepository.getAppUserByName(name);
    }

    public AppUser getAppUserById(Long id) {
        return this.userRepository.getAppUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado pelo Id: " + id));
    }


    public void updateAppUserPassword(Long id, String password) {
        logger.info("updateAppUserPassword: Criptografado senha para update");
        String hashed = PasswordUtil.hash(password);

        var update = this.userRepository.updateAppUserPassword(id, hashed);

        if (update == 0) {
            throw new ResourceNotFoundException("Erro ao atualizar senha. Usuário não encontrado. Id: " + id);
        }
    }

    public void deleteAppUser(Long id) {
        var delete = this.userRepository.deleteAppUser(id);

        if (delete == 0) {
            throw new ResourceNotFoundException("Erro ao deletar usuário. Id: " + id);
        }
    }
}
