package br.com.restaurant.restaurant.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AppUser {

    private Long id;
    private String name;
    private String email;
    private String login;
    private String password;
    private String address;
    private UserType userType;
    private LocalDateTime lastUpdate;
}
