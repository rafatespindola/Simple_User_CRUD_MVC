package br.com.fiap.restaurant_platform_api.repository;

import br.com.fiap.restaurant_platform_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
