package br.com.restaurant.restaurant.dto;

import br.com.restaurant.restaurant.entity.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UpdateAppUserDTO(

        @Schema(description = "Nome do usuário", example = "Rafael Teles Espindola")
        @NotBlank(message = "Campo obrigatório")
        String name,

        @Schema(description = "Email do usuário", example = "rafael@email.com")
        @NotBlank(message = "Campo obrigatório")
        @Email(message = "Email inválido")
        String email,

        @Schema(description = "Login do usuário", example = "rafatespindola")
        @NotBlank(message = "Campo obrigatório")
        String login,

        @Schema(description = "Endereço do usuário", example = "Rua Brasil, N 123. Cidade tal - BR")
        @NotBlank(message = "Campo obrigatório")
        String address,

        @Schema(description = "Tipo de usuário", example = "CLIENT")
        @NotNull(message = "Campo obrigatório")
        UserType userType
) {

}
