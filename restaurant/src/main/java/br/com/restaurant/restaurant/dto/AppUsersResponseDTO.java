package br.com.restaurant.restaurant.dto;

import br.com.restaurant.restaurant.entity.UserType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record AppUsersResponseDTO(

        @Schema(description = "Nome do usuário", example = "Rafael Teles Espindola")
        Long id,

        @Schema(description = "Nome do usuário", example = "Rafael Teles Espindola")
        String name,

        @Schema(description = "Email do usuário", example = "rafael@email.com")
        String email,

        @Schema(description = "Login do usuário", example = "rafatespindola")
        String login,

        @Schema(description = "Endereço do usuário", example = "Rua Brasil, N 123. Cidade tal - BR")
        String address,

        @Schema(description = "Tipo de usuário", example = "RESTAURANT_OWNER")
        UserType userType,

        @Schema(description = "Última atualização realizada", example = "2026-04-18 14:21:55")
        LocalDateTime lastUpdate
) {

}
