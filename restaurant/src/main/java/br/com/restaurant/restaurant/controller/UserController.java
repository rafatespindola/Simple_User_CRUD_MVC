package br.com.restaurant.restaurant.controller;

import br.com.restaurant.restaurant.dto.UpdatePasswordRequestDTO;
import br.com.restaurant.restaurant.entity.AppUser;
import br.com.restaurant.restaurant.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Validated
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
        @RequestBody @Valid AppUser appUser
    ) {
        logger.info("POST -> /api/v1/users -> createUser -> User: {}", appUser.toString());
        this.userService.createUser(appUser);
        return ResponseEntity.status(201).build();
    }


    @GetMapping
    public ResponseEntity<List<AppUser>> getAllAppUsers(
            @RequestParam("page") @Min(1) int page,
            @RequestParam("size") @Min(1) int size
    ) {
        logger.info("GET -> /api/v1/users -> getAllAppUsers -> Page: {}, Size: {}", page, size);
        var appUsers = this.userService.getAllAppUsers(page, size);
        return ResponseEntity.ok(appUsers);
    }


    @GetMapping("/name")
    public ResponseEntity<List<AppUser>> getAppUserByName(
            @RequestParam("name") String name
    ) {
        logger.info("GET -> /api/v1/users/ -> getAppUserByName -> Param name: {}", name);
        var appUser = this.userService.getAppUserByName(name);
        return ResponseEntity.ok(appUser);
    }


    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getAppUserById(
            @PathVariable("id") Long id
    ) {
        logger.info("GET -> /api/v1/users/{id} -> getAppUserById -> id: {}", id);
        var appUser = this.userService.getAppUserById(id);
        return ResponseEntity.ok(appUser);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAppUser(
            @PathVariable Long id,
            @RequestBody AppUser appUser
    ) {
        logger.info("PUT -> /api/v1/users/{id} -> updateAppUser -> id: {}, AppUser: {}", id, appUser.toString());
        this.userService.updateAppUser(id, appUser);
        return ResponseEntity.status(204).build();
    }


    @PatchMapping("/password/{id}")
    public ResponseEntity<Void> updateAppUserPassword(
            @PathVariable Long id,
            @RequestBody @Valid UpdatePasswordRequestDTO request
    ) {
        logger.info("Patch -> /api/v1/users/password/{id} -> updateAppUserPassword -> id: {}, Password: {}", id, request.password());
        this.userService.updateAppUserPassword(id, request.password());
        return ResponseEntity.status(204).build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppUser(
            @PathVariable Long id
    ) {
        logger.info("DELETE -> /api/v1/users/{id} -> deleteAppUser -> id: {}", id);
        this.userService.deleteAppUser(id);
        return ResponseEntity.status(204).build();
    }

}
