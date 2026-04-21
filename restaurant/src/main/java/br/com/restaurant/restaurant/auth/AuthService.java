package br.com.restaurant.restaurant.auth;

import br.com.restaurant.restaurant.exception.LoginException;
import br.com.restaurant.restaurant.repository.UserRepository;
import br.com.restaurant.restaurant.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository repository;
    private final JwtService jwtService;

    public AuthService(UserRepository repository, JwtService jwtService) {
        this.repository = repository;
        this.jwtService = jwtService;
    }

    public AuthResponse login(AuthRequest request) {

        var appUserList = repository.getAppUserByLogin(request.login());

        if (appUserList.isEmpty()) {
            throw new LoginException("Login não encontrado: " + request.login());
        }

        if (appUserList.size() > 1) {
            throw new LoginException("Estado crítico. Há mais de um usuário para o mesmo login. Login: " + request.login());
        }

        var appUser = appUserList.getFirst();

        if (!PasswordUtil.matches(request.password(), appUser.getPassword())) {
            throw new LoginException("Senha inválida");
        }

        String token = jwtService.generateToken(appUser.getId(), appUser.getLogin());

        return new AuthResponse(token);
    }
}
