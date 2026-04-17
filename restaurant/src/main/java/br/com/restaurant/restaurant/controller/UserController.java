package br.com.restaurant.restaurant.controller;

import br.com.restaurant.restaurant.dto.CreateAppUserDTO;
import br.com.restaurant.restaurant.dto.UpdateAppUserDTO;
import br.com.restaurant.restaurant.dto.UpdatePasswordRequestDTO;
import br.com.restaurant.restaurant.entity.AppUser;
import br.com.restaurant.restaurant.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SecurityRequirement(name = "bearerAuth")
@Validated
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "Criar usuário")
    @PostMapping
    public ResponseEntity<AppUser> createAppUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados necessários para criação do usuário",
                    required = true
            )
            @RequestBody @Valid CreateAppUserDTO createAppUserDTO
    ) {
        logger.info("POST -> /api/v1/users -> createAppUser -> AppUser: {}", createAppUserDTO.toString());
        this.userService.createAppUser(createAppUserDTO);
        return ResponseEntity.status(201).build();
    }


    @Operation(
            summary = "Buscar usuários",
            description = "Retorna uma lista de usuários de acordo com os parâmetros size e page. Ambos iniciam em 1"
    )
    @GetMapping
    public ResponseEntity<List<AppUser>> getAllAppUsers(
            @Parameter(
                    description = "Página da lista de usuários",
                    example = "1",
                    required = true
            )
            @RequestParam("page") @Min(1) int page,
            @Parameter(
                    description = "Tamanho da lista de usuários retornados por página",
                    example = "100",
                    required = true
            )
            @RequestParam("size") @Min(1) int size
    ) {
        logger.info("GET -> /api/v1/users -> getAllAppUsers -> Page: {}, Size: {}", page, size);
        var appUsers = this.userService.getAllAppUsers(page, size);
        return ResponseEntity.ok(appUsers);
    }


    @Operation(
            summary = "Buscar usuários por nome",
            description = "Retorna uma lista de usuários que contenham o nome informado"
    )
    @GetMapping("/name")
    public ResponseEntity<List<AppUser>> getAppUserByName(
            @Parameter(
                    description = "Nome do usuário para busca",
                    example = "Rafael",
                    required = true
            )
            @RequestParam @NotBlank(message = "O parâmetro 'name' não pode ser vazio") String name
    ) {
        logger.info("GET -> /api/v1/users/ -> getAppUserByName -> Param name: {}", name);
        var appUser = this.userService.getAppUserByName(name);
        return ResponseEntity.ok(appUser);
    }


    @Operation(
            summary = "Buscar usuário pelo Id",
            description = "Retona um usuário, se houver"
    )
    @GetMapping("/{id}")
    public ResponseEntity<AppUser> getAppUserById(
            @PathVariable @Parameter(
                    description = "Id do usuário",
                    example = "1",
                    required = true
            ) Long id
    ) {
        logger.info("GET -> /api/v1/users/{id} -> getAppUserById -> id: {}", id);
        var appUser = this.userService.getAppUserById(id);
        return ResponseEntity.ok(appUser);
    }


    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza todas informações do usuário menos a senha"
    )
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAppUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados necessários para atualização do usuário",
                    required = true
            )
            @RequestBody @Valid UpdateAppUserDTO appUser,
            @Parameter(
                    description = "Id do usuário",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        logger.info("PUT -> /api/v1/users/{id} -> updateAppUser -> id: {} -> AppUser: {}", id, appUser.toString());
        this.userService.updateAppUser(appUser, id);
        return ResponseEntity.status(204).build();
    }


    @Operation(summary = "Atualizar senha de usuário")
    @PatchMapping("/password/{id}")
    public ResponseEntity<Void> updateAppUserPassword(
            @Parameter(
                    description = "Id do usuário",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nova senha do usuário",
                    required = true
            )
            @RequestBody @Valid UpdatePasswordRequestDTO request
    ) {
        logger.info("Patch -> /api/v1/users/password/{id} -> updateAppUserPassword -> id: {}, Password: {}", id, request.password());
        this.userService.updateAppUserPassword(id, request.password());
        return ResponseEntity.status(204).build();
    }


    @Operation(summary = "Deletar usuário por Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppUser(
            @Parameter(
                    description = "Id do usuário",
                    example = "1",
                    required = true
            )
            @PathVariable Long id
    ) {
        logger.info("DELETE -> /api/v1/users/{id} -> deleteAppUser -> id: {}", id);
        this.userService.deleteAppUser(id);
        return ResponseEntity.status(204).build();
    }

}
