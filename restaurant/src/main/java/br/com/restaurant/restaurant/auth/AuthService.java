package br.com.restaurant.restaurant.auth;

import br.com.restaurant.restaurant.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthService {

    private final UserRepository repository;
    private final JwtService jwtService;

    public AuthService(UserRepository repository, JwtService jwtService) {
        this.repository = repository;
        this.jwtService = jwtService;
    }

    public AuthResponse login(AuthRequest request) {

        var appUserList = repository.getAppUserByLogin(request.login());

        if (appUserList.isEmpty()) {
            throw new NoSuchElementException("Login não encontrado: " + request.login());
        }

        if (appUserList.size() > 1) {
            throw new IllegalStateException("Estado crítico. Há mais de um usuário para o mesmo login. Login: " + request.login());
        }

        var appUser = appUserList.getFirst();

        if (!PasswordUtil.matches(request.password(), appUser.getPassword())) {
            throw new IllegalArgumentException("Login ou senha inválidos");
        }

        String token = jwtService.generateToken(appUser.getId(), appUser.getLogin());

        return new AuthResponse(token);
    }
}
