package br.com.restaurant.restaurant.repository;

import br.com.restaurant.restaurant.entity.AppUser;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class UserRepositoryImp implements UserRepository{
    private final JdbcClient jdbcClient;

    public UserRepositoryImp(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Integer createUser(AppUser appUser) {
        LocalDateTime agora = LocalDateTime.now();
        return this.jdbcClient
                .sql("""
                    INSERT INTO users (name, email, login, password, address, user_type, last_update)
                    VALUES (:name, :email, :login, :password, :address, :user_type, :last_update)
                """)
                .param("name", appUser.getName())
                .param("email", appUser.getEmail())
                .param("login", appUser.getLogin())
                .param("password", appUser.getPassword())
                .param("address", appUser.getAddress())
                .param("user_type", appUser.getUserType().name())
                .param("last_update", agora)
                .update();
    }
}
