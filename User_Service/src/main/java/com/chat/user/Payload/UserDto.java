package com.chat.user.Payload;

import com.chat.user.Model.Role;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class UserDto {

    private Long id;

    @Size(min = 4, max = 10, message = "Password must be between 4 to 10 characters long ")
    private String name;

    @Pattern(regexp = "^[a-zA-Z]{5,15}$", message = "userName must be between 5 and 10 characters, containing only letters (no numbers or special characters).\n")
    @NotBlank(message = "userName must not be blank")
    private String username;

    @NotBlank
    @Size(min = 6, max = 6, message = "Password must be at least 6 characters long ")
    private String password;

//    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n", message = "Invalid email address. Please provide a valid email address in the format 'username@domain.com'.")
    @Email(message ="Invalid email address. Please provide a valid email address in the format 'username@domain.com'." )
    @NotEmpty(message = "Email is required !!")
    private String email;

    private String bio;

    private boolean active;

    private Set<Role> roles = new HashSet<>();
}
