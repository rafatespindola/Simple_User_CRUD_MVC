package br.com.restaurant.restaurant.service;

import br.com.restaurant.restaurant.auth.PasswordUtil;
import br.com.restaurant.restaurant.controller.UserController;
import br.com.restaurant.restaurant.entity.AppUser;
import br.com.restaurant.restaurant.repository.UserRepository;
import br.com.restaurant.restaurant.validation.appUser.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(AppUser appUser) {
        AppUserValidationHandler uniqueLoginValidation = new UniqueLoginValidationHandler(this.userRepository);
        AppUserValidationHandler emailFormatValidation = new EmailFormatValidationHandler();
        AppUserValidationHandler uniqueEmailValidation = new UniqueEmailValidationHandler(this.userRepository);

        uniqueLoginValidation.setNext(emailFormatValidation);
        emailFormatValidation.setNext(uniqueEmailValidation);

        uniqueLoginValidation.handle(appUser);

        logger.info("createUser: Validações de criação de usuário bem sucedidadas. AppUser: {}", appUser.toString());

        String hashed = PasswordUtil.hash(appUser.getPassword());
        appUser.setPassword(hashed);

        var create = this.userRepository.createUser(appUser);
        Assert.state(create == 1, "createUser: Erro ao salvar usuário -> AppUser: " + appUser);
    }

    public List<AppUser> getAllAppUsers(int page, int size) {
        int offset = (page - 1) * size;
        return this.userRepository.getAllAppUsers(size, offset);
    }

    public List<AppUser> getAppUserByName(String name) {
        return this.userRepository.getAppUserByName(name);
    }

    public Optional<AppUser> getAppUserById(Long id) {
        return this.userRepository.getAppUserById(id);
    }

    public void updateAppUser(Long id, AppUser appUser) {
        logger.info("updateAppUser: Atualizado id do usuário a partir do id da URL. Id: {}", id);
        appUser.setId(id);

        logger.info("updateAppUser: Iniciado corrente validação de criação de usuário");
        AppUserValidationHandler uniqueLoginOnUpdateValidationHandler = new UniqueLoginOnUpdateValidationHandler(this.userRepository);
        AppUserValidationHandler emailFormatValidation = new EmailFormatValidationHandler();
        AppUserValidationHandler uniqueEmailOnUpdateValidationHandler = new UniqueEmailOnUpdateValidationHandler(this.userRepository);

        logger.info("updateAppUser: Iniciado de fato validação de criação de usuário");
        uniqueLoginOnUpdateValidationHandler.setNext(emailFormatValidation);
        emailFormatValidation.setNext(uniqueEmailOnUpdateValidationHandler);

        uniqueLoginOnUpdateValidationHandler.handle(appUser);

        logger.info("updateAppUser: Validações de atualização do usuário bem sucedidas. Email: " + appUser.getEmail());
        var update = this.userRepository.updateAppUser(id, appUser);
        Assert.state(update == 1, "Erro ao atualizar appUser. Id: " + id + " AppUser: " + appUser.toString());
    }

    public void updateAppUserPassword(Long id, String password) {
        logger.info("updateAppUserPassword: Criptografado senha para update");
        String hashed = PasswordUtil.hash(password);

        var update = this.userRepository.updateAppUserPassword(id, hashed);
        Assert.state(update == 1, "Erro ao atualizar senha do appUser de Id: " + id + " Senha: " + hashed);
    }

    public void deleteAppUser(Long id) {
        var delete = this.userRepository.deleteAppUser(id);
        Assert.state(delete == 1, "Erro ao deletar appUser. Id: " + id);
    }
}
