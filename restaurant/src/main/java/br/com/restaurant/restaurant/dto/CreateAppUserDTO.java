package br.com.restaurant.restaurant.dto;

import br.com.restaurant.restaurant.entity.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record CreateAppUserDTO(

        @NotBlank(message = "nome é obrigatório")
        String name,

        @NotBlank(message = "email é obrigatório")
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "login é obrigatório")
        String login,

        @NotBlank(message = "password é obrigatório")
        @Size(min = 6, message = "A senha deve conter no mínimo 6 caracteres")
        String password,

        @NotBlank(message = "address é obrigatório")
        String address,

        @NotNull(message = "userType é obrigatório")
        UserType userType
) {

}
