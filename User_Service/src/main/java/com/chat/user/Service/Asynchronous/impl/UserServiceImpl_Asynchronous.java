package com.chat.user.Service.Asynchronous.impl;

import com.chat.user.Config.AppConstants;
import com.chat.user.Exception.EmailAlreadyExistsException;
import com.chat.user.Exception.UserNameAlreadyExistsException;
import com.chat.user.Model.User;
import com.chat.user.Payload.UpdateUserDetails;
import com.chat.user.Payload.UserDto;
import com.chat.user.Repositories.Asynchronous.RoleRepositories_Asy;
import com.chat.user.Repositories.Asynchronous.UserRepositories_Asy;
import com.chat.user.Service.Asynchronous.UserService_Asynchronous;
import com.chat.user.Service.Synchronous.impl.UserServiceImpl_Synchronous;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Transactional
@Qualifier("AsyncService")
public class UserServiceImpl_Asynchronous implements UserService_Asynchronous {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl_Synchronous.class);
    private UserRepositories_Asy userRepositories;
    private RoleRepositories_Asy roleRepositories;
    //    private PasswordEncoder PasswordEncoder;
    private ModelMapper modelMapper;

    @Override
    public Mono<UserDto> registerUser(UserDto userDto) {
        log.info("Registering user {}", userDto.getUsername());
        return userRepositories.existsByUsername(userDto.getUsername())
                .flatMap(usernameExists -> {
                    if (usernameExists) {
                        log.warn("Username {} already exists", userDto.getUsername());
                        return Mono.error(new UserNameAlreadyExistsException(
                                String.format("Username %s already exists", userDto.getUsername())));
                    }
                    return userRepositories.existsByEmail(userDto.getEmail())
                            .flatMap(emailExists -> {
                                if (emailExists) {
                                    log.warn("Email {} already exists", userDto.getEmail());
                                    return Mono.error(new EmailAlreadyExistsException(
                                            String.format("Email %s already exists", userDto.getEmail())));
                                }

                                // Proceed with registration if no existing username or email
                                userDto.setActive(true);
                                // Encode password securely before saving (uncomment and implement appropriately)
                                // userDto.setPassword(PasswordEncoder.encode(userDto.getPassword()));

                                return roleRepositories.findById(AppConstants.APP_USER)
                                        .switchIfEmpty(Mono.error(new RuntimeException("Default role not found")))
                                        .flatMap(role -> {
                                            userDto.getRoles().add(role);
                                            return Mono.fromCallable(() -> dtoToUser(userDto));
                                        })
                                        .flatMap(user -> userRepositories.save(user))
                                        .map(this::userToDto);
                            });
                })
                .onErrorResume(e -> {
                    log.error("Error occurred during user registration", e);
                    return Mono.error(e); // Or handle error gracefully
                });
    }

    @Override
    public Mono<UserDto> updateUser(UpdateUserDetails updateUserDetails, Long id) {
        return null;
    }

    @Override
    public Flux<UserDto> findAllUsers() {
        return null;
    }

    @Override
    public Mono<UserDto> findUserById(long id) {
        return null;
    }

    @Override
    public Flux<UserDto> searchUsersByUsername(String username) {
        return null;
    }

    @Override
    public User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        user.setCreatedAt(Instant.now());
        return user;
    }

    @Override
    public UserDto userToDto(User user) {
        return this.modelMapper.map(user, UserDto.class);
    }
}
