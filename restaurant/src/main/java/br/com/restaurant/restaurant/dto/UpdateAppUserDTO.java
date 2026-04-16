package br.com.restaurant.restaurant.dto;

import br.com.restaurant.restaurant.entity.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record UpdateAppUserDTO(

        @NotBlank(message = "Campo obrigatório")
        String name,

        @NotBlank(message = "Campo obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Campo obrigatório")
        String login,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 6, message = "A senha deve conter no mínimo 6 caracteres")
        String password,

        @NotBlank(message = "Campo obrigatório")
        String address,

        @NotNull(message = "Campo obrigatório")
        UserType userType
) {

}
