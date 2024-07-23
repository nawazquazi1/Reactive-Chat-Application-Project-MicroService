package com.chat.user.Controller.Synchronous;


import com.chat.user.Exception.ApiException;
import com.chat.user.Exception.EmailAlreadyExistsException;
import com.chat.user.Exception.UserNameAlreadyExistsException;
import com.chat.user.Payload.UpdateUserDetails;
import com.chat.user.Payload.UserDto;
import com.chat.user.Service.Synchronous.UserService_Synchronous;
import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("syn/user")
@AllArgsConstructor
@Slf4j
public class UserController_Syn {


    private final UserService_Synchronous SyncService;


    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto user) {
        UserDto userDto;
        try {
            userDto = this.SyncService.registerUser(user);
            return ResponseEntity.ok(userDto);
        } catch (UserNameAlreadyExistsException | EmailAlreadyExistsException e) {
            throw new ApiException(e.getMessage());
        }
    }

    @PutMapping("/update/user/{userId}")
    public ResponseEntity<UserDto> update(@Valid @RequestBody UpdateUserDetails user, @PathVariable Long userId) {
        UserDto userDto;
        try {
            userDto = this.SyncService.updateUser(user, userId);
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(this.SyncService.findAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> findById(@PathVariable Long userId) {
        return ResponseEntity.ok(this.SyncService.findUserById(userId));
    }

    @GetMapping("/search/keyword/{keyword}")
    public ResponseEntity<List<UserDto>> searchUserWithKeyword(@PathVariable String keyword) {
        List<UserDto> users = this.SyncService.searchUsersByUsername(keyword);
        return ResponseEntity.ok(users);
    }


}
