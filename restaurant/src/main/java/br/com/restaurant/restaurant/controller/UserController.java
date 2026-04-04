package br.com.restaurant.restaurant.controller;

import br.com.restaurant.restaurant.entity.AppUser;
import br.com.restaurant.restaurant.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<AppUser> createUser(
        @RequestBody AppUser appUser
    ) {
        logger.info("POST -> /api/v1/users -> createUser -> User: {}", appUser.toString());
        this.userService.createUser(appUser);
        return ResponseEntity.status(201).build();
    }

}
