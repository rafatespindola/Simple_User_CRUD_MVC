package br.com.restaurant.restaurant.dto;

import br.com.restaurant.restaurant.entity.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateAppUserDTO(

        @Schema(description = "Nome do usuário", example = "Rafael Teles Espindola")
        @NotBlank(message = "'name' é obrigatório")
        String name,

        @Schema(description = "Email do usuário", example = "rafael@email.com")
        @NotBlank(message = "'email' é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @Schema(description = "Login do usuário", example = "rafatespindola")
        @NotBlank(message = "'login' é obrigatório")
        String login,

        @Schema(description = "Senha do usuário", example = "12@#abAB")
        @NotBlank(message = "'password' é obrigatório")
        @Size(min = 6, message = "A senha deve conter no mínimo 6 caracteres")
        String password,

        @Schema(description = "Endereço do usuário", example = "Rua Brasil, N 123. Cidade tal - BR")
        @NotBlank(message = "'address' é obrigatório")
        String address,

        @Schema(description = "Tipo de usuário", example = "RESTAURANT_OWNER")
        @NotNull(message = "'userType' é obrigatório")
        UserType userType
) {

}
