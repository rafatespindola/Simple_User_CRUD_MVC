package br.com.restaurant.restaurant.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRequest(

        @Schema(description = "Login do usuário", example = "rafatespindola")
        @NotBlank(message = "'login' é obrigatório")
        String login,

        @Schema(description = "Senha do usuário", example = "12@#abAB")
        @NotBlank(message = "'password' é obrigatório")
        @Size(min = 6, message = "A senha deve conter no mínimo 6 caracteres")
        String password) {
}
