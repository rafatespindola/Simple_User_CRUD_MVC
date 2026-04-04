package br.com.restaurant.restaurant.repository;

import br.com.restaurant.restaurant.entity.AppUser;

public interface UserRepository {
    Integer createUser(AppUser appUser);
}
