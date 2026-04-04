package br.com.restaurant.restaurant.service;

import br.com.restaurant.restaurant.entity.AppUser;
import br.com.restaurant.restaurant.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(AppUser appUser) {
        var create = this.userRepository.createUser(appUser);
        Assert.state(create == 1, "Erro ao salvar usuário -> User: " + appUser);
    }
}
