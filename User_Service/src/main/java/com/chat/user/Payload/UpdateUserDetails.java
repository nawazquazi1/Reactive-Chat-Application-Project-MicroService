package com.chat.user.Payload;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Getter
@Setter
public class UpdateUserDetails {

    @Pattern(regexp = "^[a-zA-Z]{5,15}$", message = "userName must be between 5 and 10 characters, containing only letters (no numbers or special characters).")
    private String UserName;

    @Size(min = 4, max = 10, message = "Password must be between 4 to 10 characters long ")
    private String name;

    private String bio;
}
