package br.com.restaurant.restaurant.repository;

import br.com.restaurant.restaurant.dto.CreateAppUserDTO;
import br.com.restaurant.restaurant.dto.UpdateAppUserDTO;
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
    public Integer createUser(CreateAppUserDTO appUser, String password) {
        LocalDateTime agora = LocalDateTime.now();
        return this.jdbcClient
                .sql("""
                    INSERT INTO users (name, email, login, password, address, user_type, last_update)
                    VALUES (:name, :email, :login, :password, :address, :user_type, :last_update)
                """)
                .param("name", appUser.name())
                .param("email", appUser.email())
                .param("login", appUser.login())
                .param("password", password)
                .param("address", appUser.address())
                .param("user_type", appUser.userType().name())
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
                    .sql("SELECT * " +
                            "FROM users " +
                            "WHERE TRIM(LOWER(name)) LIKE TRIM(LOWER(CONCAT('%', :name, '%')));")
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
    public Integer updateAppUser(UpdateAppUserDTO appUser, Long id) {
        LocalDateTime agora = LocalDateTime.now();
        return this.jdbcClient
                .sql("UPDATE users set " +
                        "name = :name," +
                        "email = :email," +
                        "login = :login," +
                        "address = :address," +
                        "user_type = :user_type," +
                        "last_update = :last_update " +
                        "WHERE id = :id")
                .param("id", id)
                .param("name", appUser.name())
                .param("email", appUser.email())
                .param("login", appUser.login())
                .param("address", appUser.address())
                .param("user_type", appUser.userType().name())
                .param("last_update", agora)
                .update();
    }

    @Override
    public Integer updateAppUserPassword(Long id, String password) {
        LocalDateTime agora = LocalDateTime.now();
        return this.jdbcClient
                .sql("UPDATE users set " +
                        "password = :password," +
                        "last_update = :last_update " +
                        "WHERE id = :id")
                .param("id", id)
                .param("password", password)
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

    @Override
    public List<AppUser> getAppUserByLogin(String login) {
        return this.jdbcClient
                .sql("SELECT * \n" +
                        "FROM users \n" +
                        "WHERE REPLACE(TRIM(login), ' ', '') = REPLACE(TRIM(:login), ' ', '');")
                .param("login", login)
                .query(AppUser.class)
                .list();
    }


}