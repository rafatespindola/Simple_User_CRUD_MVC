package br.com.restaurant.restaurant.repository;

import br.com.restaurant.restaurant.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Integer createUser(AppUser appUser);
    List<AppUser> getAllAppUsers(int size, int offset);
    Optional<AppUser> getAppUserById(Long id);
    List<AppUser> getAppUserByName(String name);
    Integer updateAppUser(Long id, AppUser appUser);
    Integer deleteAppUser(Long id);
}
