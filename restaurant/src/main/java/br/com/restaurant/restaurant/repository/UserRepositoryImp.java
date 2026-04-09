package br.com.restaurant.restaurant.repository;

import br.com.restaurant.restaurant.entity.AppUser;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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


    @Override
    public List<AppUser> getAllAppUsers(int size, int offset) {
        return this.jdbcClient
                .sql("SELECT * FROM users LIMIT :size OFFSET :offset")
                .param("size", size)
                .param("offset", offset)
                .query(AppUser.class)
                .list();
    }


    @Override
    public List<AppUser> getAppUserByName(String name) {
        return this.jdbcClient
                .sql("SELECT * FROM users WHERE name = :name")
                .param("name", name)
                .query(AppUser.class)
                .list();
    }


    @Override
    public Optional<AppUser> getAppUserById(Long id) {
        return this.jdbcClient
                .sql("SELECT * FROM users WHERE id = :id")
                .param("id", id)
                .query(AppUser.class)
                .optional();
    }


    @Override
    public Integer updateAppUser(Long id, AppUser appUser) {
        LocalDateTime agora = LocalDateTime.now();
        return this.jdbcClient
                .sql("UPDATE users set " +
                        "name = :name," +
                        "email = :email," +
                        "login = :login," +
                        "password = :password," +
                        "address = :address," +
                        "user_type = :user_type," +
                        "last_update = :last_update WHERE id = :id")
                .param("id", id)
                .param("name", appUser.getName())
                .param("email", appUser.getEmail())
                .param("login", appUser.getLogin())
                .param("password", appUser.getPassword())
                .param("address", appUser.getAddress())
                .param("user_type", appUser.getUserType().name())
                .param("last_update", agora)
                .update();
    }

    @Override
    public Integer deleteAppUser(Long id) {
        return this.jdbcClient.sql("DELETE FROM users WHERE id = :id")
                .param("id", id)
                .update();
    }

    @Override
    public boolean existsByEmail(String email) {
        return !this.jdbcClient
                .sql("SELECT * \n" +
                        "FROM users \n" +
                        "WHERE LOWER(REPLACE(TRIM(email), ' ', '')) = LOWER(REPLACE(TRIM(:email), ' ', ''));")
                .param("email", email)
                .query(AppUser.class)
                .list().isEmpty();
    }

    @Override
    public List<AppUser> getAppUserByEmail(String email) {
        return this.jdbcClient
                .sql("SELECT * \n" +
                        "FROM users \n" +
                        "WHERE LOWER(REPLACE(TRIM(email), ' ', '')) = LOWER(REPLACE(TRIM(:email), ' ', ''));")
                .param("email", email)
                .query(AppUser.class)
                .list();
    }


}