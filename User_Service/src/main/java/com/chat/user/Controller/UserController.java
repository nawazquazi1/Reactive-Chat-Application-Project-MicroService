package com.chat.user.Controller;


import com.chat.user.Exception.ApiException;
import com.chat.user.Exception.EmailAlreadyExistsException;
import com.chat.user.Exception.UserNameAlreadyExistsException;
import com.chat.user.Model.User;
import com.chat.user.Payload.UserDto;
import com.chat.user.Service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto user) {
        UserDto userDto;
        try {
        userDto = this.userService.registerUser(user);
            return ResponseEntity.ok(userDto);
        } catch (UserNameAlreadyExistsException | EmailAlreadyExistsException e) {
            throw new ApiException(e.getMessage());
        }
    }


    @GetMapping("/nawaz")
    public String nawaz(){
        return "hello muy name is nawaz quaZIO ";
    }

}
