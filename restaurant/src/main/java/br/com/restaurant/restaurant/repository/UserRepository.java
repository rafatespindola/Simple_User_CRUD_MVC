package br.com.restaurant.restaurant.repository;

import br.com.restaurant.restaurant.dto.CreateAppUserDTO;
import br.com.restaurant.restaurant.dto.UpdateAppUserDTO;
import br.com.restaurant.restaurant.dto.AppUsersResponseDTO;
import br.com.restaurant.restaurant.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Integer createUser(CreateAppUserDTO appUser, String password);
    List<AppUsersResponseDTO> getAllAppUsers(int size, int offset);
    Optional<AppUsersResponseDTO> getAppUserById(Long id);
    List<AppUsersResponseDTO> getAppUserByName(String name);
    Integer updateAppUser(UpdateAppUserDTO appUser, Long id);
    Integer updateAppUserPassword(Long id, String password);
    Integer deleteAppUser(Long id);
    boolean existsByEmail(String email);
    List<AppUser> getAppUserByEmail(String email);
    List<AppUser> getAppUserByLogin(String email);

}
