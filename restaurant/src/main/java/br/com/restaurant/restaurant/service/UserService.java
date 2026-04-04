package br.com.restaurant.restaurant.service;

import br.com.restaurant.restaurant.entity.AppUser;
import br.com.restaurant.restaurant.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

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
        var update = this.userRepository.updateAppUser(id, appUser);
        Assert.state(update == 1, "Erro ao atualizar appUser. Id: " + id + " AppUser: " + appUser.toString());
    }

    public void deleteAppUser(Long id) {
        var delete = this.userRepository.deleteAppUser(id);
        Assert.state(delete == 1, "Erro ao deletar appUser. Id: " + id);
    }
}
