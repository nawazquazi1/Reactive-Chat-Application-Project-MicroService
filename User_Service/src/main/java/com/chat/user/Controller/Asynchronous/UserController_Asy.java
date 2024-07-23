package com.chat.user.Controller.Asynchronous;

import com.chat.user.Exception.EmailAlreadyExistsException;
import com.chat.user.Exception.UserNameAlreadyExistsException;
import com.chat.user.Payload.UserDto;
import com.chat.user.Service.Asynchronous.UserService_Asynchronous;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/asy/user")
@AllArgsConstructor
public class UserController_Asy {


    private final UserService_Asynchronous AsyncService;

    @PostMapping("/register")
    public Mono<ResponseEntity<UserDto>> registerUser(@Valid @RequestBody UserDto userDto) {
        return AsyncService.registerUser(userDto)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> {
                    if (e instanceof UserNameAlreadyExistsException || e instanceof EmailAlreadyExistsException) {
                        return Mono.just(ResponseEntity.badRequest().body(userDto));
                    }
                    return Mono.just(ResponseEntity.ok(userDto));
                }
                );
    }



}
